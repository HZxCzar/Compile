package Compiler.Src.IR.Entity;

import Compiler.Src.IR.Type.IRType;
import Compiler.Src.Util.ScopeUtil.GlobalScope;

@lombok.Setter
@lombok.Getter
public abstract class IREntity{
    protected IRType type;
    protected String value;
    public IREntity(IRType type, String value) {
        if(type.equals(GlobalScope.irBoolType)){
            if(value.equals("true")){
                value = "1";
            }else if(value.equals("false")){
                value = "0";
            }
        }
        this.type = type;
        this.value = value;
    }

    public abstract String toString();
}
