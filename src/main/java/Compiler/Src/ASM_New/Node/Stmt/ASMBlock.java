package Compiler.Src.ASM_New.Node.Stmt;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeMap;

import Compiler.Src.ASM_New.Util.ASMCounter;
import Compiler.Src.ASM.Entity.ASMReg;
import Compiler.Src.ASM_New.ASMVisitor;
import Compiler.Src.ASM_New.Entity.ASMVirtualReg;
import Compiler.Src.ASM_New.Node.Inst.Arithmetic.ASMArithR;
import Compiler.Src.ASM_New.Node.Inst.Control.ASMBranch;
import Compiler.Src.ASM_New.Node.Inst.Control.ASMJump;
import Compiler.Src.ASM_New.Node.Inst.Memory.ASMLoad;
import Compiler.Src.ASM_New.Node.Inst.Memory.ASMStore;
import Compiler.Src.ASM_New.Node.Inst.Presudo.ASMBezq;
import Compiler.Src.ASM_New.Node.Inst.Presudo.ASMLi;
import Compiler.Src.ASM_New.Node.Inst.Presudo.ASMMove;
import Compiler.Src.ASM_New.Node.Util.ASMLabel;
import Compiler.Src.ASM_New.Util.ASMControl;
import Compiler.Src.Util.Error.OPTError;

@lombok.Getter
@lombok.Setter
public class ASMBlock extends ASMStmt {
    private ASMLabel label;
    private ASMStmt returnInst;

    // mem2reg
    private ArrayList<String> Successor;
    private ASMStmt PhiStmt;
    private TreeMap<ASMVirtualReg, ArrayList<ASMVirtualReg>> src2dest;

    public ASMBlock(ASMLabel label) {
        this.label = label;
        this.returnInst = null;
        PhiStmt = new ASMStmt();
        src2dest = new TreeMap<ASMVirtualReg, ArrayList<ASMVirtualReg>>();
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
            var tmp = (ASMVirtualReg)src2tmp.get(src);
            PhiStmt.addInst(new ASMMove(++ASMCounter.InstCount, this, (ASMVirtualReg) src, tmp));
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
}