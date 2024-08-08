package Compiler.Src.AST.Node.DefNode;

import Compiler.src.Util.Error;
import Compiler.Src.Util.Info.VarInfo;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class ASTVarDef extends ASTDef {
    private final ASTExpr initexpr;
    @Override
    public <T> T accept(ASTVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }
    @Override
    public String str()
    {
        String ret=((VarInfo)getInfo()).getType().str()+" "+getName();
        if(initexpr!=null)
        {
            ret+=" = "+initexpr.str();
        }
        ret+=";";
        return ret;
    }
}
