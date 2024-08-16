package Compiler.Src.IR.Node.Stmt;

import java.util.ArrayList;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Entity.IREntity;
import Compiler.Src.IR.Node.IRNode;
import Compiler.Src.IR.Node.Inst.IRInst;
import Compiler.Src.Util.Error.BaseError;

@lombok.Getter
@lombok.Setter
public class IRStmt extends IRNode {
    private ArrayList<IRInst> insts;
    private IREntity dest;
    private IREntity destAddr;

    // public IRStmt(ArrayList<IRInst> insts, IREntity dest) {
    //     this.insts = insts;
    //     this.dest = dest;
    // }

    public void addInsts(IRInst inst) {
        insts.add(inst);
    }

    public void addBlockInsts(IRStmt insts)
    {
        this.insts.addAll(insts.getInsts());
    }

    @Override
    public <T> T accept(IRVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }

    public void addFront(IRInst node) {
        insts.add(0, node);
    }
}
