package Compiler.Src.ASM.Entity;

import Compiler.Src.ASM.Util.ASMCounter;
import Compiler.Src.Util.Error.ASMError;

@lombok.Getter
@lombok.Setter
public class ASMStackReg extends ASMReg {
    private int offset;

    public ASMStackReg(String name, int offset) {
        super(name);
        this.offset = offset;
    }

    public ASMStackReg(String name) {
        super(name);
    }

    public ASMStackReg(ASMCounter counter) {
        super("StackReg");
        this.offset = (counter.allocaCount++)-2;
    }

    @Override
    public String toString() {
        throw new ASMError("Stack register should not be printed");
    }
}
