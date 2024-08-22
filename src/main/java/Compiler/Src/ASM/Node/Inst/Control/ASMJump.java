package Compiler.Src.ASM.Node.Inst.Control;

import Compiler.Src.ASM.Node.Inst.ASMInst;

@lombok.Getter
@lombok.Setter
public class ASMJump extends ASMInst {
    private String label;

    public ASMJump(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "";
    }
}
