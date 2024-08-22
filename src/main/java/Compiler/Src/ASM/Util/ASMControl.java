package Compiler.Src.ASM.Util;

public class ASMControl {
    protected ASMCounter counter; // for instruction selection

    protected ASMControl() {
        this.counter = new ASMCounter();
    }
}
