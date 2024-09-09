package Compiler.Src.ASM_New.Node.Inst.Memory;

import java.util.ArrayList;

import Compiler.Src.ASM_New.Node.Stmt.ASMBlock;
import Compiler.Src.ASM_New.ASMVisitor;
import Compiler.Src.ASM_New.Entity.ASMReg;
import Compiler.Src.ASM_New.Entity.ASMVirtualReg;
import Compiler.Src.ASM_New.Node.Inst.ASMInst;

@lombok.Getter
@lombok.Setter
public class ASMLoad extends ASMInst {
    private String op;
    private ASMReg rs2, rs1;
    private int imm;

    public ASMLoad(int id,ASMBlock parent,String op, ASMReg rs2, int imm, ASMReg rs1) {
        super(id, parent);
        this.op = op;
        this.rs2 = rs2;
        this.imm = imm;
        this.rs1 = rs1;
    }

    @Override
    public String toString() {
        String str = op + " " + rs2.toString() + ", " + imm + "(" + rs1.toString() + ")";
        return str;
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
    @Override
    public void setDest(ASMReg reg) {
        rs2 = reg;
    }

    @Override
    public ASMReg getDef() {
        if (rs2 instanceof ASMVirtualReg) {
            return (ASMVirtualReg) rs2;
        }
        return null;
    }

    @Override
    public ArrayList<ASMReg> getUses() {
        var ret = new ArrayList<ASMReg>();
        if (rs1 instanceof ASMReg) {
            ret.add((ASMReg) rs1);
        }
        return ret;
    }

    @Override
    public void replaceUse(ASMReg oldReg, ASMReg newReg) {
        if (rs1.equals(oldReg)) {
            rs1 = newReg;
        }
    }
}
