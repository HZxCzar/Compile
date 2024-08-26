package Compiler.Src.IR.Node.Stmt;

import java.util.ArrayList;
import java.util.HashSet;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Node.Inst.IRInst;
import Compiler.Src.IR.Node.util.IRLabel;
import Compiler.Src.Util.Error.BaseError;

@lombok.Getter
@lombok.Setter
public class IRBlock extends IRStmt {
    private IRLabel labelName;

    private IRInst returnInst;

    //CFG
    private ArrayList<IRBlock> successors;
    private ArrayList<IRBlock> predecessors;

    //Mem2Reg
    private IRBlock idom;
    private HashSet<IRBlock> DomFrontier;

    public IRBlock(IRLabel labelName) {
        this.labelName = labelName;
        this.returnInst = null;

        //CFG
        this.successors = new ArrayList<>();
        this.predecessors = new ArrayList<>();

        //Mem2Reg
        this.idom = null;
        this.DomFrontier = new HashSet<>();
    }

    @Override
    public <T> T accept(IRVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        String str = labelName.toString() + ":\n";
        for (var inst : getInsts()) {
            str += "  " + inst.toString() + "\n";
        }
        str += "  " +returnInst.toString()+"\n";
        return str;
    }

    //CFG

    public void addSuccessor(IRBlock block) {
        successors.add(block);
    }

    public void addPredecessor(IRBlock block) {
        predecessors.add(block);
    }
}
