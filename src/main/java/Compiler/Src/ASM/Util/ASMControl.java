package Compiler.Src.ASM.Util;

import java.util.ArrayList;
import java.util.TreeMap;

import Compiler.Src.ASM.Entity.ASMPhysicalReg;
import Compiler.Src.ASM.Entity.ASMReg;
import Compiler.Src.ASM.Entity.ASMVirtualReg;
import Compiler.Src.ASM.Node.ASMNode;
import Compiler.Src.ASM.Node.Global.ASMFuncDef;
import Compiler.Src.ASM.Node.Inst.Arithmetic.ASMArithR;
import Compiler.Src.ASM.Node.Inst.Control.ASMJump;
import Compiler.Src.ASM.Node.Inst.Memory.ASMLoad;
import Compiler.Src.ASM.Node.Inst.Memory.ASMStore;
import Compiler.Src.ASM.Node.Inst.Presudo.ASMBezq;
import Compiler.Src.ASM.Node.Inst.Presudo.ASMLi;
import Compiler.Src.ASM.Node.Inst.Presudo.ASMRet;
import Compiler.Src.ASM.Node.Stmt.ASMBlock;
import Compiler.Src.ASM.Node.Stmt.ASMStmt;
import Compiler.Src.ASM.Node.Util.ASMLabel;
import Compiler.Src.IR.Entity.IRLiteral;
import Compiler.Src.Util.Error.ASMError;

@lombok.Getter
@lombok.Setter
public class ASMControl {
    protected ASMCounter counter;
    protected BuiltInRegs regs;

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
            InstList.addInst(new ASMLi(regs.getT0(), offset));
            InstList.addInst(new ASMArithR("add", regs.getT0(), regs.getSp(), regs.getT0()));
            InstList.addInst(new ASMStore("sw", reg, 0, regs.getT0()));
        } else {
            InstList.addInst(new ASMStore("sw", reg, offset, regs.getSp()));
        }
        return InstList;
    }

    public ASMStmt LoadAt(ASMReg reg, int offset) {
        var InstList = new ASMStmt();
        if (offset > 2047 || offset < -2048) {
            InstList.addInst(new ASMLi(regs.getT0(), offset));
            InstList.addInst(new ASMArithR("add", regs.getT0(), regs.getSp(), regs.getT0()));
            InstList.addInst(new ASMStore("lw", reg, 0, regs.getT0()));
        } else {
            InstList.addInst(new ASMStore("lw", reg, offset, regs.getSp()));
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
        initInst.addInst(0, new ASMLi(regs.getT0(), -total));
        initInst.addInst(1, new ASMArithR("add", regs.getSp(), regs.getSp(), regs.getT0()));
        initInst.appendInsts(StoreAt(regs.getRa(), total - 4));
        initInst.appendInsts(StoreAt(regs.getS0(), total - 8));
        // initBlock.addInst(2, new ASMLi(regs.getT0(), total - 4));
        // initBlock.addInst(3,
        // new ASMArithR("add", regs.getT0(), regs.getT0(), regs.getSp()));
        // initBlock.addInst(4, new ASMStore("sw", regs.getRa(), 0, regs.getT0()));
        // initBlock.addInst(5, new ASMLi(regs.getT0(), total - 8));
        // initBlock.addInst(6,
        // new ASMArithR("add", regs.getT0(), regs.getT0(), regs.getSp()));
        // initBlock.addInst(7, new ASMStore("sw", regs.getS0(), 0, regs.getT0()));
        initInst.addInst(new ASMLi(regs.getT0(), total));
        initInst.addInst(new ASMArithR("add", regs.getS0(), regs.getSp(), regs.getT0()));
        initBlock.appendInsts(0, initInst);
        var jumpStmt = new ASMStmt();
        var jumpInst = new ASMJump(func.getName() + "." + func.getBlocks().get(1).getLabel().getLabel());
        jumpStmt.addInst(jumpInst);
        // initBlock.addInst(jumpInst);
        initBlock.setReturnInst(jumpStmt);
        for (int i = 1; i < func.getBlocks().size(); i++) {
            var block = func.getBlocks().get(i);
            String Labelname_old = block.getLabel().getLabel();
            String Labelname_new = func.getName() + "." + Labelname_old;
            block.setLabel(new ASMLabel(Labelname_new));
            var size = block.getReturnInst().getInsts().size();
            for (int j = 0; j < size; j++) {
                var inst = block.getReturnInst().getInsts().get(j);
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
                        returnInst.addInst(new ASMLi(regs.getT0(), total));
                        returnInst.addInst(new ASMArithR("add", regs.getSp(), regs.getSp(), regs.getT0()));
                        block.getReturnInst().appendInsts(j, returnInst);
                    }
                    break;
                }
            }
        }
    }
}
