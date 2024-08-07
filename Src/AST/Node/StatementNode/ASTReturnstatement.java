package Compiler.Src.AST.Node.StatementNode;

import AST.ASTNode;
import AST.ASTVisitor;
import Compiler.Src.Util.Info.StmtInfo;
import Compiler.Src.AST.Node.Expr.ASTExpr;
import Compiler.Src.AST.Node.Statement.ASTStatement;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class ASTReturnstatement extends ASTStatement {
    private final ASTExpr ret;
    @Override
    public <T> T accept(ASTVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }
}
