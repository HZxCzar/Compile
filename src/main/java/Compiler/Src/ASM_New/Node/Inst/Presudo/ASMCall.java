package Compiler.Src.ASM_New.Node.Inst.Presudo;

import java.util.ArrayList;

import Compiler.Src.ASM_New.Node.Stmt.ASMBlock;
import Compiler.Src.ASM_New.ASMVisitor;
import Compiler.Src.ASM_New.Entity.ASMVirtualReg;
import Compiler.Src.ASM_New.Node.Inst.*;

@lombok.Getter
@lombok.Setter
public class ASMCall extends ASMInst {
    private String funcName;

    public ASMCall(int id,ASMBlock parent,String funcName) {
        super(id, parent);
        this.funcName = funcName;
    }

    @Override
    public String toString() {
        String str = "call " + funcName;
        return str;
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
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
