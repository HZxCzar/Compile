package Compiler.Src.ASM.Node.Inst.Memory;

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
        return "";
    }
}
