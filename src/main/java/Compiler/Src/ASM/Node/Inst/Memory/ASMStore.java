package Compiler.Src.ASM.Node.Inst.Memory;

import Compiler.Src.ASM.ASMVisitor;
import Compiler.Src.ASM.Node.Inst.ASMInst;

@lombok.Getter
@lombok.Setter
public class ASMStore extends ASMInst {
    private String src;
    private String dest;
    private int imm;

    public ASMStore(String src, String dest, int imm) {
        this.src = src;
        this.dest = dest;
        this.imm = imm;
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
