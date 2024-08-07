package Compiler.Src.AST.Node.DefNode;

import java.util.ArrayList;

import Compiler.src.Util.Error.*;
import Compiler.Src.AST.Node.ASTNode;
import Compiler.Src.AST.Node.Statement.ASTBlockstatement;
import Compiler.Src.Util.Info.ClassInfo;
import Compiler.Src.Util.Info.FuncInfo;
import Compiler.Src.AST.ASTVisitor;
import Compiler.Src.Util.ScopeUtil.*;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class ASTFuncDef extends ASTDef {
    private final FuncScope funcscope;
    private final ArrayList<ASTVarDef> params;
    private final ASTBlockstatement blockedBody;
    @Override
    public <T> T accept(ASTVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }

    @Override
    public void addScope(BaseScope scope) {
        if (this.funcscope == null) {
            this.funcscope = new FuncScope(scope, (FuncInfo) getInfo());
        }
    }

    @Override
    public FuncScope getScope() {
        return getFuncscope();
    }

    @Override
    public String str()
    {
        String ret=((FuncInfo)getInfo()).getFunctype().str()+" "+getName()+"(";
        for(int i=0;i<params.size();i++)
        {
            ret+=params.get(i).str();
            if(i!=params.size()-1)
            {
                ret+=", ";
            }
        }
        ret+=")\n";
        ret+=blockedBody.str();
        return ret;
    }
}
