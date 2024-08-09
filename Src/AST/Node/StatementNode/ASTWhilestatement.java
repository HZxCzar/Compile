package Compiler.Src.AST.Node.StatementNode;

import AST.ASTNode;
import AST.ASTVisitor;
import Compiler.Src.Util.Info.ExprInfo;
import Compiler.Src.Util.Info.StmtInfo;
import Compiler.Src.Util.ScopeUtil.BaseScope;
import Compiler.Src.Util.ScopeUtil.LoopScope;
import Compiler.Src.Util.Scope.*;
import Compiler.Src.AST.Node.Expr.ASTExpr;
import Compiler.Src.AST.Node.Statement.ASTStatement;
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

    @Override
    public void addScope(BaseScope scope) {
        if (this.scope == null) {
            this.scope = new LoopScope(scope, (ExprInfo) getInfo());
        }
    }

    @Override
    public LoopScope getScope() {
        return getScope();
    }
}
