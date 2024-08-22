package Compiler.Src.ASM.Node.Inst.Presudo;

import Compiler.Src.ASM.Entity.ASMReg;
import Compiler.Src.ASM.Node.Inst.ASMInst;

@lombok.Getter
@lombok.Setter
public class ASMLi extends ASMInst {
    private ASMReg dest;
    private int imm;

    public ASMLi(ASMReg dest, int imm) {
        this.dest = dest;
        this.imm = imm;
    }

    @Override
    public String toString() {
        return "";
    }
}
