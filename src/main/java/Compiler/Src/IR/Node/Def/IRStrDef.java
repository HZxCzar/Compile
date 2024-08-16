package Compiler.Src.IR.Node.Def;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Entity.IRVariable;
import Compiler.Src.Util.Error.BaseError;


@lombok.Getter
@lombok.Setter
public class IRStrDef extends IRGlobalDef {
    public String value_old;
    public String value;
    public IRStrDef(IRVariable dest, String value_old) {
        super(dest);
        this.value_old = value_old;
    }
    @Override
    public <T> T accept(IRVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }
}
