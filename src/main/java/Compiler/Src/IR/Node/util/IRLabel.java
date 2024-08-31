package Compiler.Src.IR.Node.util;

import Compiler.Src.IR.Node.Inst.IRInst;

@lombok.Getter
@lombok.Setter
public class IRLabel extends IRInst{
    private String label;
    public IRLabel(String label) {
        super(-1);
        this.label = label;
    }
    @Override
    public String toString() {
        return label;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IRLabel) {
            return label.equals(((IRLabel) obj).getLabel());
        }
        return false;
    }
}
