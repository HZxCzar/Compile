package Compiler.Src.Semantic;

import Compiler.Src.AST.ASTVisitor;
import Compiler.Src.AST.Node.ASTRoot;
import Compiler.Src.AST.Node.ASTNode;
import Compiler.Src.AST.Node.DefNode.*;
import Compiler.Src.AST.Node.ExprNode.*;
import Compiler.Src.AST.Node.ExprNode.ExprUnitNode.*;
import Compiler.Src.AST.Node.StatementNode.*;
import Compiler.Src.Util.Error.SBCError;
import Compiler.Src.Util.Info.*;
import Compiler.Src.Util.ScopeUtil.*;
import Compiler.Src.Semantic.*;

public class SymbolCollector extends ScopeControl implements ASTVisitor<SBCError> {
    public SBCError visit(ASTNode node) {
        throw ("SymbolCollector.visit(ASTNode) should not be called" + node.getPos());
    }

    public SBCError visit(ASTRoot node) {
        node.addScope(null);
        enterScope(node.getScope());
        var msg = new SBCError();
        for (ASTDef def : node.getDefNodes()) {
            if (def instanceof ASTClassDef || def instanceof ASTFuncDef) {
                if (currentScope.contains(def.getName())) {
                    return new SBCError(def.getName() + " is redefined" + def.getPos());
                } else {
                    currentScope.declare(def.getInfo());
                }
            }
        }
        for (ASTDef def : node.getDefNodes()) {
            if (def instanceof ASTClassDef || def instanceof ASTFuncDef) {
                msg.append(def.accept(this));
            }
        }
        if (!node.getScope().containsFuncs("main")) {
            return new SBCError("Main function is not defined" + node.getPos());
        }
        return msg;
    }

    public SBCError visit(ASTClassDef node) {
        node.addScope(currentScope);
        enterScope((ClassScope) node.getScope());
        var msg = new SBCError();
        for (ASTFuncDef def : node.getFuncs()) {
            if (currentScope.contains(def.getName())) {
                return new SBCError(def.getName() + " is redefined" + def.getPos());
            } else {
                msg.append(def.accept(this));
                currentScope.declare(def.getInfo());
            }
        }
        for (var def : node.getVars()) {
            msg.append(def.accept(this));
        }
        exitScope();
        return msg;
    }

    public SBCError visit(ASTFuncDef node) {
        node.addScope(currentScope);
        enterScope((FuncScope) node.getScope());
        if (node.getName().equals("main")) {
            if (node.getParams().size() > 0) {
                return new SBCError("Main function can not have args\n");
            } else if (((FuncInfo) node.getInfo()).getFunctype().equals(GlobalScope.intType)) {
                return new SBCError("Main function should return int type\n");
            }
        }
        if (!ValidFuncType(((FuncInfo) node.getInfo()).getFunctype())) {
            return new SBCError("Invalid ReturnType for Function " + node.getName());
        }
        var msg = new SBCError();
        for (var def : node.getParams())// Only accept params
        {
            msg.append(visit(def));
        }
        exitScope();
        return msg;
    }

    public SBCError visit(ASTVarDef node) {
        VarInfo info = node.getInfo();
        if (!ValidVarType(info.getType())) {
            return new SBCError("Invalid VarType\n");
        } else if (currentScope.contains(node.getName())) {
            return new SBCError("Redefination of " + node.getName() + "\n");
        } else {
            currentScope.declare(new VarInfo(node.getName(), info.getType()));
        }
        return new SBCError();
    }

    public SBCError visit(ASTDef node) {
        return new SBCError("SBC should not visit ASTDef node\n");
    }

    public SBCError visit(ASTConstarray node) {
        return new SBCError("SBC should not visit ASTConstarray node\n");
    }

    public SBCError visit(ASTFstring node) {
        return new SBCError("SBC should not visit ASTFstring node\n");
    }

    public SBCError visit(ASTArrayExpr node) {
        return new SBCError("SBC should not visit ASTArrayExpr node\n");
    }

    public SBCError visit(ASTAssignExpr node) {
        return new SBCError("SBC should not visit ASTAssignExpr node\n");
    }

    public SBCError visit(ASTAtomExpr node) {
        return new SBCError("SBC should not visit ASTAtomExpr node\n");
    }

    public SBCError visit(ASTBinaryExpr node) {
        return new SBCError("SBC should not visit ASTBinaryExpr node\n");
    }

    public SBCError visit(ASTCallExpr node) {
        return new SBCError("SBC should not visit ASTCallExpr node\n");
    }

    public SBCError visit(ASTConditionalExpr node) {
        return new SBCError("SBC should not visit ASTConditionalExpr node\n");
    }

    public SBCError visit(ASTMemberExpr node) {
        return new SBCError("SBC should not visit ASTMemberExpr node\n");
    }

    public SBCError visit(ASTNewExpr node) {
        return new SBCError("SBC should not visit ASTNewExpr node\n");
    }

    public SBCError visit(ASTParenExpr node) {
        return new SBCError("SBC should not visit ASTParenExpr node\n");
    }

    public SBCError visit(ASTPreunaryExpr node) {
        return new SBCError("SBC should not visit ASTPreunaryExpr node\n");
    }

    public SBCError visit(ASTUnaryExpr node) {
        return new SBCError("SBC should not visit ASTUnaryExpr node\n");
    }

    public SBCError visit(ASTExpr node) {
        return new SBCError("SBC should not visit ASTExpr node\n");
    }

    public SBCError visit(ASTBlockstatement node) {
        return new SBCError("SBC should not visit ASTBlockstatement node\n");
    }

    public SBCError visit(ASTBreakstatement node) {
        return new SBCError("SBC should not visit ASTBreakstatement node\n");
    }

    public SBCError visit(ASTContinuestatement node) {
        return new SBCError("SBC should not visit ASTContinuestatement node\n");
    }

    public SBCError visit(ASTEmptystatement node) {
        return new SBCError("SBC should not visit ASTEmptystatement node\n");
    }

    public SBCError visit(ASTExpressionstatement node) {
        return new SBCError("SBC should not visit ASTExpressionstatement node\n");
    }

    public SBCError visit(ASTForstatement node) {
        return new SBCError("SBC should not visit ASTForstatement node\n");
    }

    public SBCError visit(ASTIfstatement node) {
        return new SBCError("SBC should not visit ASTIfstatement node\n");
    }

    public SBCError visit(ASTReturnstatement node) {
        return new SBCError("SBC should not visit ASTReturnstatement node\n");
    }

    public SBCError visit(ASTVarstatement node) {
        return new SBCError("SBC should not visit ASTVarstatement node\n");
    }

    public SBCError visit(ASTWhilestatement node) {
        return new SBCError("SBC should not visit ASTWhilestatement node\n");
    }

    public SBCError visit(ASTStatement node) {
        return new SBCError("SBC should not visit ASTStatement node\n");
    }
}
