package Compiler.Src.ASM.Node.Inst.Arithmetic;

import Compiler.Src.ASM.ASMVisitor;
import Compiler.Src.ASM.Entity.ASMReg;
import Compiler.Src.ASM.Node.Inst.ASMInst;

@lombok.Getter
@lombok.Setter
public class ASMArithR extends ASMInst {
    private String op;
    private ASMReg dest, lhs, rhs;
    public ASMArithR(String op, ASMReg dest, ASMReg lhs, ASMReg rhs) {
        this.op = op;
        this.dest = dest;
        this.lhs = lhs;
        this.rhs = rhs;
    }
    @Override
    public String toString() {
        return "";
    }
    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
