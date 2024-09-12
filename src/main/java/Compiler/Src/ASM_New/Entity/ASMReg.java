package Compiler.Src.ASM_New.Entity;

import java.util.Objects;

@lombok.Getter
@lombok.Setter
public abstract class ASMReg implements Comparable<ASMReg> {
    protected final String name;

    public ASMReg(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ASMReg) {
            return ((ASMReg) obj).name.equals(this.name);
        }
        return false;
    }

    @Override
    public int compareTo(ASMReg o) {
        return name.compareTo(o.name);
    }

    @Override
    public int hashCode() {
        if (this instanceof ASMVirtualReg) {
            return Objects.hash(name, ((ASMVirtualReg) this).getId());
        }
        return Objects.hash(name, -1);
    }
}
