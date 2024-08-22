package Compiler.Src.ASM.Entity;

@lombok.Getter
@lombok.Setter
public class ASMStackReg extends ASMReg {
    private int offset;
    public ASMStackReg(String name, int offset) {
        super(name);
        this.offset = offset;
    }
}
