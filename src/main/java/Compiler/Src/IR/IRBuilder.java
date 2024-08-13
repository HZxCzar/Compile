package Compiler.Src.IR;

import Compiler.Src.AST.ASTVisitor;
import Compiler.Src.AST.Node.*;
import Compiler.Src.AST.Node.DefNode.ASTClassDef;
import Compiler.Src.AST.Node.DefNode.ASTFuncDef;
import Compiler.Src.AST.Node.DefNode.ASTVarDef;
import Compiler.Src.AST.Node.ExprNode.ASTArrayExpr;
import Compiler.Src.AST.Node.ExprNode.ASTAssignExpr;
import Compiler.Src.AST.Node.ExprNode.ASTAtomExpr;
import Compiler.Src.AST.Node.ExprNode.ASTBinaryExpr;
import Compiler.Src.AST.Node.ExprNode.ASTCallExpr;
import Compiler.Src.AST.Node.ExprNode.ASTConditionalExpr;
import Compiler.Src.AST.Node.ExprNode.ASTMemberExpr;
import Compiler.Src.AST.Node.ExprNode.ASTNewExpr;
import Compiler.Src.AST.Node.ExprNode.ASTParenExpr;
import Compiler.Src.AST.Node.ExprNode.ASTPreunaryExpr;
import Compiler.Src.AST.Node.ExprNode.ASTUnaryExpr;
import Compiler.Src.AST.Node.ExprNode.ExprUnitNode.ASTConstarray;
import Compiler.Src.AST.Node.ExprNode.ExprUnitNode.ASTFstring;
import Compiler.Src.AST.Node.StatementNode.ASTBlockstatement;
import Compiler.Src.AST.Node.StatementNode.ASTBreakstatement;
import Compiler.Src.AST.Node.StatementNode.ASTContinuestatement;
import Compiler.Src.AST.Node.StatementNode.ASTEmptystatement;
import Compiler.Src.AST.Node.StatementNode.ASTExpressionstatement;
import Compiler.Src.AST.Node.StatementNode.ASTForstatement;
import Compiler.Src.AST.Node.StatementNode.ASTIfstatement;
import Compiler.Src.AST.Node.StatementNode.ASTReturnstatement;
import Compiler.Src.AST.Node.StatementNode.ASTVarstatement;
import Compiler.Src.AST.Node.StatementNode.ASTWhilestatement;
import Compiler.Src.IR.Node.IRNode;
import Compiler.Src.IR.Util.IRControl;
import Compiler.Src.Util.Error.*;

public class IRBuilder extends IRControl implements ASTVisitor<IRNode> {
    @Override
    public IRNode visit(ASTNode node) throws BaseError {
        throw new IRError("IRBuilder.visit(ASTNode) should not be called");
    }

    @Override
    public IRNode visit(ASTRoot node) throws BaseError {
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTFuncDef node) throws BaseError {
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTClassDef node) throws BaseError {
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTVarDef node) throws BaseError {
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTConstarray node) throws BaseError {
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTFstring node) throws BaseError {
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTNewExpr node) throws BaseError {
        return new IRNode();
    }

    @Override
    public IRNode visit(ASTMemberExpr node) throws BaseError {
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
