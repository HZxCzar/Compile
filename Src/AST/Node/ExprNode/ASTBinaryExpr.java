package Compile.Src.AST.Node.ExprNode;

import AST.ASTNode;
import AST.ASTVisitor;
import Compile.Src.Util.Info.ExprInfo;
import Compile.Src.AST.Node.Expr.ASTExpr;
import Compile.Src.Util.Info.*;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class ASTBinaryExpr extends ASTExpr {
    private final ASTExpr left,right;
    private final String op;
    @Override
    public <T> T accept(ASTVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }
}

