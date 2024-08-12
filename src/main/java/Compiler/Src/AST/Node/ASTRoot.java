package Compiler.Src.AST.Node;

import java.util.ArrayList;

import Compiler.Src.AST.ASTVisitor;
import Compiler.Src.AST.Node.DefNode.ASTDef;
import Compiler.Src.Util.Error.*;
import Compiler.Src.Util.ScopeUtil.*;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class ASTRoot extends ASTNode {
    public GlobalScope Gscope;
    public ArrayList<ASTDef> DefNodes;

    public void addDef(ASTDef def) {
        this.DefNodes.add(def);
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }

    public BaseScope findScope() {
        return Gscope;
    }

    public void addScope(BaseScope scope) {
        if (this.Gscope == null) {
            this.Gscope = new GlobalScope();
        }
    }
}
