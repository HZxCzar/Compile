package Compiler.Src.ASM.Node.Inst.Presudo;

import java.util.ArrayList;

import Compiler.Src.ASM.ASMVisitor;
import Compiler.Src.ASM.Entity.ASMReg;
import Compiler.Src.ASM.Entity.ASMVirtualReg;
import Compiler.Src.ASM.Node.Inst.ASMInst;

@lombok.Getter
@lombok.Setter
public class ASMUnarry extends ASMInst {
    private String op;
    private ASMReg dest, src;

    public ASMUnarry(String op, ASMReg dest, ASMReg src) {
        this.op = op;
        this.dest = dest;
        this.src = src;
    }

    @Override
    public String toString() {
        String str = op + " " + dest.toString() + ", " + src.toString();
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
        if (src instanceof ASMVirtualReg) {
            ret.add((ASMVirtualReg) src);
        }
        return ret;
    }
}
