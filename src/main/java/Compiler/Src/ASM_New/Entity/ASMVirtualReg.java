package Compiler.Src.ASM_New.Entity;

import Compiler.Src.ASM_New.Util.ASMCounter;
import Compiler.Src.Util.Error.ASMError;

@lombok.Getter
@lombok.Setter
public class ASMVirtualReg extends ASMReg implements Comparable<ASMVirtualReg> {
    public static int allocaCount = 0;
    private int id;

    public ASMVirtualReg(int id) {
        super("ASMVirtualReg");
        this.id = id;
    }

    // public ASMVirtualReg(ASMCounter counter) {
    //     super("ASMVirtualReg");
    //     this.offset = (counter.allocaCount++)-2;
    // }

    @Override
    public String toString() {
        throw new ASMError("Virtual register should not be printed");
    }

    @Override
    public int compareTo(ASMVirtualReg o) {
        int nameComparison = this.getName().compareTo(o.getName());
        if (nameComparison != 0) {
            return nameComparison;
        }
        return Integer.compare(this.id, o.id);
    }
}
