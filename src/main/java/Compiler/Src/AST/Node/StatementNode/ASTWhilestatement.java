package Compiler.Src.AST.Node.StatementNode;

import Compiler.Src.Util.ScopeUtil.*;
import Compiler.Src.AST.ASTVisitor;
import Compiler.Src.AST.Node.ExprNode.ASTExpr;
import Compiler.Src.Util.Error.*;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class ASTWhilestatement extends ASTStatement {
    private LoopScope scope;
    private final ASTExpr judge;
    private final ASTStatement stmts;
    @Override
    public <T> T accept(ASTVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }

    public void addScope(BaseScope scope) {
        if (this.scope == null) {
            this.scope = new LoopScope(scope,null);
        }
    }

    public LoopScope getScope() {
        return getScope();
    }
}
