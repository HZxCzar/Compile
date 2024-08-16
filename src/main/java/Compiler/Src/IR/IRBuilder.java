package Compiler.Src.IR;

import java.lang.reflect.Array;
import java.util.ArrayList;

import Compiler.Src.AST.ASTVisitor;
import Compiler.Src.AST.Node.*;
import Compiler.Src.AST.Node.DefNode.*;
import Compiler.Src.AST.Node.ExprNode.*;
import Compiler.Src.AST.Node.ExprNode.ExprUnitNode.*;
import Compiler.Src.AST.Node.StatementNode.*;
import Compiler.Src.IR.Entity.*;
import Compiler.Src.IR.Node.*;
import Compiler.Src.IR.Node.Def.*;
import Compiler.Src.IR.Node.Inst.*;
import Compiler.Src.IR.Node.Stmt.IRStmt;
import Compiler.Src.IR.Type.*;
import Compiler.Src.IR.Util.IRControl;
import Compiler.Src.IR.Util.IRCounter;
import Compiler.Src.Util.Error.*;
import Compiler.Src.Util.Info.ExprInfo;
import Compiler.Src.Util.Info.FuncInfo;
import Compiler.Src.Util.Info.TypeInfo;
import Compiler.Src.Util.Info.VarInfo;
import Compiler.Src.Util.ScopeUtil.BaseScope;
import Compiler.Src.Util.ScopeUtil.GlobalScope;

public class IRBuilder extends IRControl implements ASTVisitor<IRNode> {
    @Override
    public IRNode visit(ASTNode node) throws BaseError {
        throw new IRError("IRBuilder.visit(ASTNode) should not be called");
    }

    @Override
    public IRNode visit(ASTRoot node) throws BaseError {
        enterASTNode(node);
        var program = new IRRoot();
        for (var def : node.getDefNodes()) {
            if (def instanceof ASTClassDef) {
                var ClassNode = (ASTClassDef) def;
                var defs = new ArrayList<IRType>();
                for (var vardef : ClassNode.getVars()) {
                    defs.add(new IRType(((VarInfo) vardef.getInfo()).getType()));
                }
                var typename = "%class." + def.findName();
                var structtype = new IRStructType(typename, defs);
                program.addDef(new IRGlobalDef(new IRVariable(structtype, typename)));
            }
        }
        for (var def : node.getDefNodes()) {
            if (def instanceof ASTClassDef) {
                var classDef = (IRClassDef) def.accept(this);
                for (var func : classDef.getFuncs()) {
                    program.addFunc(func);
                }
            }
        }
        for (var def : node.getDefNodes()) {
            if (def instanceof ASTFuncDef) {
                var funcDef = (IRFuncDef) def.accept(this);
                if (def.findName().equals("main")) {
                    funcDef.getBlockstmts().get(0).addFront(new IRCall("main.global.init", new ArrayList<>()));
                }
                program.addFunc(funcDef);
            }
        }
        for (var def : node.getDefNodes()) {
            if (def instanceof ASTVarDef) {
                program.addDef((IRGlobalDef) def.accept(this));
            }
        }
        var init = initFunc.getBlockstmts().get(0);
        init.setReturnInst(new IRRet());
        program.addFunc(initFunc);
        for (var str : strDefs) {
            program.addDef(str);
        }
        exitASTNode(node);
        return program;
    }

    @Override
    public IRNode visit(ASTFuncDef node) throws BaseError {
        enterASTNode(node);
        var functype = new IRType(node.getReturnType());
        var params = new ArrayList<IRVariable>();
        for (var param : node.getParams()) {
            if (!param.findName().equals("this")) {
                currentScope.IRdeclare(param.findName());
            }
            params.add(new IRVariable(new IRType(param.getVarType()), getVarName(param.findName(), currentScope)));
        }
        var stmts = new IRStmt();
        for (var stmt : node.getBlockedBody().getStmts()) {
            stmts.addInsts((IRInst) stmt.accept(this));
        }
        var func = new IRFuncDef(node.findName(), params, functype, stmt2block(stmts, functype));
        exitASTNode(node);
        return func;
    }

    @Override
    public IRNode visit(ASTClassDef node) throws BaseError {
        enterASTNode(node);
        var classVars = new ArrayList<IRType>();
        var classFunc = new ArrayList<IRFuncDef>();
        for (var vardef : node.getVars()) {
            classVars.add(new IRType(vardef.getVarType()));
        }
        var constructor = node.getConstructor();
        constructor.getParams().add(0,
                ASTVarDef.builder().info(new VarInfo("this", new TypeInfo(node.findName(), 0))).initexpr(null).build());
        var IRconstructor = (IRFuncDef) constructor.accept(this);
        IRconstructor.setName("class.constructor." + node.findName());
        classFunc.add(IRconstructor);
        for (var func : node.getFuncs()) {
            func.getParams().add(0, ASTVarDef.builder().pos(node.getPos())
                    .info(new VarInfo("this", new TypeInfo(node.findName(), 0))).initexpr(null).build());
            var IRfunc = (IRFuncDef) func.accept(this);
            IRfunc.setName("class.method." + node.findName() + "." + func.findName());
            classFunc.add(IRfunc);
        }
        var classDef = new IRClassDef(classVars, classFunc);
        exitASTNode(node);
        return classDef;
    }

    @Override
    public IRNode visit(ASTVarDef node) throws BaseError {
        enterASTNode(node);
        var varType = new IRVariable(new IRType(node.getVarType()), getVarName(node.findName(), currentScope));
        var instList = new IRStmt();
        currentScope.IRdeclare(node.findName());
        if (node.getInitexpr() != null) {
            var initexpr = ASTAssignExpr.builder().pos(node.getPos())
                    .Info(new ExprInfo("assignExpr", node.getVarType(), false))
                    .left(ASTAtomExpr.builder().pos(node.getPos())
                            .Info(new ExprInfo("atomExpr", node.getVarType(), false))
                            .atomType(ASTAtomExpr.Type.INDENTIFIER).value(node.findName()).constarray(null)
                            .fstring(null).build())
                    .right(node.getInitexpr()).build();
            var initInst = (IRStmt) initexpr.accept(this);
            if (currentScope instanceof GlobalScope) {
                initFunc.getBlockstmts().get(0).addBlockInsts(initInst);
            } else {
                instList.addBlockInsts(initInst);
            }
        }
        if (currentScope instanceof GlobalScope) {
            exitASTNode(node);
            return new IRGlobalDef(varType);
        }
        instList.addFront(new IRAlloca(varType, new IRType(node.getVarType())));
        exitASTNode(node);
        return instList;
    }

    @Override
    public IRNode visit(ASTConstarray node) throws BaseError {
        enterASTNode(node);
        var instList = new IRStmt();
        var args = node.collectArgs(strDefs);
        var typename = TypeInfo2Name((TypeInfo) node.getInfo().getType());
        typename = typename.equals("void") ? "ptr" : typename;
        var typesize = new IRLiteral(GlobalScope.irIntType, name2Size.get(typename).toString());
        args.add(0,typesize);
        var dest = new IRVariable(GlobalScope.irPtrType, "%constarray." + (++counter.allocaCount));
        instList.addInsts(new IRCall(dest, GlobalScope.irPtrType, "__malloc_const_array", args));
        instList.setDest(dest);
        exitASTNode(node);
        return instList;
    }

    @Override
    public IRNode visit(ASTFstring node) throws BaseError {
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTNewExpr node) throws BaseError {
        enterASTNode(node);
        var instList = new IRStmt();
        var newtype = node.getType();
        if (newtype.getDepth() == 0) {
            var allocaVar = new IRVariable(GlobalScope.irIntType, "%alloca." + (++counter.allocaCount));
            var stmts = alloca_unit(newtype,allocaVar);
            instList.addBlockInsts(stmts);
            instList.setDest(allocaVar);
        } else {
            // var instList1 = new IRStmt();
            // var args = new ArrayList<IREntity>();
            // for (var sizeinfo : node.getSize()) {
            //     var irSizeinfo = (IRStmt) sizeinfo.accept(this);
            //     instList1.addBlockInsts(irSizeinfo);
            //     args.add(irSizeinfo.getDest());
            // }
            // var typesize = new IRLiteral(GlobalScope.irIntType, name2Size.get(TypeInfo2Name(newtype)).toString());
            // var typedepth = new IRLiteral(GlobalScope.irIntType, String.valueOf(newtype.getDepth()));
            // var typeinited = new IRLiteral(GlobalScope.irIntType, String.valueOf(args.size()));
            // args.add(0, typesize);
            // args.add(1, typedepth);
            // args.add(2, typeinited);
            // var dest = new IRVariable(GlobalScope.irPtrType,
            //         "%new." + newtype.getName() + "." + (++counter.allocaCount));
            // var allocaCall = new IRCall(dest, GlobalScope.irPtrType, "__array_alloca", args);
            // instList1.addInsts(allocaCall);
            // instList1.setDest(dest);
            // var instList2 = new IRStmt();
            // if (node.getConstarray() != null) {
            //     var constarrayStmts = (IRStmt) node.getConstarray().accept(this);
            //     instList2.addBlockInsts(constarrayStmts);
            //     instList2.addInsts(new IRStore(dest, constarrayStmts.getDest()));
            //     instList2.setDest(dest);
            // }
            // instList.addBlockInsts(instList1);
            // instList.addBlockInsts(instList2);
            // instList.setDest(dest);
            if(node.getSize()!=null)
            {
                var depth=node.getType().getDepth();
                var args = new ArrayList<IREntity>();
                for(var sizeinfo:node.getSize())
                {
                    var irSizeinfo = (IRStmt) sizeinfo.accept(this);
                    instList.addBlockInsts(irSizeinfo);
                    args.add(irSizeinfo.getDest());
                }
                var innerType = new TypeInfo(node.getType().getName(),0);
                var dest=new IRVariable(GlobalScope.irPtrType,"%new."+node.getType().getName()+"."+(++counter.allocaCount));
                instList.addBlockInsts(initArray(args, depth, 0, innerType, dest));
                instList.setDest(dest);
            }
            else if(node.getConstarray()!=null){
                
            }
        }
        exitASTNode(node);
        return instList;
    }

    @Override
    public IRNode visit(ASTMemberExpr node) throws BaseError {
        enterASTNode(node);
        
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTCallExpr node) throws BaseError {
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTArrayExpr node) throws BaseError {
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTUnaryExpr node) throws BaseError {
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTPreunaryExpr node) throws BaseError {
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTBinaryExpr node) throws BaseError {
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTConditionalExpr node) throws BaseError {
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTAssignExpr node) throws BaseError {
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTAtomExpr node) throws BaseError {
        enterASTNode(node);
        var instList = new IRStmt();
        if (node.getAtomType() == ASTAtomExpr.Type.INDENTIFIER) {
            var info = currentScope.IRBackSearch(node.getValue());
            if (info instanceof VarInfo) {
                BaseScope scope = currentScope.IRBackSearchScope(node.getValue());
                var irtype = new IRType(((VarInfo) info).getType());
                var src = new IRVariable(GlobalScope.irPtrType, getVarName(node.getValue(), scope));
                var dest = new IRVariable(irtype, "%load." + (++counter.loadCount));
                instList.addInsts(new IRLoad(dest, src));
                instList.setDest(dest);
                instList.setDestAddr(src);
            } else if (info instanceof FuncInfo) {
                instList.setDest(new IRFunc(node.getValue(), null, new IRType(((FuncInfo) info).getFunctype())));
            } else {
                throw new IRError("IRBuilder.visit(ASTAtomExpr) should not be called");
            }
        } else if (node.getAtomType() == ASTAtomExpr.Type.INT) {
            instList.setDest(new IRLiteral(GlobalScope.irIntType, node.getValue()));
        } else if (node.getAtomType() == ASTAtomExpr.Type.BOOL) {
            instList.setDest(new IRLiteral(GlobalScope.irBoolType, node.getValue()));
        } else if (node.getAtomType() == ASTAtomExpr.Type.STRING) {
            var dest = new IRVariable(GlobalScope.irPtrType, "@str." + (++IRCounter.strCount));
            var str = new IRStrDef(dest, node.getValue());
            strDefs.add(str);
            instList.setDest(dest);
        } else if (node.getAtomType() == ASTAtomExpr.Type.NULL) {
            instList.setDest(new IRLiteral(GlobalScope.irPtrType, "null"));
        } else if (node.getAtomType() == ASTAtomExpr.Type.THIS) {
            var src = new IRVariable(GlobalScope.irPtrType, "%this");
            var dest = new IRVariable(GlobalScope.irPtrType, "%load." + (++counter.loadCount));
            instList.addInsts(new IRLoad(dest, src));
            instList.setDest(dest);
            instList.setDestAddr(src);
        } else if (node.getAtomType() == ASTAtomExpr.Type.FSTRING) {
            var dest = new IRVariable(GlobalScope.irPtrType, "@str." + (++IRCounter.strCount));
            instList.addBlockInsts((IRStmt) node.getFstring().accept(this));
            instList.setDest(dest);
        } else if (node.getAtomType() == ASTAtomExpr.Type.CONSTARRAY) {
            var irType = new IRType((TypeInfo) node.getConstarray().getInfo().getType());
            var dest = new IRVariable(irType, "@constarray." + (++counter.constarrayCount));
            instList.addBlockInsts((IRStmt) node.getConstarray().accept(this));
            instList.setDest(dest);
        }
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTParenExpr node) throws BaseError {
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTBlockstatement node) throws BaseError {
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTBreakstatement node) throws BaseError {
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTContinuestatement node) throws BaseError {
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTEmptystatement node) throws BaseError {
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTExpressionstatement node) throws BaseError {
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTForstatement node) throws BaseError {
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTIfstatement node) throws BaseError {
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTReturnstatement node) throws BaseError {
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTVarstatement node) throws BaseError {
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTWhilestatement node) throws BaseError {
        return new IRNode();
    }
}
