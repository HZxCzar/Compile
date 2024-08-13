package Compiler.Src.IR.Node.Def;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.Util.Error.BaseError;

public class IRStrDef extends IRDef {
    public String name;
    public String value_old;
    public String value;
    public IRStrDef(String name, String value_old) {
        this.name = name;
        this.value_old = value_old;
    }
    @Override
    public <T> T accept(IRVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }
}
