package Compiler.Src.AST;

import Compiler.Src.AST.Node.*;
import Compiler.Src.AST.Node.DefNode.*;
import Compiler.Src.AST.Node.ExprNode.*;
import Compiler.Src.AST.Node.StatementNode.*;
import Compiler.Src.AST.Node.ExprNode.ExprUnitNode.*;
import Compiler.Src.Util.Error.*;


public interface ASTVisitor<T> {
    public T visit(ASTNode node) throws ASTError;
    public T visit(ASTRoot node) throws ASTError;

    public T visit(ASTFuncDef node) throws ASTError;
    public T visit(ASTClassDef node) throws ASTError;
    public T visit(ASTVarDef node) throws ASTError;

    public T visit(ASTNewExpr node) throws ASTError;
    public T visit(ASTMemberExpr node) throws ASTError;
    public T visit(ASTCallExpr node) throws ASTError;
    public T visit(ASTArrayExpr node) throws ASTError;
    public T visit(ASTUnaryExpr node) throws ASTError;
    public T visit(ASTPreunaryExpr node) throws ASTError;
    public T visit(ASTBinaryExpr node) throws ASTError;
    public T visit(ASTConditionalExpr node) throws ASTError;
    public T visit(ASTAssignExpr node) throws ASTError;
    public T visit(ASTAtomExpr node) throws ASTError;
    public T visit(ASTParenExpr node) throws ASTError;

    public T visit(ASTBlockstatement node) throws ASTError;
    public T visit(ASTBreakstatement node) throws ASTError;
    public T visit(ASTContinuestatement node) throws ASTError;
    public T visit(ASTEmptystatement node) throws ASTError;
    public T visit(ASTExprstatement node) throws ASTError;
    public T visit(ASTForstatement node) throws ASTError;
    public T visit(ASTIfstatement node) throws ASTError;
    public T visit(ASTReturnstatement node) throws ASTError;
    public T visit(ASTVarstatement node) throws ASTError;
    public T visit(ASTWhilestatement node) throws ASTError;
}