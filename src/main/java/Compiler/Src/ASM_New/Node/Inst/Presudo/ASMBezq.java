package Compiler.Src.ASM_New.Node.Inst.Presudo;

import java.util.ArrayList;

import Compiler.Src.ASM_New.Node.Stmt.ASMBlock;
import Compiler.Src.ASM_New.ASMVisitor;
import Compiler.Src.ASM_New.Entity.ASMReg;
import Compiler.Src.ASM_New.Entity.ASMVirtualReg;
import Compiler.Src.ASM_New.Node.Inst.ASMInst;

@lombok.Getter
@lombok.Setter
public class ASMBezq extends ASMInst {
    private ASMReg rs1;
    private String Label;

    public ASMBezq(int id,ASMBlock parent,ASMReg rs1, String label) {
        super(id, parent);
        this.rs1 = rs1;
        Label = label;
    }

    @Override
    public String toString() {
        String str = "beqz " + rs1.toString() + ", " + Label;
        return str;
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public void addFuncName(String funcName) {
        Label = funcName + "." + Label;
    }
    
    @Override
    public void setDest(ASMReg reg) {
        return;
    }

    @Override
    public ASMReg getDef() {
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
}
