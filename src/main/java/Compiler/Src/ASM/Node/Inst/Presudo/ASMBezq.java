package Compiler.Src.ASM.Node.Inst.Presudo;

import Compiler.Src.ASM.ASMVisitor;
import Compiler.Src.ASM.Entity.ASMReg;
import Compiler.Src.ASM.Node.Inst.ASMInst;

@lombok.Getter
@lombok.Setter
public class ASMBezq extends ASMInst {
    private ASMReg rs1;
    private String Label;

    public ASMBezq(ASMReg rs1, String label) {
        this.rs1 = rs1;
        Label = label;
    }

    @Override
    public String toString() {
        String str = "beqz " + rs1.toString() + ", " + Label;
        return str;
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
