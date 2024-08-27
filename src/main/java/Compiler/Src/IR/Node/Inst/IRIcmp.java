package Compiler.Src.IR.Node.Inst;

import java.util.ArrayList;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Entity.IREntity;
import Compiler.Src.IR.Entity.IRVariable;
import Compiler.Src.IR.Type.IRType;
import Compiler.Src.Util.Error.BaseError;

@lombok.Getter
@lombok.Setter
public class IRIcmp extends IRInst {
    private String cond;
    private IRType type;
    private IREntity lhs, rhs;
    private IRVariable dest;

    public IRIcmp(IRVariable dest, String cond, IRType type, IREntity lhs, IREntity rhs) {
        this.cond = cond;
        this.type = type;
        this.lhs = lhs;
        this.rhs = rhs;
        this.dest = dest;
    }

    @Override
    public <T> T accept(IRVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        return dest.getValue() + " = icmp " + cond + " " + lhs.getType().toString() + " " + lhs.getValue() + ", "
                + rhs.getValue();
    }

    @Override
    public ArrayList<IRVariable> getUses() {
        ArrayList<IRVariable> res = new ArrayList<>();
        if (lhs instanceof IRVariable) {
            res.add((IRVariable) lhs);
        }
        if (rhs instanceof IRVariable) {
            res.add((IRVariable) rhs);
        }
        return res;
    }

    @Override
    public void replaceUse(IRVariable oldVar, IREntity newVar) {
        if (lhs.equals(oldVar)) {
            lhs = newVar;
        }
        if (rhs.equals(oldVar)) {
            rhs = newVar;
        }
    }
}
