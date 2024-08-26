package Compiler.Src.ASM.Node.Stmt;

import java.util.ArrayList;
import java.util.HashSet;

import Compiler.Src.ASM.ASMVisitor;
import Compiler.Src.ASM.Entity.ASMVirtualReg;
import Compiler.Src.ASM.Node.Util.ASMLabel;

@lombok.Getter
@lombok.Setter
public class ASMBlock extends ASMStmt {
    private ASMLabel label;
    private ASMStmt returnInst;
    private HashSet<ASMVirtualReg> uses;
    private HashSet<ASMVirtualReg> defs;
    private ArrayList<ASMBlock> successors;
    private HashSet<ASMVirtualReg> liveIn;
    private HashSet<ASMVirtualReg> liveOut;

    public ASMBlock(ASMLabel label) {
        this.label = label;
        this.returnInst = null;
        this.uses = new HashSet<ASMVirtualReg>();
        this.defs = new HashSet<ASMVirtualReg>();
        this.successors = new ArrayList<ASMBlock>();
        this.liveIn = new HashSet<ASMVirtualReg>();
        this.liveOut = new HashSet<ASMVirtualReg>();
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        String str = label.toString() + ":\n";
        for (var inst : getInsts()) {
            str += "    " + inst.toString() + "\n";
        }
        str += returnInst.toString();
        return str;
    }
}