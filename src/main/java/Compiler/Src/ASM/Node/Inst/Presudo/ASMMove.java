package Compiler.Src.ASM.Node.Inst.Presudo;

import Compiler.Src.ASM.ASMVisitor;
import Compiler.Src.ASM.Entity.ASMReg;
import Compiler.Src.ASM.Node.Inst.ASMInst;

@lombok.Getter
@lombok.Setter
public class ASMMove extends ASMInst {
    private ASMReg dest;
    private ASMReg rs1;

    public ASMMove(ASMReg dest, ASMReg rs1) {
        this.dest = dest;
        this.rs1 = rs1;
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
