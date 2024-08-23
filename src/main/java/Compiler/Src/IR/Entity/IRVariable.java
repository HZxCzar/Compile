package Compiler.Src.IR.Entity;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Type.IRType;
import Compiler.Src.Util.Error.BaseError;

@lombok.Getter
@lombok.Setter
public class IRVariable extends IREntity {
    public IRVariable(IRType type, String value) {
        super(type, value);
    }

    public boolean isGlobal() {
        return getValue().startsWith("@");
    }

    @Override
    public String toString() {
        return getType().toString() + " " + getValue();
    }

    @Override
    public <T> T accept(IRVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }
}
