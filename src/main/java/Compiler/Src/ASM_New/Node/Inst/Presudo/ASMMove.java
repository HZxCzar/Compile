package Compiler.Src.ASM_New.Node.Inst.Presudo;

import java.util.ArrayList;

import Compiler.Src.ASM_New.Node.Stmt.ASMBlock;
import Compiler.Src.ASM_New.ASMVisitor;
import Compiler.Src.ASM_New.Entity.ASMReg;
import Compiler.Src.ASM_New.Entity.ASMVirtualReg;
import Compiler.Src.ASM_New.Node.Inst.ASMInst;

@lombok.Getter
@lombok.Setter
public class ASMMove extends ASMInst {
    private ASMReg dest;
    private ASMReg rs1;

    public ASMMove(int id,ASMBlock parent,ASMReg dest, ASMReg rs1) {
        super(id, parent);
        this.dest = dest;
        this.rs1 = rs1;
    }

    @Override
    public String toString() {
        String str = "mv " + dest.toString() + ", " + rs1.toString();
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
        if (rs1 instanceof ASMVirtualReg) {
            ret.add((ASMVirtualReg) rs1);
        }
        return ret;
    }
}
