package Compiler.Src.ASM_New.Entity;

@lombok.Getter
@lombok.Setter
public abstract class ASMReg {
    protected final String name;

    public ASMReg(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ASMReg) {
            return name.equals(((ASMReg) obj).name);
        }
        return false;
    }
}
