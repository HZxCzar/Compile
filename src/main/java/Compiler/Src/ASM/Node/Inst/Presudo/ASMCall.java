package Compiler.Src.ASM.Node.Inst.Presudo;

import Compiler.Src.ASM.ASMVisitor;
import Compiler.Src.ASM.Node.Inst.*;

@lombok.Getter
@lombok.Setter
public class ASMCall extends ASMInst {
    private String funcName;

    public ASMCall(String funcName) {
        this.funcName = funcName;
    }

    @Override
    public String toString() {
        String str = "call " + funcName;
        return str;
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }

}
