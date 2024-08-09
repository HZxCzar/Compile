package Compiler.Src.Semantic;

import Compiler.Src.AST.ASTVisitor;
import Compiler.Src.AST.Node.ASTRoot;
import Compiler.Src.AST.Node.ASTNode;
import Compiler.Src.AST.Node.DefNode.*;
import Compiler.Src.AST.Node.ExprNode.*;
import Compiler.Src.AST.Node.ExprNode.ExprUnitNode.*;
import Compiler.Src.AST.Node.StatementNode.*;
import Compiler.Src.Util.Error.BaseError;
import Compiler.Src.Util.Error.SBCError;
import Compiler.Src.Util.Info.*;
import Compiler.Src.Util.ScopeUtil.*;

public class SymbolCollector extends ScopeControl implements ASTVisitor<SBCError> {
    public SBCError visit(ASTNode node) throws BaseError {
        return new SBCError("SymbolCollector.visit(ASTNode) should not be called");
    }

    public SBCError visit(ASTRoot node) throws BaseError {
        node.addScope(null);
        enterScope(node.getScope());
        SBCError msg = new SBCError();
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
        if (node.getScope().containsFuncs("main") == null) {
            return new SBCError("Main function is not defined" + node.getPos());
        }
        return msg;
    }

    public SBCError visit(ASTClassDef node) throws BaseError {
        node.addScope(currentScope);
        enterScope((ClassScope) node.getScope());
        SBCError msg = new SBCError();
        for (ASTFuncDef def : node.getFuncs()) {
            if (currentScope.contains(def.getName())) {
                return new SBCError(def.getName() + " is redefined" + def.getPos());
            } else {
                msg.append(def.accept(this));
                currentScope.declare(def.getInfo());
            }
        }
        for (ASTVarDef def : node.getVars()) {
            msg.append(def.accept(this));
        }
        exitScope();
        return msg;
    }

    public SBCError visit(ASTFuncDef node) throws BaseError {
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
        SBCError msg = new SBCError();
        // for (var def : node.getParams())// Only accept params
        // {
        // msg.append(visit(def));
        // }
        exitScope();
        return msg;
    }

    public SBCError visit(ASTVarDef node) throws BaseError {
        VarInfo info = (VarInfo) node.getInfo();
        if (!ValidVarType(info.getType())) {
            return new SBCError("Invalid VarType\n");
        } else if (currentScope.contains(node.getName())) {
            return new SBCError("Redefination of " + node.getName() + "\n");
        } else {
            currentScope.declare(new VarInfo(node.getName(), info.getType()));
        }
        return new SBCError();
    }

    public SBCError visit(ASTDef node) throws BaseError {
        return new SBCError("SBC should not visit ASTDef node\n");
    }

    public SBCError visit(ASTConstarray node) throws BaseError {
        return new SBCError("SBC should not visit ASTConstarray node\n");
    }

    public SBCError visit(ASTFstring node) throws BaseError {
        return new SBCError("SBC should not visit ASTFstring node\n");
    }

    public SBCError visit(ASTArrayExpr node) throws BaseError {
        return new SBCError("SBC should not visit ASTArrayExpr node\n");
    }

    public SBCError visit(ASTAssignExpr node) throws BaseError {
        return new SBCError("SBC should not visit ASTAssignExpr node\n");
    }

    public SBCError visit(ASTAtomExpr node) throws BaseError {
        return new SBCError("SBC should not visit ASTAtomExpr node\n");
    }

    public SBCError visit(ASTBinaryExpr node) throws BaseError {
        return new SBCError("SBC should not visit ASTBinaryExpr node\n");
    }

    public SBCError visit(ASTCallExpr node) throws BaseError {
        return new SBCError("SBC should not visit ASTCallExpr node\n");
    }

    public SBCError visit(ASTConditionalExpr node) throws BaseError {
        return new SBCError("SBC should not visit ASTConditionalExpr node\n");
    }

    public SBCError visit(ASTMemberExpr node) throws BaseError {
        return new SBCError("SBC should not visit ASTMemberExpr node\n");
    }

    public SBCError visit(ASTNewExpr node) throws BaseError {
        return new SBCError("SBC should not visit ASTNewExpr node\n");
    }

    public SBCError visit(ASTParenExpr node) throws BaseError {
        return new SBCError("SBC should not visit ASTParenExpr node\n");
    }

    public SBCError visit(ASTPreunaryExpr node) throws BaseError {
        return new SBCError("SBC should not visit ASTPreunaryExpr node\n");
    }

    public SBCError visit(ASTUnaryExpr node) throws BaseError {
        return new SBCError("SBC should not visit ASTUnaryExpr node\n");
    }

    public SBCError visit(ASTExpr node) throws BaseError {
        return new SBCError("SBC should not visit ASTExpr node\n");
    }

    public SBCError visit(ASTBlockstatement node) throws BaseError {
        return new SBCError("SBC should not visit ASTBlockstatement node\n");
    }

    public SBCError visit(ASTBreakstatement node) throws BaseError {
        return new SBCError("SBC should not visit ASTBreakstatement node\n");
    }

    public SBCError visit(ASTContinuestatement node) throws BaseError {
        return new SBCError("SBC should not visit ASTContinuestatement node\n");
    }

    public SBCError visit(ASTEmptystatement node) throws BaseError {
        return new SBCError("SBC should not visit ASTEmptystatement node\n");
    }

    public SBCError visit(ASTExpressionstatement node) throws BaseError {
        return new SBCError("SBC should not visit ASTExpressionstatement node\n");
    }

    public SBCError visit(ASTForstatement node) throws BaseError {
        return new SBCError("SBC should not visit ASTForstatement node\n");
    }

    public SBCError visit(ASTIfstatement node) throws BaseError {
        return new SBCError("SBC should not visit ASTIfstatement node\n");
    }

    public SBCError visit(ASTReturnstatement node) throws BaseError {
        return new SBCError("SBC should not visit ASTReturnstatement node\n");
    }

    public SBCError visit(ASTVarstatement node) throws BaseError {
        return new SBCError("SBC should not visit ASTVarstatement node\n");
    }

    public SBCError visit(ASTWhilestatement node) throws BaseError {
        return new SBCError("SBC should not visit ASTWhilestatement node\n");
    }

    public SBCError visit(ASTStatement node) throws BaseError {
        return new SBCError("SBC should not visit ASTStatement node\n");
    }
}
