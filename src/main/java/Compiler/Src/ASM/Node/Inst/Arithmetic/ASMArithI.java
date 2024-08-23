package Compiler.Src.ASM.Node.Inst.Arithmetic;

import Compiler.Src.ASM.ASMVisitor;
import Compiler.Src.ASM.Entity.ASMReg;
import Compiler.Src.ASM.Node.Inst.ASMInst;

@lombok.Getter
@lombok.Setter
public class ASMArithI extends ASMInst {
    private String op;
    private ASMReg dest, lhs;
    private int imm;

    public ASMArithI(String op, ASMReg dest, ASMReg lhs, int imm) {
        this.op = op;
        this.dest = dest;
        this.lhs = lhs;
        this.imm = imm;
    }

    @Override
    public String toString() {
        String str = op + " " + dest.toString() + ", " + lhs.toString() + ", " + imm;
        return str;
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
