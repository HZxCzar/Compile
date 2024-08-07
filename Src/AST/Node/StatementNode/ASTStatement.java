package Compiler.Src.AST.Node.StatementNode;

import AST.ASTNode;
import AST.ASTVisitor;
import Compiler.Src.Util.Info.StmtInfo;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class ASTStatement extends ASTNode {
    @Override
    public <T> T accept(ASTVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }
}
