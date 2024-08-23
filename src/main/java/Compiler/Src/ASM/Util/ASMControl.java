package Compiler.Src.ASM.Util;

import Compiler.Src.ASM.Entity.ASMPhysicalReg;

@lombok.Getter
@lombok.Setter
public class ASMControl {
    protected ASMCounter counter;
    protected BuiltInRegs regs;

    protected ASMControl() {
        this.counter = new ASMCounter();
        this.regs = new BuiltInRegs();
    }

    public ASMPhysicalReg getArgReg(int i) {
        switch (i) {
            case 0:
                return regs.getA0();
            case 1:
                return regs.getA1();
            case 2:
                return regs.getA2();
            case 3:
                return regs.getA3();
            case 4:
                return regs.getA4();
            case 5:
                return regs.getA5();
            case 6:
                return regs.getA6();
            case 7:
                return regs.getA7();
            default:
                return null;
        }
    }
}
