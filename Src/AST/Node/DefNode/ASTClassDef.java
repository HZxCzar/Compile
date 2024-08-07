package Compiler.Src.AST.Node.DefNode;

import java.util.ArrayList;

import Compiler.Src.AST.ASTVisitor;
import Compiler.Src.AST.Node.Def.ASTDef;
import Compiler.Src.AST.Node.Def.ASTVarDef;
import Compiler.Src.Util.Info.*;
import Compiler.Src.Util.ScopeUtil.*;
import Compiler.src.Util.Error;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class ASTClassDef extends ASTDef {
    private ClassScope classScope;
    private ASTFuncDef constructor;
    private ArrayList<ASTVarDef> vars;
    private ArrayList<ASTFuncDef> funcs;

    @Override
    public <T> T accept(ASTVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }

    @Override
    public void addScope(BaseScope scope) {
        if (this.classScope == null) {
            this.classScope = new ClassScope(scope, (ClassInfo) getInfo());
        }
    }

    @Override
    public ClassScope getScope() {
        return getCurrentScope();
    }

    @Override
    public String str() {
        String ret = "Class " + getInfo().getName() + "{\n";
        for (var vname : vars.keySet()) {
            ret += vars.get(vname).str() + "\n";
        }
        ret += constructor.str() + "\n";
        for (var funcname : funcs.keySet()) {
            ret += funcs.get(funcname).str() + "\n";
        }
        ret += "}\n";
        return ret;
    }
}
