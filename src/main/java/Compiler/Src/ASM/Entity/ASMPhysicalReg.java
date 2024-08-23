package Compiler.Src.ASM.Entity;


@lombok.Getter
@lombok.Setter
public class ASMPhysicalReg extends ASMReg {
    public ASMPhysicalReg(String name) {
        super(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
