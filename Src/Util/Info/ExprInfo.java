package Compiler.Src.Util.Info;

import java.util.ArrayList;
@lombok.Getter
@lombok.Setter
public class ExprInfo extends BaseInfo {
    private BaseInfo type;
    private boolean isLvalue;
    public ExprInfo(String name,BaseInfo type,boolean isLvalue)
    {
        super(name);
        this.type=type;
        this.isLvalue=isLvalue;
    }
    
    public TypeInfo getDepTypeInfo()
    {
        BaseInfo type=this.getType();
        if(type instanceof TypeInfo)
        {
            return type;
        }
        else if(type instanceof VarInfo)
        {
            return ((VarInfo)type).getType();
        }
        else{
            return null;
        }
    }
}
