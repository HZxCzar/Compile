package Compiler.Src.AST.Node;

import java.util.ArrayList;

import Compiler.Src.AST.ASTVisitor;
import Compiler.Src.AST.Node;
import Compiler.Src.AST.Node.DefNode.ASTDef;
import Compiler.Src.Util.position;
import Compiler.Src.Util.Error.ASTError;
import Compiler.Src.Util.Scope.GlobalScope;
import Compiler.Src.Util.ScopeUtil.BaseScope;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class ASTRoot extends ASTNode {
    private GlobalScope Gscope;
    private ArrayList<ASTDef> DefNodes;

    public void addDef(ASTDef def) {
        DefNodes.add(def);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) throws ASTError {
        return visitor.visit(this);
    }

    @Override
    public BaseScope getScope() {
        return getGscope();
    }

    @Override
    public void addScope(BaseScope scope) {
        if (this.Gscope == null) {
            this.Gscope = new GlobalScope();
        }
    }
}
