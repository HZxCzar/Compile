package Compiler.Src.IR.Node.Stmt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Entity.IRVariable;
import Compiler.Src.IR.Node.Inst.IRInst;
import Compiler.Src.IR.Node.Inst.IRPhi;
import Compiler.Src.IR.Node.util.IRLabel;
import Compiler.Src.Util.Error.BaseError;

@lombok.Getter
@lombok.Setter
public class IRBlock extends IRStmt implements Comparable<IRBlock> {
    private IRLabel labelName;

    private IRInst returnInst;

    // CFG
    private HashSet<IRBlock> successors;
    private HashSet<IRBlock> predecessors;

    // Mem2Reg
    private IRBlock idom;
    private HashSet<IRBlock> DomFrontier;
    private ArrayList<IRBlock> DomChildren;

    private TreeMap<IRVariable, IRPhi> PhiList;

    private int loopDepth;

    public IRBlock(IRLabel labelName, int loopDepth) {
        this.labelName = labelName;
        this.returnInst = null;

        this.loopDepth = loopDepth;

        // CFG
        this.successors = new HashSet<IRBlock>();
        this.predecessors = new HashSet<IRBlock>();

        // Mem2Reg
        this.idom = null;
        this.DomFrontier = new HashSet<IRBlock>();
        this.DomChildren = new ArrayList<IRBlock>();

        this.PhiList = new TreeMap<IRVariable, IRPhi>();
    }

    @Override
    public <T> T accept(IRVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        String str = labelName.toString() + ":\n";
        for (var phi : PhiList.values()) {
            str += "  " + phi.toString() + "\n";
          }
        for (var inst : getInsts()) {
            str += "  " + inst.toString() + "\n";
        }
        str += "  " + returnInst.toString() + "\n";
        return str;
    }

    // CFG

    public void addSuccessor(IRBlock block) {
        this.successors.add(block);
    }

    public void addPredecessor(IRBlock block) {
        this.predecessors.add(block);
    }

    public void RemoveInst(IRInst inst) {
        if(inst instanceof IRPhi) {
            for(var var : getPhiList().entrySet()) {
                if(var.getValue().equals(inst)) {
                    getPhiList().remove(var.getKey());
                    break;
                }
            }
        }
        getInsts().remove(inst);
    }

    @Override
    public int compareTo(IRBlock rhs) {
        return labelName.compareTo(((IRBlock) rhs).getLabelName());
    }

    @Override
    public int hashCode() {
        return labelName.hashCode();
    }
}
