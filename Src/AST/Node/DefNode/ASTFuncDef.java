package Compile.Src.AST.Node.DefNode;

import java.util.ArrayList;

import Compile.src.Util.Error.*;
import Compile.Src.AST.Node.ASTNode;
import Compile.Src.AST.Node.Statement.ASTBlockstatement;
import Compile.Src.Util.Info.ClassInfo;
import Compile.Src.Util.Info.FuncInfo;
import Compile.Src.AST.ASTVisitor;
import Compile.Src.Util.ScopeUtil.*;
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
            this.funcscope = new ClassScope(scope, (FuncInfo) getInfo());
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
