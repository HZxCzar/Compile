package Compiler.Src.IR.Node.Inst;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Entity.IREntity;
import Compiler.Src.IR.Entity.IRVariable;
import Compiler.Src.IR.Type.IRType;
import Compiler.Src.Util.Error.BaseError;

@lombok.Getter
@lombok.Setter
public class IRLoad extends IRInst {
    private IRType type;
    private IRVariable dest;
    private IREntity ptr;
    public IRLoad(IRVariable dest, IREntity ptr) {
        this.type = dest.getType();
        this.dest = dest;
        this.ptr = ptr;
    }
    @Override
    public <T> T accept(IRVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }
}
