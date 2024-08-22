package Compiler.Src.ASM.Entity;

@lombok.Getter
@lombok.Setter
public abstract class ASMReg {
    private final String name;

    public ASMReg(String name) {
        this.name = name;
    }
}
