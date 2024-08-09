package Compiler.Src.AST.Node.StatementNode;

import AST.ASTNode;
import AST.ASTVisitor;
import Compiler.Src.Util.Info.ClassInfo;
import Compiler.Src.Util.Info.StmtInfo;
import Compiler.Src.Util.ScopeUtil.BaseScope;
import Compiler.Src.Util.ScopeUtil.ClassScope;
import Compiler.Src.Util.ScopeUtil.LoopScope;
import Compiler.Src.Util.Scope.*;
import Compiler.Src.AST.Node.Expr.ASTExpr;
import Compiler.Src.AST.Node.Statement.ASTStatement;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class ASTForstatement extends ASTStatement {
    private LoopScope scope;
    private final ASTVarstatement Varinit;
    private final ASTExpr Exprinit,cond,step;
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
