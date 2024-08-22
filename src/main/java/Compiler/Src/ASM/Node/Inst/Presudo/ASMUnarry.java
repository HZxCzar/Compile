package Compiler.Src.ASM.Node.Inst.Presudo;

import Compiler.Src.ASM.ASMVisitor;
import Compiler.Src.ASM.Node.Inst.ASMInst;

@lombok.Getter
@lombok.Setter
public class ASMUnarry extends ASMInst {
    private String op;
    private String dest;

    public ASMUnarry(String op, String dest) {
        this.op = op;
        this.dest = dest;
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
