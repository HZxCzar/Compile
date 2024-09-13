package Compiler.Src.ASM_New.Allocator;

import java.util.ArrayList;
import java.util.TreeMap;

import Compiler.Src.ASM_New.Entity.ASMPhysicalReg;
import Compiler.Src.ASM_New.Entity.ASMReg;
import Compiler.Src.ASM_New.Entity.ASMVirtualReg;
import Compiler.Src.ASM_New.Node.ASMNode;
import Compiler.Src.ASM_New.Node.ASMRoot;
import Compiler.Src.ASM_New.Node.Global.ASMFuncDef;
import Compiler.Src.ASM_New.Node.Inst.Arithmetic.ASMArithI;
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
import Compiler.Src.ASM_New.Util.ASMCounter;
import Compiler.Src.ASM_New.Util.BuiltInRegs;
import Compiler.Src.IR.Entity.IRLiteral;
import Compiler.Src.Util.Error.ASMError;

public class StackManager {
    BuiltInRegs regs;
    ASMBlock curBlock;

    public void visit(ASMRoot root) {
        regs = new BuiltInRegs();
        for (var func : root.getFuncs()) {
            work(func);
        }
    }

    public void work(ASMFuncDef func) {
        var initBlock = func.getBlocks().get(0);
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
}
