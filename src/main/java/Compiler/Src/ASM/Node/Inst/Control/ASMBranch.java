package Compiler.Src.ASM.Node.Inst.Control;

import Compiler.Src.ASM.ASMVisitor;
import Compiler.Src.ASM.Entity.ASMReg;
import Compiler.Src.ASM.Node.Inst.ASMInst;

@lombok.Getter
@lombok.Setter
public class ASMBranch extends ASMInst {
    private ASMReg cond;
    private String label;

    public ASMBranch(ASMReg cond,String label) {
        this.cond = cond;
        this.label = label;
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
