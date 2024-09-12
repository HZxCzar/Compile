package Compiler.Src.ASM_New.Node.Inst.Presudo;

import java.util.ArrayList;
import java.util.Objects;

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
    public ASMReg getDef() {
        if (dest instanceof ASMReg) {
            return dest;
        }
        return null;
    }

    @Override
    public ArrayList<ASMReg> getUses() {
        var ret = new ArrayList<ASMReg>();
        if (rs1 instanceof ASMReg) {
            ret.add(rs1);
        }
        return ret;
    }

    @Override
    public void replaceUse(ASMReg oldReg, ASMReg newReg) {
        if (rs1.equals(oldReg)) {
            rs1 = newReg;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(dest, rs1);
    }
}
