package Compiler.Src.ASM_New.Node.Stmt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

import Compiler.Src.ASM_New.Util.ASMCounter;
import Compiler.Src.ASM_New.Util.BuiltInRegs;
import Compiler.Src.ASM_New.Entity.ASMReg;
import Compiler.Src.ASM_New.ASMVisitor;
import Compiler.Src.ASM_New.Entity.ASMVirtualReg;
import Compiler.Src.ASM_New.Node.Inst.Control.ASMBranch;
import Compiler.Src.ASM_New.Node.Inst.Control.ASMJump;
import Compiler.Src.ASM_New.Node.Inst.Presudo.ASMBezq;
import Compiler.Src.ASM_New.Node.Inst.Presudo.ASMMove;
import Compiler.Src.ASM_New.Node.Util.ASMLabel;
import Compiler.Src.ASM_New.Util.ASMControl;
import Compiler.Src.Util.Error.OPTError;

@lombok.Getter
@lombok.Setter
public class ASMBlock extends ASMStmt {
    private ASMLabel label;
    private ASMStmt returnInst;

    public HashSet<ASMReg> uses = null;
    public HashSet<ASMReg> def = null;
    public HashSet<ASMReg> liveIn = null;
    public HashSet<ASMReg> liveOut = null;

    public ArrayList<ASMBlock> pred = null;
    public ArrayList<ASMBlock> succ = null;

    BuiltInRegs BuiltInRegs;

    // mem2reg
    private ArrayList<String> Successor;
    private ASMStmt PhiStmt;
    private TreeMap<ASMVirtualReg, ArrayList<ASMVirtualReg>> src2dest;
    private int loopDepth;

    public ASMBlock(ASMLabel label) {
        this.label = label;
        this.returnInst = null;
        PhiStmt = new ASMStmt();
        src2dest = new TreeMap<ASMVirtualReg, ArrayList<ASMVirtualReg>>();

        Successor = new ArrayList<String>();
        
        uses = new HashSet<ASMReg>();
        def = new HashSet<ASMReg>();
        liveIn = new HashSet<ASMReg>();
        liveOut = new HashSet<ASMReg>();

        pred = new ArrayList<ASMBlock>();
        succ = new ArrayList<ASMBlock>();

        BuiltInRegs = new BuiltInRegs();

        liveIn.add(BuiltInRegs.getSp());
        liveIn.add(BuiltInRegs.getRa());
        liveOut.add(BuiltInRegs.getSp());
        liveOut.add(BuiltInRegs.getRa());
    }

    public void PhiMove_Formal(ASMControl control) {
        // var tmpDest = new ArrayList<ASMVirtualReg>();
        var src2tmp = new TreeMap<>();
        for (int i = 0; i < src2dest.size(); ++i) {
            // throw new OPTError("PhiMove_Formal");
            var src = src2dest.keySet().toArray()[i];
            var tmp = new ASMVirtualReg(++ASMCounter.allocaCount);
            src2tmp.put(src, tmp);
            PhiStmt.addInst(new ASMMove(++ASMCounter.InstCount, this, tmp, (ASMVirtualReg) src));
        }
        for (var src : src2dest.keySet()) {
            var tmp = (ASMVirtualReg) src2tmp.get(src);
            for (var dest : src2dest.get(src)) {
                PhiStmt.addInst(new ASMMove(++ASMCounter.InstCount, this, (ASMVirtualReg) dest, tmp));
            }
        }
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
        if (PhiStmt.getInsts().size() > 0) {
            str += PhiStmt.toString();
        }
        str += returnInst.toString();
        return str;
    }

    public void replaceLabel(String oldLabel, String newLabel) {
        for (var inst : getReturnInst().getInsts()) {
            if (inst instanceof ASMBranch) {
                if (((ASMBranch) inst).getLabel().equals(oldLabel)) {
                    ((ASMBranch) inst).setLabel(newLabel);
                }
            } else if (inst instanceof ASMJump) {
                if (((ASMJump) inst).getLabel().equals(oldLabel)) {
                    ((ASMJump) inst).setLabel(newLabel);
                }
            } else if (inst instanceof ASMBezq) {
                if (((ASMBezq) inst).getLabel().equals(oldLabel)) {
                    ((ASMBezq) inst).setLabel(newLabel);
                }
            }
        }
    }

    public void addSucc(ASMBlock block) {
        if (succ == null) {
            succ = new ArrayList<ASMBlock>();
        }
        succ.add(block);
    }

    public void addPred(ASMBlock block) {
        if (pred == null) {
            pred = new ArrayList<ASMBlock>();
        }
        pred.add(block);
    }
}