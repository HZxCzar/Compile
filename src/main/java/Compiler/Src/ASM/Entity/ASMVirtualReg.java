package Compiler.Src.ASM.Entity;

import Compiler.Src.ASM.Util.ASMCounter;
import Compiler.Src.Util.Error.ASMError;

@lombok.Getter
@lombok.Setter
public class ASMVirtualReg extends ASMReg implements Comparable<ASMVirtualReg> {
    private int offset;

    public ASMVirtualReg(String name, int offset) {
        super(name);
        this.offset = offset;
    }

    public ASMVirtualReg(String name) {
        super(name);
    }

    public ASMVirtualReg(ASMCounter counter) {
        super("ASMVirtualReg");
        this.offset = (counter.allocaCount++)-2;
    }

    @Override
    public String toString() {
        throw new ASMError("Stack register should not be printed");
    }

    @Override
    public int compareTo(ASMVirtualReg o) {
        int nameComparison = this.getName().compareTo(o.getName());
        if (nameComparison != 0) {
            return nameComparison;
        }
        return Integer.compare(this.offset, o.offset);
    }
}
