package Compiler.Src.Util.Info;

import java.util.ArrayList;
@lombok.Getter
@lombok.Setter
public class ExprInfo extends BaseInfo {
    private TypeInfo type;
    private boolean isLvalue;
    public ExprInfo(String name,TypeInfo type,boolean isLvalue)
    {
        super(name);
        this.type=type;
        this.isLvalue=isLvalue;
    }
}
