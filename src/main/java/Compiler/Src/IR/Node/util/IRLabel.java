package Compiler.Src.IR.Node.util;

import Compiler.Src.IR.Node.Inst.IRInst;

@lombok.Getter
@lombok.Setter
public class IRLabel extends IRInst implements Comparable<IRLabel>{
    private String label;
    public IRLabel(String label) {
        this.label = label;
    }
    @Override
    public String toString() {
        return label;
    }

    @Override
    public int compareTo(IRLabel other) {
        return this.label.compareTo(other.label);
    }
}
