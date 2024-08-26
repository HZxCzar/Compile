package Compiler.Src.ASM.Node.Inst.Presudo;

import java.util.ArrayList;

import Compiler.Src.ASM.ASMVisitor;
import Compiler.Src.ASM.Entity.ASMReg;
import Compiler.Src.ASM.Entity.ASMVirtualReg;
import Compiler.Src.ASM.Node.Inst.ASMInst;

@lombok.Getter
@lombok.Setter
public class ASMBezq extends ASMInst {
    private ASMReg rs1;
    private String Label;

    public ASMBezq(ASMReg rs1, String label) {
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
    public ASMVirtualReg getDef() {
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
