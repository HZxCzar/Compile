package Compiler.Src.ASM.Node.Stmt;

import java.util.ArrayList;

import Compiler.Src.ASM.ASMVisitor;
import Compiler.Src.ASM.Entity.ASMReg;
import Compiler.Src.ASM.Node.ASMNode;
import Compiler.Src.ASM.Node.Inst.ASMInst;

@lombok.Getter
@lombok.Setter
public class ASMStmt extends ASMNode {
    private ArrayList<ASMInst> insts;
    private ASMReg dest;

    public ASMStmt() {
        insts = new ArrayList<ASMInst>();
    }

    public void addInst(ASMInst inst) {
        insts.add(inst);
    }

    public void appendInsts(ASMStmt nodes) {
        this.insts.addAll(nodes.insts);
    }

    @Override
    public String toString() {
        return "";
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
