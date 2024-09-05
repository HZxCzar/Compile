package Compiler.Src.ASM_New.Allocator;

import java.util.HashSet;
import java.util.TreeMap;

import Compiler.Src.ASM_New.Node.Util.ASMLabel;
import Compiler.Src.Util.Error.OPTError;
import Compiler.Src.ASM_New.Node.Inst.Control.ASMJump;
import Compiler.Src.ASM_New.Node.Inst.Presudo.ASMBezq;
import Compiler.Src.ASM_New.Entity.ASMReg;
import Compiler.Src.ASM_New.Node.Global.ASMFuncDef;
import Compiler.Src.ASM_New.Node.Stmt.ASMBlock;

public class LiveAnalysis {
    private TreeMap<String, ASMBlock> label2Block;

    public void LiveAnalysisMethod(ASMFuncDef func)
    {
        for(var block : func.getBlocks())
        {
            init(block);
        }
        BuildCFG(func);
        boolean changed = true;
        while(changed)
        {
            changed=false;
            for(var block:func.getBlocks())
            {
                changed |= CalcLive(block);
            }
        }
    }

    public void init(ASMBlock block)
    {
        for(var inst : block.getInsts())
        {
            inst.getUses().forEach(reg -> {
                if (!block.def.contains(reg)) {
                    block.getUses().add(reg);
                }
            });
            block.getDef().add(inst.getDef());
        }
    }

    public void BuildCFG(ASMFuncDef func)
    {
        label2Block = new TreeMap<>();
        for(var block : func.getBlocks())
        {
            label2Block.put(block.getLabel().getLabel(), block);
        }
        for(var block : func.getBlocks())
        {
            var size= block.getReturnInst().getInsts().size();
            var jumpInst = block.getReturnInst().getInsts().get(size-1);
            if(jumpInst instanceof ASMJump)
            {
                var nextBlock = label2Block.get(((ASMJump) jumpInst).getLabel());
                block.addSucc(nextBlock);
                nextBlock.addPred(block);
            }
            else{
                throw new OPTError("CFGBuilder: last inst is not jump");
            }
            if(size>1)
            {
                var BeqzInst = block.getReturnInst().getInsts().get(size-2);
                if(BeqzInst instanceof ASMBezq)
                {
                    var nextBlock = label2Block.get(((ASMBezq) BeqzInst).getLabel());
                    block.addSucc(nextBlock);
                    nextBlock.addPred(block);
                }
            }
        }
    }

    public boolean CalcLive(ASMBlock block)
    {
        HashSet<ASMReg> OldIn = new HashSet<ASMReg>(block.getLiveIn());
        HashSet<ASMReg> OldOut = new HashSet<ASMReg>(block.getLiveOut());

        block.setLiveIn(new HashSet<ASMReg>());
        block.setLiveOut(new HashSet<ASMReg>());
        for(var succ : block.getSucc())
        {
            block.getLiveOut().addAll(succ.getLiveIn());
        }

        block.getLiveIn().addAll(block.getUses());
        block.getLiveIn().addAll(block.getLiveOut());
        block.getLiveIn().removeAll(block.getDef());

        return !block.getLiveIn().equals(OldIn) || !block.getLiveOut().equals(OldOut);
    }
}
