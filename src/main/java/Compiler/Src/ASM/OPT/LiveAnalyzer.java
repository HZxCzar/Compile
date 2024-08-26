package Compiler.Src.ASM.OPT;

import java.util.HashSet;
import java.util.TreeMap;

import Compiler.Src.ASM.Entity.ASMVirtualReg;
import Compiler.Src.ASM.Node.ASMRoot;
import Compiler.Src.ASM.Node.Global.ASMFuncDef;
import Compiler.Src.ASM.Node.Inst.Control.ASMJump;
import Compiler.Src.ASM.Node.Inst.Presudo.ASMBezq;
import Compiler.Src.ASM.Node.Stmt.ASMBlock;

public class LiveAnalyzer {
    private TreeMap<String, ASMBlock> label2Block;
    private HashSet<ASMBlock> visited;
    private ASMFuncDef currentFunc;

    public void visit(ASMRoot root) {
        for (var func : root.getFuncs()) {
            visit(func);
        }
    }

    public void visit(ASMFuncDef funcDef) {
        this.currentFunc = funcDef;
        label2Block = new TreeMap<>();
        visited = new HashSet<>();
        for (var block : funcDef.getBlocks()) {
            label2Block.put(block.getLabel().getLabel(), block);
        }
        for (var block : funcDef.getBlocks()) {
            statistic(block);
        }
        CalcRpo(funcDef.getBlocks().get(0));
        boolean end=true;
        while(end)
        {
            end=false;
            for(var block:funcDef.getOrder2Block())
            {
                if(!calcLive(block))
                {
                    end=true;
                }
            }
        }
    }

    public boolean calcLive(ASMBlock block)
    {
        HashSet<ASMVirtualReg> liveIn_OD=block.getLiveIn();
        HashSet<ASMVirtualReg> liveOut_OD=block.getLiveOut();
        block.setLiveIn(new HashSet<>());
        block.setLiveOut(new HashSet<>());
        for(var succ:block.getSuccessors())
        {
            block.getLiveOut().addAll(succ.getLiveIn());
        }
        var lhs=block.getDefs();
        var rhs=block.getLiveOut();
        rhs.removeAll(block.getDefs());
        block.getLiveIn().addAll(lhs);
        block.getLiveIn().addAll(rhs);
        if(!block.getLiveIn().equals(liveIn_OD)||!block.getLiveOut().equals(liveOut_OD))
        {
            return false;
        }
        return true;
    }

    public void statistic(ASMBlock block) {
        for (var inst : block.getInsts()) {
            for (var reg : inst.getUses()) {
                if (block.getDefs().contains(reg)) {
                    continue;
                }
                block.getUses().add(reg);
            }
            var def = inst.getDef();
            if (def != null) {
                block.getDefs().add(def);
            }
        }
        for (var inst : block.getReturnInst().getInsts()) {
            for (var use : inst.getUses()) {
                if (block.getDefs().contains(use)) {
                    continue;
                }
                block.getUses().add(use);
            }
            var def = inst.getDef();
            if (def != null) {
                block.getDefs().add(def);
            }

            if (inst instanceof ASMJump) {
                var target = (ASMBlock) label2Block.get(((ASMJump) inst).getLabel());
                block.getSuccessors().add(target);
            } else if (inst instanceof ASMBezq) {
                var target = (ASMBlock) label2Block.get(((ASMBezq) inst).getLabel());
                block.getSuccessors().add(target);
            }
        }
    }

    public void CalcRpo(ASMBlock block)
    {
        visited.add(block);
        for (var succ : block.getSuccessors()) {
            if (!visited.contains(succ)) {
                CalcRpo(succ);
            }
        }
        currentFunc.getOrder2Block().add(block);
    }
}