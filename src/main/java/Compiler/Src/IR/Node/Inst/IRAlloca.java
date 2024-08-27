package Compiler.Src.IR.Node.Inst;

import java.util.ArrayList;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Entity.IREntity;
import Compiler.Src.IR.Entity.IRVariable;
import Compiler.Src.IR.Type.IRType;
import Compiler.Src.Util.Error.BaseError;
import Compiler.Src.Util.Error.IRError;

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

    @Override
    public ArrayList<IRVariable> getUses() {
        throw new IRError("IRAlloca.getUses() is not implemented");
    }

    @Override
    public void replaceUse(IRVariable oldVar, IREntity newVar) {
        throw new IRError("IRAlloca.replaceUse() is not implemented");
    }
}
