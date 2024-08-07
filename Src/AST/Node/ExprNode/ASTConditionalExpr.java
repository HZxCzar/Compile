package Compiler.Src.AST.Node.ExprNode;

import AST.ASTNode;
import AST.ASTVisitor;
import Compiler.Src.Util.Info.ExprInfo;
import Compiler.Src.AST.Node.Expr.ASTExpr;
import Compiler.Src.Util.Info.*;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class ASTConditionalExpr extends ASTExpr {
    private final ASTExpr Ques,left,right;
    @Override
    public <T> T accept(ASTVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }
}
