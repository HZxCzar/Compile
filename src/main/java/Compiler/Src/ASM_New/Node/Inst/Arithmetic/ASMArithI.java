package Compiler.Src.ASM_New.Node.Inst.Arithmetic;

import java.util.ArrayList;

import Compiler.Src.ASM_New.Node.Stmt.ASMBlock;
import Compiler.Src.ASM_New.ASMVisitor;
import Compiler.Src.ASM_New.Entity.ASMReg;
import Compiler.Src.ASM_New.Entity.ASMVirtualReg;
import Compiler.Src.ASM_New.Node.Inst.ASMInst;

@lombok.Getter
@lombok.Setter
public class ASMArithI extends ASMInst {
    private String op;
    private ASMReg dest, lhs;
    private int imm;

    public ASMArithI(int id,ASMBlock parent,String op, ASMReg dest, ASMReg lhs, int imm) {
        super(id, parent);
        this.op = op;
        this.dest = dest;
        this.lhs = lhs;
        this.imm = imm;
    }

    @Override
    public String toString() {
        String str = op + " " + dest.toString() + ", " + lhs.toString() + ", " + imm;
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
        if (lhs instanceof ASMVirtualReg) {
            ret.add((ASMVirtualReg) lhs);
        }
        return ret;
    }
}
