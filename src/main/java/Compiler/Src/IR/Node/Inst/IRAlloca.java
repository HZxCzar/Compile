package Compiler.Src.IR.Node.Inst;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Entity.IRVariable;
import Compiler.Src.IR.Type.IRType;
import Compiler.Src.Util.Error.BaseError;

@lombok.Getter
@lombok.Setter
public class IRAlloca extends IRInst {
    private IRVariable dest;
    private IRType type;

    public IRAlloca(IRVariable dest, IRType type) {
        this.dest = dest;
        this.type = type;
    }

    @Override
    public <T> T accept(IRVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return dest.getValue() + " = alloca " + type.getTypeName();
    }
}
