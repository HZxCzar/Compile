package Compiler.Src.ASM.Node.Inst.Memory;

import java.util.ArrayList;

import Compiler.Src.ASM.ASMVisitor;
import Compiler.Src.ASM.Entity.ASMReg;
import Compiler.Src.ASM.Entity.ASMVirtualReg;
import Compiler.Src.ASM.Node.Inst.ASMInst;

@lombok.Getter
@lombok.Setter
public class ASMLoad extends ASMInst {
    private String op;
    private ASMReg rs2, rs1;
    private int imm;

    public ASMLoad(String op, ASMReg rs2, int imm, ASMReg rs1) {
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
    public ASMVirtualReg getDef() {
        if (rs2 instanceof ASMVirtualReg) {
            return (ASMVirtualReg) rs2;
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
