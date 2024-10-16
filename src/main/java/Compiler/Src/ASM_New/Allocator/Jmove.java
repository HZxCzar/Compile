package Compiler.Src.ASM_New.Allocator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import Compiler.Src.ASM_New.Entity.ASMPhysicalReg;
import Compiler.Src.ASM_New.Entity.ASMReg;

import Compiler.Src.ASM_New.Node.ASMRoot;
import Compiler.Src.ASM_New.Node.Global.ASMFuncDef;
import Compiler.Src.ASM_New.Node.Inst.ASMInst;
import Compiler.Src.ASM_New.Node.Inst.Control.ASMJump;
import Compiler.Src.ASM_New.Node.Inst.Memory.ASMLoad;
import Compiler.Src.ASM_New.Node.Inst.Memory.ASMStore;
import Compiler.Src.ASM_New.Node.Inst.Presudo.ASMBezq;
import Compiler.Src.ASM_New.Node.Inst.Presudo.ASMCall;
import Compiler.Src.ASM_New.Node.Inst.Presudo.ASMLi;
import Compiler.Src.ASM_New.Node.Inst.Presudo.ASMMove;
import Compiler.Src.ASM_New.Node.Inst.Presudo.ASMRet;
import Compiler.Src.ASM_New.Node.Stmt.ASMBlock;
import Compiler.Src.ASM_New.Node.Stmt.ASMStmt;
import Compiler.Src.ASM_New.Util.ASMCounter;
import Compiler.Src.ASM_New.Util.BuiltInRegs;
import Compiler.Src.Util.Error.OPTError;
public class Jmove {
    public void visit(ASMRoot root)
    {
        root.getFuncs().forEach(func->work_on_func(func));
    }
    public void work_on_func(ASMFuncDef func)
    {
        for(int i=0;i<func.getBlocks().size();++i)
        {
            var block=func.getBlocks().get(i);
            for(int ind=0;ind<block.getInsts().size();++ind)
            {
                var inst=block.getInsts().get(ind);
                if(inst instanceof ASMMove && ((ASMMove)inst).getDest().equals(((ASMMove)inst).getRs1()))
                {
                    block.getInsts().remove(inst);
                    --ind;
                }
            }
            for(var ind=0;ind<block.getPhiStmt().getInsts().size();++ind)
            {
                var inst=block.getPhiStmt().getInsts().get(ind);
                if(inst instanceof ASMMove && ((ASMMove)inst).getDest().equals(((ASMMove)inst).getRs1()))
                {
                    block.getPhiStmt().getInsts().remove(inst);
                    --ind;
                }
            }       
            if(block.getReturnInst().getInsts().get(block.getReturnInst().getInsts().size()-1) instanceof ASMJump)
            {
                var inst = (ASMJump)block.getReturnInst().getInsts().get(block.getReturnInst().getInsts().size()-1);
                if(block.jlabel==null && i+1<func.getBlocks().size() && func.getBlocks().get(i+1).getLabel().getLabel().equals(inst.getLabel()))
                {
                    block.getReturnInst().getInsts().remove(block.getReturnInst().getInsts().size()-1);
                }
                else if(block.jlabel!=null && i+1<func.getBlocks().size())
                {
                    var jump=block.getJump();
                    if(func.getBlocks().get(i+1).getLabel().getLabel().equals(jump.getLabel()) && block.getReturnInst().getInsts().size()>=2)
                    {
                        var bezqInst=(ASMBezq)block.getReturnInst().getInsts().get(block.getReturnInst().getInsts().size()-2);
                        bezqInst.setLabel(jump.getLabel());
                        block.setJlabel(null);
                        block.setJump(null);
                    }
                }
            }
        }
    }
}
