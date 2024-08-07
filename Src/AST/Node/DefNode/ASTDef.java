package Compile.Src.AST.Node.DefNode;

import AST.ASTNode;
import AST.ASTVisitor;
import Compile.Src.Util.Info.*;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class ASTDef extends ASTNode {
    private final BaseInfo info;
    @Override
    public <T> T accept(ASTVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }
    @Override
    public String getName()
    {
        return getInfo().getName();
    }
}
