package Compiler.Src.Semantic;

import java.lang.ProcessBuilder.Redirect.Type;

import Compiler.Src.AST.ASTVisitor;
import Compiler.Src.AST.Node.ASTRoot;
import Compiler.Src.AST.Node.ASTNode;
import Compiler.Src.AST.Node.DefNode.*;
import Compiler.Src.AST.Node.ExprNode.*;
import Compiler.Src.AST.Node.ExprNode.ExprUnitNode.*;
import Compiler.Src.AST.Node.StatementNode.*;
import Compiler.Src.Util.Error.*;
import Compiler.Src.Util.Info.*;
import Compiler.Src.Util.ScopeUtil.*;
import Compiler.Src.Semantic.*;

@lombok.Getter
@lombok.Setter

public class SemanticChecker extends ScopeControl implements ASTVisitor<SMCError> {
    public SMCError visit(ASTNode node) {
        return new SMCError("SMC should not visit ASTNode\n");
    }

    public SMCError visit(ASTRoot node) {
        node.addScope(null);
        enterScope((GlobalScope) node.getScope());
        var msg = new SMCError();
        for (var def : node.getDefNodes()) {
            msg.append(def.accept(this));
        }
        exitScope();
        return msg;
    }

    public SMCError visit(ASTClassDef node) {
        node.addScope(currentScope);
        enterScope((ClassScope) node.getScope());
        var msg = new SMCError();
        msg.append(node.getConstructor().accept(this));
        for (var def : node.getFuncs()) {
            msg.append(def.accept(this));
        }
        exitScope();
        return msg;
    }

    public SMCError visit(ASTFuncDef node)// 处理传参
    {
        node.addScope(currentScope);
        enterScope((FuncScope) node.getScope());
        var msg = new SMCError();
        for (var param : node.getParams()) {
            msg.append(param.accept(this));
        }
        msg.append(node.getBlockedBody().accept(this));
        exitScope();
        return msg;
    }

    public SMCError visit(ASTVarDef node) {
        var msg = new SMCError();
        VarInfo info = node.getInfo();
        if (!ValidVarType(info.getType())) {
            return new SMCError("Invalid type\n");
        } else if (currentScope.contains(node.getName())) {
            return new SMCError("redefination\n");
        } else {
            if (node.getInitexpr() != null) {
                msg.append(node.getInitexpr().accept(this));
                BaseInfo type = node.getInitexpr().getInfo().getType();
                if (!(type instanceof TypeInfo || type.equals(info.getType()))) {
                    return new SMCError("Incorrect assignment\n");
                }
            }
            currentScope.declare(new VarInfo(node.getName(), info.getType()));
        }
        return msg;
    }

    public SMCError visit(ASTArrayExpr node) {
        var msg = new SMCError();
        msg.append(node.getArrayName().accept(this));
        BaseInfo type = node.getArrayName().getInfo().getType();
        if (!(type instanceof TypeInfo || ((TypeInfo) type).getDepth() == 0)) {
            return new SMCError("Can not access such non-array type\n");
        }
        msg.append(node.getIndex().accept(this));
        BaseInfo index_type = node.getIndex().getInfo().getType();
        if (!(index_type instanceof TypeInfo || index_type.equals(GlobalScope.intType))) {
            return new SMCError("not a correct index for arrayExpr\n");
        }
        node.setInfo(new ExprInfo("ArrayExpr", new TypeInfo(type.getName(), ((TypeInfo) type).getDepth() - 1), true));
        return msg;
    }

    public SMCError visit(ASTAssignExpr node) {
        var msg = new SMCError();
        msg.append(node.getLeft().accept(this));
        BaseInfo LexprInfo = node.getLeft().getInfo();
        msg.append(node.getRight().accept(this));
        BaseInfo RexprInfo = node.getRight().getInfo();
        if (!(LexprInfo instanceof ExprInfo || RexprInfo instanceof ExprInfo)) {
            return new SMCError("Have none ExprInfo expression\n");
        }
        if (!((ExprInfo) LexprInfo).getType().equals(((ExprInfo) RexprInfo).getType())) {
            return new SMCError("lhs and rhs is not the same type\n");
        }
        if (!(LexprInfo.isLvalue())) {
            return new SMCError("left hand side is a left value\n");
        }
        node.setInfo(new ExprInfo("assignExpr", ((ExprInfo) LexprInfo).getType(), true));
        return msg;
    }

    public SMCError visit(ASTAtomExpr node)
    {
        var msg = new SMCError();
        if(node.getAtomType()==ASTAtomExpr.Type.INT)
        {
            node.setInfo(new ExprInfo("atomExpr",new TypeInfo(GlobalScope.intType,0), false));
        }
        else if(node.getAtomType()==ASTAtomExpr.Type.BOOL)
        {
            node.setInfo(new ExprInfo("atomExpr",new TypeInfo(GlobalScope.boolType,0), false));
        }
        else if(node.getAtomType()==ASTAtomExpr.Type.STRING)
        {
            node.setInfo(new ExprInfo("atomExpr",new TypeInfo(GlobalScope.stringType,0), false));
        }
        else if(node.getAtomType()==ASTAtomExpr.Type.NULL)
        {
            node.setInfo(new ExprInfo("atomExpr",new TypeInfo(GlobalScope.nullType,0), false));
        }
        else if(node.getAtomType()==ASTAtomExpr.Type.FSTRING)
        {
            msg.append(node.getFstring().accept(this));
            node.setInfo(new ExprInfo("atomExpr", new TypeInfo(GlobalScope.fstringType, 0), false));
        }
        else if(node.getAtomType()==ASTAtomExpr.Type.CONSTARRAY)
        {
            msg.append(node.getConstarray().accept(this));
            int depth=node.getConstarray().getExpr(0).getInfo().getType().getDepth();
            for(var unit:node.getConstarray().getExpr())
            {
                int size=((TypeInfo)unit.getInfo().getType()).getDepth();
                if(depth!=size)
                {
                    return new SMCError("each expr in costarray must have the same depth\n");
                }
                else if(((ExprInfo)unit.getInfo()).isLvalue())
                {
                    return new SMCError("constarray shouldn't contain Lvalue expression\n");   
                }
            }
            depth=depth+1;
            node.setInfo(new ExprInfo("atomExpr", new TypeInfo(GlobalScope.constarrayType, depth), false));
        }
        else if(node.getAtomType()==ASTAtomExpr.Type.INDENTIFIER)
        {
            BaseInfo info=currentScope.BackSearch(node.getValue());
            if(info==null)
            {
                return new msg("Undefined indentifier\n");
            }
            else if(info instanceof VarInfo)
            {
                node.setInfo(new ExprInfo("atomExpr", info.getType(), true));
            }
            else if(info instanceof FuncInfo)
            {
                node.setInfo(new ExprInfo("atomExpr", info.getFunctype(), false));
            }
            else{
                return new msg("Identifier is possiblily a class name, shouldn't be here\n");
            }
        }
        else if (node.getAtomType()==ASTAtomExpr.Type.THIS)
        {
            BaseScope scope=whichClass(currentScope);
            if(scope==null)
            {
                return new SMCError("Invalid use of THIS out of a ClassScope\n");
            }
            node.setInfo(new ExprInfo("atomExpr", new TypeInfo(scope.getInfo().getName(), 0), true));
        }
        else{
            return new SMCError("Invalid AtomExprn\n");
        }
        return msg;
    }
}
