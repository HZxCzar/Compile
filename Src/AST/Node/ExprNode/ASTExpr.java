package Compile.Src.AST.Node.ExprNode;

import AST.ASTNode;
import AST.ASTVisitor;
import Compile.Src.Util.Info.ExprInfo;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class ASTExpr extends ASTNode {
    private final ExprInfo Info;
    
    @Override
    public <T> T accept(ASTVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }
}
