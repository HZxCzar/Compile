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
public class ASTIfstatement extends ASTStatement {
    private BaseScope itScope,elseScope;
    private final ASTExpr judge;
    private final ASTStatement ifstmt,elsestmt;
    @Override
    public <T> T accept(ASTVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }

    public void addIfScope(BaseScope scope) {
        if (this.scope == null) {
            this.scope = new BaseScope(scope, (ExprInfo) getInfo());
        }
    }

    public void addElseScope(BaseScope scope) {
        if (this.scope == null) {
            this.scope = new BaseScope(scope, (ExprInfo) getInfo());
        }
    }

    public BaseScope getIfScope() {
        return getIfScope();
    }

    public BaseScope getElseScope() {
        return getElseScope();
    }
}
