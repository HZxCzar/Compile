package Compiler.Src.ASM_New.Node;

import Compiler.Src.ASM_New.ASMVisitor;

public abstract class ASMNode {
    public abstract String toString();

    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
