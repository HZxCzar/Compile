package Compiler.Src.IR.Node.util;

import Compiler.Src.IR.Node.Inst.IRInst;

@lombok.Getter
@lombok.Setter
public class IRLabel extends IRInst{
    private String label;
    public IRLabel(String label) {
        this.label = label;
    }
    @Override
    public String toString() {
        return label;
    }
}
