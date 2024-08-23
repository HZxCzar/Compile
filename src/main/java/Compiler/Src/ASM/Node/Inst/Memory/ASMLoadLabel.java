package Compiler.Src.ASM.Node.Inst.Memory;

import Compiler.Src.ASM.ASMVisitor;
import Compiler.Src.ASM.Entity.ASMReg;
import Compiler.Src.ASM.Node.Inst.ASMInst;

@lombok.Getter
@lombok.Setter
public class ASMLoadLabel extends ASMInst {
    ASMReg dest;
    String label;

    public ASMLoadLabel(ASMReg dest, String label) {
        this.dest = dest;
        this.label = label;
    }

    @Override
    public String toString() {
        String str = "la " + dest.toString() + ", " + label;
        return str;
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
