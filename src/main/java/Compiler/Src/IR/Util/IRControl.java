package Compiler.Src.IR.Util;

import Compiler.Src.Util.ScopeUtil.BaseScope;
import Compiler.Src.Util.ScopeUtil.GlobalScope;

import java.util.ArrayList;
import java.util.TreeMap;

import Compiler.Src.AST.Node.ASTNode;
import Compiler.Src.AST.Node.ExprNode.ASTAssignExpr;
import Compiler.Src.AST.Node.ExprNode.ASTAtomExpr;
import Compiler.Src.AST.Node.ExprNode.ASTNewExpr;
import Compiler.Src.AST.Node.StatementNode.ASTForstatement;
import Compiler.Src.AST.Node.StatementNode.ASTIfstatement;
import Compiler.Src.AST.Node.Util.ASTScopedNode;
import Compiler.Src.Util.Error.*;
import Compiler.Src.Util.Info.ExprInfo;
import Compiler.Src.Util.Info.TypeInfo;
import Compiler.Src.IR.Entity.*;
import Compiler.Src.IR.Node.Def.IRFuncDef;
import Compiler.Src.IR.Node.Def.IRStrDef;
import Compiler.Src.IR.Node.Inst.*;
import Compiler.Src.IR.Node.Stmt.*;
import Compiler.Src.IR.Node.util.IRLabel;
import Compiler.Src.IR.Type.IRType;

public class IRControl {
    protected GlobalScope globalScope;
    protected BaseScope currentScope;
    protected IRCounter counter;
    protected IRFuncDef initFunc;
    protected ArrayList<IRStrDef> strDefs;
    protected TreeMap<String, Integer> name2Size;

    public IRControl() {
        this.counter = new IRCounter();
        this.initFunc = new IRFuncDef("main.global.init", new ArrayList<IRVariable>(), GlobalScope.irVoidType,
                new ArrayList<IRBlock>());
        this.strDefs = new ArrayList<IRStrDef>();
        this.name2Size = new TreeMap<>();
        name2Size.put("i1", 1);
        name2Size.put("i32", 4);
        name2Size.put("ptr", 4);
    }

    public void enterASTNode(ASTNode node) {
        if (!(node instanceof ASTScopedNode)) {
            return;
        }
        var scope = ((ASTScopedNode) node).getScope();
        if (scope == null) {
            return;
        }
        currentScope = scope;
        if (globalScope == null) {
            if (!(currentScope instanceof GlobalScope)) {
                throw new IRError("Global scope not found");
            }
            globalScope = (GlobalScope) scope;
        }
    }

    public void enterASTIfNode(ASTIfstatement node, String kind) {
        if (kind.equals("then")) {
            currentScope = node.getIfScope();
        } else if (kind.equals("else")) {
            currentScope = node.getElseScope();
        } else {
            throw new IRError("Invalid if stmt node name");
        }
    }

    public void exitASTNode(ASTNode node) {
        if (node instanceof ASTScopedNode) {
            var scope = ((ASTScopedNode) node).getScope();
            if (scope != null) {
                currentScope = scope.getParent();
            }
        }
    }

    public void exitASTIfNode(ASTIfstatement node, String kind) {
        if (kind.equals("if")) {
            var scope = node.getIfScope();
            if (scope != null) {
                currentScope = scope.getParent();
            } else {
                throw new IRError("If scope not found");
            }
        } else if (kind.equals("else")) {
            var scope = node.getElseScope();
            if (scope != null) {
                currentScope = scope.getParent();
            }
        } else {
            throw new IRError("Invalid if stmt node name");
        }
    }

    protected String getVarName(String name, BaseScope scope) {
        if (name.equals("this")) {
            return "%" + name;
        }
        if (scope instanceof GlobalScope) {
            return "@" + name;
        }
        return "%" + name + ".depth." + scope.getScopedep();
    }

    public ArrayList<IRBlock> stmt2block(IRStmt stmts, IRType rType) {
        var blocks = new ArrayList<IRBlock>();
        blocks.add(0, new IRBlock(new IRLabel("")));
        var enterblock = new IRBlock(new IRLabel("entry"));
        for (var inst : stmts.getInsts()) {
            if (inst instanceof IRLabel) {
                if (blocks.get(blocks.size() - 1).getReturnInst() == null) {
                    throw new IRError("Block doesn't have return instruction");
                }
                blocks.add(new IRBlock((IRLabel) inst));
            } else {
                if (blocks.get(blocks.size() - 1).getReturnInst() != null) {
                    continue;
                }
                if (inst instanceof IRRet || inst instanceof IRBranch) {
                    blocks.get(blocks.size() - 1).setReturnInst(inst);
                } else if (inst instanceof IRAlloca) {
                    enterblock.addInsts(inst);
                } else {
                    blocks.get(blocks.size() - 1).addInsts(inst);
                }
            }
        }
        // var firstblock = blocks.get(0);
        // enterblock.addBlockInsts(firstblock);
        // enterblock.setReturnInst(firstblock.getReturnInst());
        var enterBranch2start = new IRLabel("start");
        enterblock.setReturnInst(
                new IRBranch(new IRVariable(GlobalScope.irBoolType, "true"), enterBranch2start, enterBranch2start));
        blocks.add(0, enterblock);
        return blocks;
    }

    protected String TypeInfo2Name(TypeInfo type) {
        if (type.getDepth() > 0 || type.equals(GlobalScope.stringType)) {
            return "ptr";
        } else if (type.equals(GlobalScope.intType)) {
            return "i32";
        } else if (type.equals(GlobalScope.boolType)) {
            return "i1";
        } else if (type.equals(GlobalScope.voidType)) {
            return "void";
        } else if (!type.isDefined()) {
            return "%class." + type.getName();
        } else {
            throw new IRError("Invalid type info in Typeinfo2Name");
        }
    }

    protected IRStmt alloca_unit(TypeInfo type, IRVariable allocaVar) {
        if (type.getDepth() > 0) {
            throw new IRError("Invalid alloca unit type");
        }
        var instList = new IRStmt();
        // var allocaVar = new IRVariable(GlobalScope.irIntType, "%alloca." +
        // (++counter.allocaCount));
        var args = new ArrayList<IREntity>();
        if(type.equals(GlobalScope.nullType)){
            args.add(new IRLiteral(GlobalScope.irIntType,"4"));
        }
        else{
            args.add(new IRLiteral(GlobalScope.irIntType, name2Size.get(TypeInfo2Name(type)).toString()));
        }
        var allocaCall = new IRCall(allocaVar, GlobalScope.irPtrType, "malloc", args);
        instList.addInsts(allocaCall);
        if (!type.isDefined()) {
            var constructorargs = new ArrayList<IREntity>();
            constructorargs.add(allocaVar);
            var constructorCall = new IRCall("%class." + type.getName() + ".constructor", constructorargs);
            instList.addInsts(constructorCall);
        }
        instList.setDest(allocaVar);
        return instList;
    }

    // protected IRStmt alloca_array(ArrayList<IREntity> args,int offset)
    // {
    // var instList = new IRStmt();
    // var loopnum=args.get(offset);
    // var loopNode=ASTForstatement.builder().
    // }

    protected void initSize(String name, ArrayList<IRType> types) {
        var totalSize = 0;
        for (var type : types) {
            totalSize += name2Size.get(type.getTypeName());
        }
        name2Size.put(name, totalSize);
    }

    protected IRStmt initArray(ArrayList<IREntity> args, int full_length, int depth, TypeInfo innerType,
            IRVariable mallocDest) {
        var stmts = new IRStmt();
        var init = new IRStmt();
        var cond = new IRStmt();
        var update = new IRStmt();
        var body = new IRStmt();
        // var mallocDest = new IRVariable(GlobalScope.irPtrType,
        // "%initArray.mallocDest." + depth + (++counter.ArrayCount));
        if (depth != args.size()) {
            var info = new ArrayList<IREntity>();
            var arraySize = new IRVariable(GlobalScope.irIntType,
                    "%initArray.arraySize." + depth + (++counter.ArrayCount));
            info.add(arraySize);
            init.addInsts(new IRLoad(arraySize, args.get(depth)));
            init.addInsts(new IRCall(mallocDest, GlobalScope.irPtrType, "__malloc_array_", info));

            var initVar = new IRVariable(GlobalScope.irPtrType,
                    getVarName("%initArray." + depth + (++counter.ArrayCount), currentScope));
            init.addInsts(new IRAlloca(initVar, GlobalScope.irIntType));
            var initbeg = new IRLiteral(GlobalScope.irIntType, "0");
            var cmpTarg = new IRVariable(GlobalScope.irIntType,
                    "%initArray.midArray." + depth + (++counter.ArrayCount));
            // var initmidVar= new IRVariable(GlobalScope.irIntType, "%initArray.midArray."
            // + depth + (++counter.ArrayCount));
            init.addInsts(new IRStore(initVar, initbeg));
            init.addInsts(new IRLoad(cmpTarg, args.get(depth)));
            var condDest = new IRVariable(GlobalScope.irBoolType, "%cond." + depth + (++counter.ArrayCount));
            var condmidVar = new IRVariable(GlobalScope.irIntType, "%cond.midArray." + depth + (++counter.ArrayCount));
            cond.addInsts(new IRLoad(condmidVar, initVar));
            cond.addInsts(new IRIcmp(condDest, "sgt", GlobalScope.irBoolType, condmidVar,
                    new IRLiteral(GlobalScope.irIntType, "0")));
            cond.setDest(condDest);
            var updatemidVar = new IRVariable(GlobalScope.irIntType,
                    "%initArray.update." + depth + (++counter.ArrayCount));
            var updatemidVar2 = new IRVariable(GlobalScope.irIntType,
                    "%initArray.update2." + depth + (++counter.ArrayCount));
            update.addInsts(new IRLoad(updatemidVar, initVar));
            update.addInsts(new IRArith(updatemidVar2, "add", updatemidVar, new IRLiteral(GlobalScope.irIntType, "1")));
            update.addInsts(new IRStore(initVar, updatemidVar2));
            var fetchDest = new IRVariable(GlobalScope.irPtrType,
                    "%initArray.fetchDest." + depth + (++counter.ArrayCount));
            var offset = new IRVariable(GlobalScope.irIntType,
                    "%initArray.offset." + depth + (++counter.ArrayCount));
            body.addInsts(new IRLoad(offset, initVar));
            var fetchargs = new ArrayList<IREntity>();
            fetchargs.add(offset);
            var fetch = new IRGetelementptr(fetchDest, GlobalScope.irPtrType, mallocDest, fetchargs);
            body.addInsts(fetch);
            body.addBlockInsts(initArray(args, full_length, depth + 1, innerType, fetchDest));
            var LoopNode = new IRLoop(depth, init, cond, update, body);
            stmts.addBlockInsts(LoopNode);
        } else {
            if (depth < full_length) {
                // 未完全定义
                stmts.addBlockInsts(alloca_unit(GlobalScope.nullType, mallocDest));
            } else {
                // 完全定义
                stmts.addBlockInsts(alloca_unit(innerType, mallocDest));
            }
        }
        stmts.setDest(mallocDest);
        return stmts;
    }
}
