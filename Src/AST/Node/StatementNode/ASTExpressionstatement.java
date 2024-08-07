package Compile.Src.AST.Node.StatementNode;

import AST.ASTNode;
import AST.ASTVisitor;
import Compile.Src.Util.Info.StmtInfo;
import Compile.Src.AST.Node.Expr.ASTExpr;
import Compile.Src.AST.Node.Statement.ASTStatement;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class ASTExpressionstatement extends ASTStatement {
    private final ASTExpr expr;
    @Override
    public <T> T accept(ASTVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }
}
