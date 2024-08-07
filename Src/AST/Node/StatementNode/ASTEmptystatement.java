package Compile.Src.AST.Node.StatementNode;

import java.util.ArrayList;

import AST.ASTNode;
import AST.ASTVisitor;
import Compile.Src.Util.Info.StmtInfo;
import Compile.Src.Util.ScopeUtil.*;
import Compile.Src.AST.Node.Statement.ASTStatement;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class ASTEmptystatement extends ASTStatement {
    @Override
    public <T> T accept(ASTVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }
}
