package Compiler.Src.IR.Node.util;

import Compiler.Src.IR.Node.IRNode;

@lombok.Getter
@lombok.Setter
public class IRLabel extends IRNode{
    private String label;
    public IRLabel(String label) {
        this.label = label;
    }
}
