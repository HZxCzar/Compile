package Compiler.Src.ASM.Entity;

@lombok.Getter
@lombok.Setter
public abstract class ASMReg {
    protected final String name;

    public ASMReg(String name) {
        this.name = name;
    }
}
