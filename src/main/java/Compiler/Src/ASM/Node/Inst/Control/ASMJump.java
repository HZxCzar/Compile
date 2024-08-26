package Compiler.Src.ASM.Node.Inst.Control;

import java.util.ArrayList;

import Compiler.Src.ASM.ASMVisitor;
import Compiler.Src.ASM.Entity.ASMVirtualReg;
import Compiler.Src.ASM.Node.Inst.ASMInst;

@lombok.Getter
@lombok.Setter
public class ASMJump extends ASMInst {
    private String label;

    public ASMJump(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        String str = "j " + label;
        return str;
    }
    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }

    public void addFuncName(String funcName) {
        label = funcName + "." + label;
    }

    @Override
    public ASMVirtualReg getDef() {
        return null;
    }

    @Override
    public ArrayList<ASMVirtualReg> getUses() {
        var ret = new ArrayList<ASMVirtualReg>();
        return ret;
    }
}
