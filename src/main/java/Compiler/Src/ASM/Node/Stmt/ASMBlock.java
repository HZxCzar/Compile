package Compiler.Src.ASM.Node.Stmt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

import Compiler.Src.ASM.ASMVisitor;
import Compiler.Src.ASM.Entity.ASMVirtualReg;
import Compiler.Src.ASM.Node.Inst.Arithmetic.ASMArithR;
import Compiler.Src.ASM.Node.Inst.Control.ASMBranch;
import Compiler.Src.ASM.Node.Inst.Control.ASMJump;
import Compiler.Src.ASM.Node.Inst.Memory.ASMLoad;
import Compiler.Src.ASM.Node.Inst.Memory.ASMStore;
import Compiler.Src.ASM.Node.Inst.Presudo.ASMBezq;
import Compiler.Src.ASM.Node.Inst.Presudo.ASMLi;
import Compiler.Src.ASM.Node.Util.ASMLabel;
import Compiler.Src.ASM.Util.ASMControl;

@lombok.Getter
@lombok.Setter
public class ASMBlock extends ASMStmt {
    private ASMLabel label;
    private ASMStmt returnInst;
    // private HashSet<ASMVirtualReg> uses;
    // private HashSet<ASMVirtualReg> defs;
    // private ArrayList<ASMBlock> successors;
    // private HashSet<ASMVirtualReg> liveIn;
    // private HashSet<ASMVirtualReg> liveOut;

    // mem2reg
    private ArrayList<String> Successor;
    private ASMStmt PhiStmt;
    private TreeMap<ASMVirtualReg, ArrayList<ASMVirtualReg>> src2dest;

    public ASMBlock(ASMLabel label) {
        this.label = label;
        this.returnInst = null;
        // this.uses = new HashSet<ASMVirtualReg>();
        // this.defs = new HashSet<ASMVirtualReg>();
        // this.successors = new ArrayList<ASMBlock>();
        // this.liveIn = new HashSet<ASMVirtualReg>();
        // this.liveOut = new HashSet<ASMVirtualReg>();
        PhiStmt = new ASMStmt();
        src2dest = new TreeMap<ASMVirtualReg, ArrayList<ASMVirtualReg>>();
    }

    public void PhiMove(ASMControl control) {
        // var tmpDest = new ArrayList<ASMVirtualReg>();
        var src2tmp = new TreeMap<>();
        for (int i = 0; i < src2dest.size(); ++i) {
            var src = src2dest.keySet().toArray()[i];
            var tmp = new ASMVirtualReg(control.getCounter());
            src2tmp.put(src, tmp);
            PhiStmt.addInst(new ASMLi(control.getRegs().getT0(), 4 * ((ASMVirtualReg) src).getOffset()));
            PhiStmt.addInst(new ASMArithR("add", control.getRegs().getT0(), control.getRegs().getT0(),
                    control.getRegs().getSp()));
            PhiStmt.addInst(new ASMLoad("lw", control.getRegs().getA0(), 0, control.getRegs().getT0()));
            PhiStmt.addInst(new ASMLi(control.getRegs().getT0(), 4 * ((ASMVirtualReg) tmp).getOffset()));
            PhiStmt.addInst(new ASMArithR("add", control.getRegs().getT0(), control.getRegs().getT0(),
                    control.getRegs().getSp()));
            PhiStmt.addInst(new ASMStore("sw", control.getRegs().getA0(), 0, control.getRegs().getT0()));
        }
        for (var src : src2dest.keySet()) {
            for (var dest : src2dest.get(src)) {
                var tmp = src2tmp.get(src);
                PhiStmt.addInst(new ASMLi(control.getRegs().getT0(), 4 * ((ASMVirtualReg) tmp).getOffset()));
                PhiStmt.addInst(new ASMArithR("add", control.getRegs().getT0(), control.getRegs().getT0(),
                        control.getRegs().getSp()));
                PhiStmt.addInst(new ASMLoad("lw", control.getRegs().getA0(), 0, control.getRegs().getT0()));
                PhiStmt.addInst(new ASMLi(control.getRegs().getT0(), 4 * ((ASMVirtualReg) dest).getOffset()));
                PhiStmt.addInst(new ASMArithR("add", control.getRegs().getT0(), control.getRegs().getT0(),
                        control.getRegs().getSp()));
                PhiStmt.addInst(new ASMStore("sw", control.getRegs().getA0(), 0, control.getRegs().getT0()));
            }
        }
        // for (int i = 0; i < src2dest.keySet().size(); ++i) {
        // for(var dest:src2dest.values().toArray()[i]){
        // PhiStmt.addInst(new ASMLi(control.getRegs().getT0(), 4 * ((ASMVirtualReg)
        // dest).getOffset()));
        // PhiStmt.addInst(new ASMArithR("add", control.getRegs().getT0(),
        // control.getRegs().getT0(),
        // control.getRegs().getSp()));
        // PhiStmt.addInst(new ASMLoad("lw", control.getRegs().getA0(), 0,
        // control.getRegs().getT0()));
        // PhiStmt.addInst(new ASMLi(control.getRegs().getT0(), 4 * ((ASMVirtualReg)
        // src2tmp.get(src2dest.keySet().toArray()[i])).getOffset()));
        // PhiStmt.addInst(new ASMArithR("add", control.getRegs().getT0(),
        // control.getRegs().getT0(),
        // control.getRegs().getSp()));
        // PhiStmt.addInst(new ASMStore("sw", control.getRegs().getA0(), 0,
        // control.getRegs().getT0()));
        // }
        // var dest = src2dest.values().toArray()[i];
        // var tmp = tmpDest.get(i);
        // PhiStmt.addInst(new ASMLi(control.getRegs().getT0(), 4 * ((ASMVirtualReg)
        // tmp).getOffset()));
        // PhiStmt.addInst(new ASMArithR("add", control.getRegs().getT0(),
        // control.getRegs().getT0(),
        // control.getRegs().getSp()));
        // PhiStmt.addInst(new ASMLoad("lw", control.getRegs().getA0(), 0,
        // control.getRegs().getT0()));
        // PhiStmt.addInst(new ASMLi(control.getRegs().getT0(), 4 * ((ASMVirtualReg)
        // dest).getOffset()));
        // PhiStmt.addInst(new ASMArithR("add", control.getRegs().getT0(),
        // control.getRegs().getT0(),
        // control.getRegs().getSp()));
        // PhiStmt.addInst(new ASMStore("sw", control.getRegs().getA0(), 0,
        // control.getRegs().getT0()));
        // }
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
}