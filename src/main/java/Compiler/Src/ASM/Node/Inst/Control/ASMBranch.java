package Compiler.Src.ASM.Node.Inst.Control;

import Compiler.Src.ASM.ASMVisitor;
import Compiler.Src.ASM.Entity.ASMReg;
import Compiler.Src.ASM.Node.Inst.ASMInst;
import Compiler.Src.Util.Error.ASMError;

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
        throw new ASMError("Branch instruction should not be printed");
    }
    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
}