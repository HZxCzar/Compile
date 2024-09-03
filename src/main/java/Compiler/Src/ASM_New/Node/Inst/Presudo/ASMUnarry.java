package Compiler.Src.ASM_New.Node.Inst.Presudo;

import java.util.ArrayList;

import Compiler.Src.ASM_New.Node.Stmt.ASMBlock;
import Compiler.Src.ASM_New.ASMVisitor;
import Compiler.Src.ASM_New.Entity.ASMReg;
import Compiler.Src.ASM_New.Entity.ASMVirtualReg;
import Compiler.Src.ASM_New.Node.Inst.ASMInst;

@lombok.Getter
@lombok.Setter
public class ASMUnarry extends ASMInst {
    private String op;
    private ASMReg dest, src;

    public ASMUnarry(int id,ASMBlock parent,String op, ASMReg dest, ASMReg src) {
        super(id, parent);
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
