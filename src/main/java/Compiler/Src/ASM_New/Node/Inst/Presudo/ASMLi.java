package Compiler.Src.ASM_New.Node.Inst.Presudo;

import java.util.ArrayList;

import Compiler.Src.ASM_New.Node.Stmt.ASMBlock;
import Compiler.Src.ASM_New.ASMVisitor;
import Compiler.Src.ASM_New.Entity.ASMReg;
import Compiler.Src.ASM_New.Entity.ASMVirtualReg;
import Compiler.Src.ASM_New.Node.Inst.ASMInst;

@lombok.Getter
@lombok.Setter
public class ASMLi extends ASMInst {
    private ASMReg dest;
    private int imm;

    public ASMLi(int id,ASMBlock parent,ASMReg dest, int imm) {
        super(id, parent);
        this.dest = dest;
        this.imm = imm;
    }

    @Override
    public String toString() {
        String str = "li " + dest.toString() + ", " + imm;
        return str;
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public ASMVirtualReg getDef() {
        if (dest instanceof ASMVirtualReg) {
            return (ASMVirtualReg) dest;
        }
        return null;
    }

    @Override
    public ArrayList<ASMVirtualReg> getUses() {
        var ret = new ArrayList<ASMVirtualReg>();
        return ret;
    }
}
