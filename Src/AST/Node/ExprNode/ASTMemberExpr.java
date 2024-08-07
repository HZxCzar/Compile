package Compiler.Src.AST.Node.ExprNode;

import AST.ASTNode;
import AST.ASTVisitor;
import Compiler.Src.Util.Info.ExprInfo;
import Compiler.Src.AST.Node.Expr.ASTExpr;
import Compiler.Src.Util.Info.*;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class ASTMemberExpr extends ASTExpr {
    private final ASTExpr member;
    private final String memberName;
    @Override
    public <T> T accept(ASTVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }
}
