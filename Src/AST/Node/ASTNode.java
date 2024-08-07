package Compile.Src.AST.Node;


import Compile.Src.Util.position;
import Compile.Src.AST.*;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class ASTNode {
    protected ASTNode parent;
    protected position pos;

    public ASTNode(position pos) {
        this.pos = pos;
    }

    public <T> T accept(ASTVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }

    public String str() {
        return pos.str();
    }
}