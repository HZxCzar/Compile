package Compiler.Src.ASM_New.Allocator;

import java.util.ArrayList;

import Compiler.Src.ASM_New.Entity.ASMPhysicalReg;
import Compiler.Src.ASM_New.Entity.ASMReg;

import Compiler.Src.ASM_New.Node.ASMRoot;
import Compiler.Src.ASM_New.Node.Global.ASMFuncDef;
import Compiler.Src.ASM_New.Node.Inst.ASMInst;
import Compiler.Src.ASM_New.Node.Inst.Memory.ASMLoad;
import Compiler.Src.ASM_New.Node.Inst.Memory.ASMStore;
import Compiler.Src.ASM_New.Node.Inst.Presudo.ASMCall;
import Compiler.Src.ASM_New.Node.Inst.Presudo.ASMLi;
import Compiler.Src.ASM_New.Node.Inst.Presudo.ASMRet;
import Compiler.Src.ASM_New.Node.Stmt.ASMBlock;
import Compiler.Src.ASM_New.Node.Stmt.ASMStmt;
import Compiler.Src.ASM_New.Util.ASMCounter;
import Compiler.Src.ASM_New.Util.BuiltInRegs;
import Compiler.Src.Util.Error.OPTError;

public class StackManager {
    BuiltInRegs regs;
    ASMBlock curBlock;

    public void visit(ASMRoot root) {
        regs = new BuiltInRegs();
        for (var func : root.getFuncs()) {
            callmodify(func);
            work(func);
        }
    }

    public void callmodify(ASMFuncDef func) {
        for (var block : func.getBlocks()) {
            var live = block.getLiveOut();
            var returnInst = new ArrayList<ASMInst>();
            for (int i = block.getReturnInst().getInsts().size() - 1; i >= 0; --i) {
                var inst = block.getReturnInst().getInsts().get(i);
                ASMStmt StoreInst = null;
                ASMStmt LoadInst = null;
                if (inst instanceof ASMCall) {
                    StoreInst = new ASMStmt();
                    LoadInst = new ASMStmt();
                    for (var reg : live) {
                        assert (reg instanceof ASMPhysicalReg);
                        if (reg.equals(regs.getA0()) && ((ASMCall) inst).isHasReturnValue()) {
                            continue;
                        }
                        if(reg.equals(regs.getSp())||reg.equals(regs.getRa())||reg.equals(regs.getT0())){
                            continue;
                        }
                        LoadInst.addInst(new ASMLoad(++ASMCounter.InstCount, block, "lw", reg,
                                reg2imm(reg), regs.getSp()));
                    }
                    if (((ASMCall) inst).isHasReturnValue()) {
                        live.remove(regs.getA0());
                    }
                    for (var reg : live) {
                        assert (reg instanceof ASMPhysicalReg);
                        if(reg.equals(regs.getSp())||reg.equals(regs.getRa())){
                            continue;
                        }
                        if(reg.equals(regs.getSp())||reg.equals(regs.getRa())||reg.equals(regs.getT0())){
                            continue;
                        }
                        StoreInst.addInst(new ASMStore(++ASMCounter.InstCount, block, "sw", reg,
                                reg2imm(reg), regs.getSp()));
                    }
                }
                if (inst.getDef() != null) {
                    live.remove(inst.getDef());
                }
                for (var reg : inst.getUses()) {
                    live.add(reg);
                }
                if (LoadInst != null) {
                    returnInst.addAll(((ASMCall) inst).getArgSize()>=8 ? 1 : 0, LoadInst.getInsts());
                }
                returnInst.add(0, inst);
                if (StoreInst != null) {
                    if (((ASMCall) inst).getArgSize()>=8) {
                        --i;
                        inst = block.getReturnInst().getInsts().get(i);
                        returnInst.add(0, inst);
                        returnInst.addAll(0, StoreInst.getInsts());
                    } else {
                        returnInst.addAll(0, StoreInst.getInsts());
                    }
                }
            }
            block.getReturnInst().setInsts(returnInst);
            var phiInst = new ArrayList<ASMInst>();
            for (int i = block.getPhiStmt().getInsts().size() - 1; i >= 0; --i) {
                var inst = block.getPhiStmt().getInsts().get(i);
                ASMStmt StoreInst = null;
                ASMStmt LoadInst = null;
                if (inst instanceof ASMCall) {
                    StoreInst = new ASMStmt();
                    LoadInst = new ASMStmt();
                    for (var reg : live) {
                        assert (reg instanceof ASMPhysicalReg);
                        if (reg.equals(regs.getA0()) && ((ASMCall) inst).isHasReturnValue()) {
                            continue;
                        }
                        if(reg.equals(regs.getSp())||reg.equals(regs.getRa())||reg.equals(regs.getT0())){
                            continue;
                        }
                        LoadInst.addInst(new ASMLoad(++ASMCounter.InstCount, block, "lw", reg,
                                reg2imm(reg), regs.getSp()));
                    }
                    if (((ASMCall) inst).isHasReturnValue()) {
                        live.remove(regs.getA0());
                    }
                    for (var reg : live) {
                        assert (reg instanceof ASMPhysicalReg);
                        if(reg.equals(regs.getSp())||reg.equals(regs.getRa())||reg.equals(regs.getT0())){
                            continue;
                        }
                        StoreInst.addInst(new ASMStore(++ASMCounter.InstCount, block, "sw", reg,
                                reg2imm(reg), regs.getSp()));
                    }
                }
                if (inst.getDef() != null) {
                    live.remove(inst.getDef());
                }
                for (var reg : inst.getUses()) {
                    live.add(reg);
                }
                if (LoadInst != null) {
                    phiInst.addAll(((ASMCall) inst).getArgSize()>=8 ? 1 : 0, LoadInst.getInsts());
                }
                phiInst.add(0, inst);
                if (StoreInst != null) {
                    if (((ASMCall) inst).getArgSize()>=8) {
                        --i;
                        inst = block.getPhiStmt().getInsts().get(i);
                        phiInst.add(0, inst);
                        phiInst.addAll(0, StoreInst.getInsts());
                    } else {
                        phiInst.addAll(0, StoreInst.getInsts());
                    }
                }
            }
            block.getPhiStmt().setInsts(phiInst);
            var Inst = new ArrayList<ASMInst>();
            for (int i = block.getInsts().size() - 1; i >= 0; --i) {
                var inst = block.getInsts().get(i);
                ASMStmt StoreInst = null;
                ASMStmt LoadInst = null;
                if (inst instanceof ASMCall) {
                    StoreInst = new ASMStmt();
                    LoadInst = new ASMStmt();
                    for (var reg : live) {
                        assert (reg instanceof ASMPhysicalReg);
                        if (reg.equals(regs.getA0()) && ((ASMCall) inst).isHasReturnValue()) {
                            continue;
                        }
                        if(reg.equals(regs.getSp())||reg.equals(regs.getRa())||reg.equals(regs.getT0())){
                            continue;
                        }
                        LoadInst.addInst(new ASMLoad(++ASMCounter.InstCount, block, "lw", reg,
                                reg2imm(reg), regs.getSp()));
                    }
                    if (((ASMCall) inst).isHasReturnValue()) {
                        live.remove(regs.getA0());
                    }
                    for (var reg : live) {
                        assert (reg instanceof ASMPhysicalReg);
                        if(reg.equals(regs.getSp())||reg.equals(regs.getRa())||reg.equals(regs.getT0())){
                            continue;
                        }
                        StoreInst.addInst(new ASMStore(++ASMCounter.InstCount, block, "sw", reg,
                                reg2imm(reg), regs.getSp()));
                    }
                }
                if (inst.getDef() != null) {
                    live.remove(inst.getDef());
                }
                for (var reg : inst.getUses()) {
                    live.add(reg);
                }
                if (LoadInst != null) {
                    Inst.addAll(((ASMCall) inst).getArgSize()>=8 ? 1 : 0, LoadInst.getInsts());
                }
                Inst.add(0, inst);
                if (StoreInst != null) {
                    if (((ASMCall) inst).getArgSize()>=8) {
                        --i;
                        inst = block.getInsts().get(i);
                        Inst.add(0, inst);
                        Inst.addAll(0, StoreInst.getInsts());
                    } else {
                        Inst.addAll(0, StoreInst.getInsts());
                    }
                }
            }
            block.setInsts(Inst);
        }
    }

    public void work(ASMFuncDef func) {
        var initBlock = func.getBlocks().get(0);
        func.setStackSize(func.getStackSize() + 4);
        var total = (func.getStackSize() + 15) / 16 * 16;
        curBlock = initBlock;
        ((ASMLi) curBlock.getInsts().get(0)).setImm(total);
        curBlock = null;
        for (int i = 1; i < func.getBlocks().size(); i++) {
            var curBlock = func.getBlocks().get(i);
            var size = curBlock.getReturnInst().getInsts().size();
            if (curBlock.getReturnInst().getInsts().get(size - 1) instanceof ASMRet) {
                for (int j = size - 1; j >= 0; j--) {
                    var inst = curBlock.getReturnInst().getInsts().get(j);
                    if (inst instanceof ASMLi) {
                        ((ASMLi) inst).setImm(total);
                        break;
                    }
                }
            }
        }
        curBlock = null;
    }

    public int reg2imm(ASMReg reg) {
        switch (reg.getName()) {
            case "t1":
                return 0;
            case "t2":
                return 4;
            case "t3":
                return 8;
            case "t4":
                return 12;
            case "t5":
                return 16;
            case "t6":
                return 20;
            case "s0":
                return 24;
            case "s1":
                return 28;
            case "s2":
                return 32;
            case "s3":
                return 36;
            case "s4":
                return 40;
            case "s5":
                return 44;
            case "s6":
                return 48;
            case "s7":
                return 52;
            case "s8":
                return 56;
            case "s9":
                return 60;
            case "s10":
                return 64;
            case "s11":
                return 68;
            case "a0":
                return 72;
            case "a1":
                return 76;
            case "a2":
                return 80;
            case "a3":
                return 84;
            case "a4":
                return 88;
            case "a5":
                return 92;
            case "a6":
                return 96;
            case "a7":
                return 100;
            default:
                throw new OPTError("reg2imm");
        }
    }
}
