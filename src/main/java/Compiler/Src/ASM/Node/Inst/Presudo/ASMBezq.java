package Compiler.Src.ASM.Node.Inst.Presudo;

import Compiler.Src.ASM.ASMVisitor;
import Compiler.Src.ASM.Entity.ASMReg;
import Compiler.Src.ASM.Node.ASMNode;

@lombok.Getter
@lombok.Setter
public class ASMBezq extends ASMNode{
    private ASMReg rs1;
    private String Label;

    public ASMBezq(ASMReg rs1, String label) {
        this.rs1 = rs1;
        Label = label;
    }

    @Override
    public String toString() {
        return "";
    }
    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
