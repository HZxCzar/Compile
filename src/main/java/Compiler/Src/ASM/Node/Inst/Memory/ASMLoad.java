package Compiler.Src.ASM.Node.Inst.Memory;

import Compiler.Src.ASM.Entity.ASMReg;
import Compiler.Src.ASM.Node.Inst.ASMInst;

@lombok.Getter
@lombok.Setter
public class ASMLoad extends ASMInst {
    private ASMReg src;
    private ASMReg dest;
    private int imm;

    public ASMLoad(ASMReg src, ASMReg dest, int imm) {
        this.src = src;
        this.dest = dest;
        this.imm = imm;
    }

    @Override
    public String toString() {
        return "";
    }
}
