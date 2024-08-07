package Compile.Src.AST.Node;

import java.util.ArrayList;

import Compile.Src.AST.ASTVisitor;
import Compile.Src.AST.Node;
import Compile.Src.AST.Node.DefNode.ASTDef;
import Compile.Src.Util.position;
import Compile.Src.Util.Error.ASTError;
import Compile.Src.Util.Scope.GlobalScope;
import Compile.Src.Util.ScopeUtil.BaseScope;

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
