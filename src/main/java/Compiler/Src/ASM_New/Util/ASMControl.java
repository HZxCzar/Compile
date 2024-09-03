package Compiler.Src.ASM_New.Util;

import java.util.ArrayList;
import java.util.TreeMap;

import Compiler.Src.ASM_New.Entity.ASMPhysicalReg;
import Compiler.Src.ASM_New.Entity.ASMReg;
import Compiler.Src.ASM_New.Entity.ASMVirtualReg;
import Compiler.Src.ASM_New.Node.ASMNode;
import Compiler.Src.ASM_New.Node.Global.ASMFuncDef;
import Compiler.Src.ASM_New.Node.Inst.Arithmetic.ASMArithR;
import Compiler.Src.ASM_New.Node.Inst.Control.ASMJump;
import Compiler.Src.ASM_New.Node.Inst.Memory.ASMLoad;
import Compiler.Src.ASM_New.Node.Inst.Memory.ASMStore;
import Compiler.Src.ASM_New.Node.Inst.Presudo.ASMBezq;
import Compiler.Src.ASM_New.Node.Inst.Presudo.ASMLi;
import Compiler.Src.ASM_New.Node.Inst.Presudo.ASMRet;
import Compiler.Src.ASM_New.Node.Stmt.ASMBlock;
import Compiler.Src.ASM_New.Node.Stmt.ASMStmt;
import Compiler.Src.ASM_New.Node.Util.ASMLabel;
import Compiler.Src.IR.Entity.IRLiteral;
import Compiler.Src.Util.Error.ASMError;

@lombok.Getter
@lombok.Setter
public class ASMControl {
    protected ASMCounter counter;
    protected BuiltInRegs regs;
    protected ASMBlock curBlock;
    protected ASMFuncDef curFunc;

    // mem2reg
    protected TreeMap<String, ASMBlock> label2block;
    protected ArrayList<ASMBlock> funcBlocks;
    protected int CreateblockCnt;

    protected ASMControl() {
        this.counter = new ASMCounter();
        this.regs = new BuiltInRegs();
        this.CreateblockCnt = 0;
    }

    public ASMPhysicalReg getArgReg(int i) {
        switch (i) {
            case 0:
                return regs.getA0();
            case 1:
                return regs.getA1();
            case 2:
                return regs.getA2();
            case 3:
                return regs.getA3();
            case 4:
                return regs.getA4();
            case 5:
                return regs.getA5();
            case 6:
                return regs.getA6();
            case 7:
                return regs.getA7();
            default:
                return null;
        }
    }

    public ASMStmt StoreAt(ASMReg reg, int offset) {
        var InstList = new ASMStmt();
        if (offset > 2047 || offset < -2048) {
            InstList.addInst(new ASMLi(++ASMCounter.InstCount,curBlock,regs.getT0(), offset));
            InstList.addInst(new ASMArithR(++ASMCounter.InstCount,curBlock,"add", regs.getT0(), regs.getSp(), regs.getT0()));
            InstList.addInst(new ASMStore(++ASMCounter.InstCount,curBlock,"sw", reg, 0, regs.getT0()));
        } else {
            InstList.addInst(new ASMStore(++ASMCounter.InstCount,curBlock,"sw", reg, offset, regs.getSp()));
        }
        return InstList;
    }

    public ASMStmt LoadAt(ASMReg reg, int offset) {
        var InstList = new ASMStmt();
        if (offset > 2047 || offset < -2048) {
            InstList.addInst(new ASMLi(++ASMCounter.InstCount,curBlock,regs.getT0(), offset));
            InstList.addInst(new ASMArithR(++ASMCounter.InstCount,curBlock,"add", regs.getT0(), regs.getSp(), regs.getT0()));
            InstList.addInst(new ASMStore(++ASMCounter.InstCount,curBlock,"lw", reg, 0, regs.getT0()));
        } else {
            InstList.addInst(new ASMStore(++ASMCounter.InstCount,curBlock,"lw", reg, offset, regs.getSp()));
        }
        return InstList;
    }

    public int IRLiteral2Int(IRLiteral literal) {
        return literal.getValue().equals("null") ? 0 : Integer.parseInt(literal.getValue());
    }

    public boolean ValidImm(Object obj)
    {
        if(obj instanceof IRLiteral)
        {
            var value=IRLiteral2Int((IRLiteral)obj);
            if(value>-2048 && value<2047)
            {
                return true;
            }
        }
        return false;
    }

    public void Formolize(ASMFuncDef func) {
        var initBlock = func.getBlocks().get(0);
        var paramCount = func.getParamCount();
        var total = ((4 * (paramCount + counter.allocaCount)) + 15) / 16 * 16;
        var initInst = new ASMStmt();
        curBlock = initBlock;
        initInst.addInst(0, new ASMLi(++ASMCounter.InstCount,curBlock,regs.getT0(), -total));
        initInst.addInst(1, new ASMArithR(++ASMCounter.InstCount,curBlock,"add", regs.getSp(), regs.getSp(), regs.getT0()));
        initInst.appendInsts(StoreAt(regs.getRa(), total - 4));
        initInst.appendInsts(StoreAt(regs.getS0(), total - 8));
        initInst.addInst(new ASMLi(++ASMCounter.InstCount,curBlock,regs.getT0(), total));
        initInst.addInst(new ASMArithR(++ASMCounter.InstCount,curBlock,"add", regs.getS0(), regs.getSp(), regs.getT0()));
        initBlock.appendInsts(0, initInst);
        var jumpStmt = new ASMStmt();
        var jumpInst = new ASMJump(++ASMCounter.InstCount,curBlock,func.getName() + "." + func.getBlocks().get(1).getLabel().getLabel());
        jumpStmt.addInst(jumpInst);
        // initBlock.addInst(jumpInst);
        initBlock.setReturnInst(jumpStmt);
        curBlock = null;
        for (int i = 1; i < func.getBlocks().size(); i++) {
            var curBlock = func.getBlocks().get(i);
            String Labelname_old = curBlock.getLabel().getLabel();
            String Labelname_new = func.getName() + "." + Labelname_old;
            curBlock.setLabel(new ASMLabel(Labelname_new));
            var size = curBlock.getReturnInst().getInsts().size();
            for (int j = 0; j < size; j++) {
                var inst = curBlock.getReturnInst().getInsts().get(j);
                if (inst instanceof ASMJump) {
                    ((ASMJump) inst).addFuncName(func.getName());
                }
                if (inst instanceof ASMBezq) {
                    ((ASMBezq) inst).addFuncName(func.getName());
                }
                if (inst instanceof ASMRet) {
                    if (j != size - 1) {
                        throw new ASMError("ret should be the last instruction in a block");
                    } else {
                        var returnInst = new ASMStmt();
                        returnInst.appendInsts(LoadAt(regs.getRa(), total - 4));
                        returnInst.appendInsts(LoadAt(regs.getS0(), total - 8));
                        returnInst.addInst(new ASMLi(++ASMCounter.InstCount,initBlock,regs.getT0(), total));
                        returnInst.addInst(new ASMArithR(++ASMCounter.InstCount,initBlock,"add", regs.getSp(), regs.getSp(), regs.getT0()));
                        curBlock.getReturnInst().appendInsts(j, returnInst);
                    }
                    break;
                }
            }
        }
        curBlock=null;
    }
}
