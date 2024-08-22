package Compiler.Src.ASM.Node.Inst.Presudo;

import Compiler.Src.ASM.ASMVisitor;
import Compiler.Src.ASM.Node.Inst.ASMInst;

@lombok.Getter
@lombok.Setter
public class ASMRet extends ASMInst {
    public ASMRet() {
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
