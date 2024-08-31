package Compiler.Src.OPT;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeMap;

import Compiler.Src.IR.Entity.IREntity;
import Compiler.Src.IR.Entity.IRLiteral;
import Compiler.Src.IR.Entity.IRVariable;
import Compiler.Src.IR.Node.IRRoot;
import Compiler.Src.IR.Node.Def.IRFuncDef;
import Compiler.Src.IR.Node.Inst.*;
import Compiler.Src.IR.Node.Stmt.IRBlock;
import Compiler.Src.IR.Node.util.IRLabel;
import Compiler.Src.IR.Type.IRType;
import Compiler.Src.IR.Util.InstCounter;
import Compiler.Src.Util.Error.OPTError;
import Compiler.Src.Util.ScopeUtil.GlobalScope;

public class Mem2Reg {

    private IRFuncDef currentFunc;

    // insertPhi
    private TreeMap<IRVariable, IRType> Var2Type;
    private TreeMap<IRVariable, ArrayList<IRBlock>> Var2Block;

    public void visit(IRRoot root) {
        for (var func : root.getFuncs()) {
            visit(func);
        }
    }

    public void visit(IRFuncDef func) {
        currentFunc = func;
        buildDom(func);
        insertPhi(func);
        rename(func);
    }

    public void buildDom(IRFuncDef func) {
        var entryBlock = func.getBlockstmts().get(0);
        entryBlock.setIdom(entryBlock);

        // build DomTree
        boolean run = true;
        while (run) {
            run = false;
            for (var block : func.getOrder2Block()) {
                if (block == entryBlock) {
                    continue;
                }
                if (calcIdom(block)) {
                    run = true;
                }
            }
        }

        // build DomFrontier
        for (var block : func.getOrder2Block()) {
            if (block.getIdom() != block) {
                block.getIdom().getDomChildren().add(block);
            }
            calcDF(block);
        }
        return;
    }

    public boolean calcIdom(IRBlock block) {
        IRBlock newIdom = null;
        for (var pred : block.getPredecessors()) {
            if (newIdom == null) {
                newIdom = pred;
            } else if (pred.getIdom() != null) {
                newIdom = intersect(pred, newIdom);
            }
        }
        if (block.getIdom() != newIdom) {
            block.setIdom(newIdom);
            return true;
        }
        return false;
    }

    public IRBlock intersect(IRBlock b1, IRBlock b2) { // LCA
        while (b1 != b2) {
            while (currentFunc.getBlock2Order().get(b1) < currentFunc.getBlock2Order().get(b2)) {
                b1 = b1.getIdom();
            }
            while (currentFunc.getBlock2Order().get(b1) > currentFunc.getBlock2Order().get(b2)) {
                b2 = b2.getIdom();
            }
        }
        return b1;
    }

    public void calcDF(IRBlock block) {
        for (var pred : block.getPredecessors()) {
            var runner = pred;
            while (runner != block.getIdom()) // entry?
            {
                runner.getDomFrontier().add(block);
                runner = runner.getIdom();
            }
        }
    }

    public void insertPhi(IRFuncDef func) {
        AllocaCollector(func);
        for (var var : Var2Block.keySet()) {
            // BFS
            var WorkQue = new LinkedList<IRBlock>();
            var util = new HashSet<IRBlock>();
            for (var block : Var2Block.get(var)) {
                WorkQue.add(block);
                util.add(block);
            }
            while (!WorkQue.isEmpty()) {
                var centerblock = WorkQue.poll();
                for (var block : centerblock.getDomFrontier()) {
                    if (block.getPhiList().containsKey(var)) {
                        continue;
                    }
                    var PhiDest = new IRVariable(Var2Type.get(var),
                            var.getValue() + ".PhiBlock." + func.getBlock2Order().get(block));
                    var PhiInst = new IRPhi(++InstCounter.InstCounter,PhiDest, PhiDest.getType(), new ArrayList<IREntity>(),
                            new ArrayList<IRLabel>());
                    block.getPhiList().put(var, PhiInst);
                    if (!util.contains(block)) {
                        util.add(block);
                        WorkQue.add(block);
                    }
                }
            }
        }
    }

    public void AllocaCollector(IRFuncDef func) {
        Var2Type = new TreeMap<>();
        Var2Block = new TreeMap<>();
        var entryBlock = func.getBlockstmts().get(0);
        for (var inst : entryBlock.getInsts()) {
            if (inst instanceof IRAlloca) {
                Var2Type.put(((IRAlloca) inst).getDest(), ((IRAlloca) inst).getType());
                Var2Block.put(((IRAlloca) inst).getDest(), new ArrayList<IRBlock>());
            }
        }
        for (var block : func.getBlockstmts()) {
            for (var inst : block.getInsts()) {
                if (inst instanceof IRStore) {
                    var dest = (IRVariable) ((IRStore) inst).getDest();
                    if (Var2Type.get(dest) == null) {
                        continue;
                    }
                    Var2Block.get(dest).add(block);
                } else if (inst instanceof IRCall && ((IRCall) inst).getFuncName().equals("__string.copy")) {
                    var dest = (IRVariable) ((IRCall) inst).getArgs().get(0);
                    if (Var2Type.get(dest) == null) {
                        continue;
                    }
                    Var2Block.get(dest).add(block);
                }
            }
        }
    }

    public void rename(IRFuncDef func) {
        var var2entity = new TreeMap<IRVariable, IREntity>();
        var entryBlock = func.getBlockstmts().get(0);
        for (var inst : entryBlock.getInsts()) {
            if (inst instanceof IRAlloca) {
                var2entity.put(((IRAlloca) inst).getDest(), null);
            }
        }
        var reg2entity = new TreeMap<IRVariable, IREntity>();
        renameBlock(entryBlock, var2entity, reg2entity);
    }

    public void renameBlock(IRBlock block, TreeMap<IRVariable, IREntity> var2entity,
            TreeMap<IRVariable, IREntity> reg2entity) {
        for (var phi : block.getPhiList().keySet()) {
            var2entity.put(phi, block.getPhiList().get(phi).getDest());
        }
        // var reg2entity = new TreeMap<IRVariable, IREntity>();
        var newInstList = new ArrayList<IRInst>();
        for (var inst : block.getInsts()) {
            if (inst instanceof IRAlloca) {
                continue;
            } else if (inst instanceof IRStore) {
                if (((IRStore) inst).getDest().isParameter()) {
                    var entity = ((IRStore) inst).getSrc();
                    if (entity instanceof IRVariable && reg2entity.containsKey((IRVariable) entity)) {
                        entity = reg2entity.get(entity);
                    }
                    var2entity.put(((IRStore) inst).getDest(), entity);
                    continue;
                }
            } else if (inst instanceof IRLoad) {
                if (((IRLoad) inst).getPtr().isParameter()) {
                    var entity = var2entity.get(((IRLoad) inst).getPtr());
                    if (entity == null) {
                        entity = new IRLiteral(((IRLoad) inst).getDest().getType(), "0"); // alloca完第一次取
                    } else if (entity instanceof IRVariable && reg2entity.containsKey((IRVariable) entity)) {
                        entity = reg2entity.get(entity);
                    }
                    reg2entity.put(((IRLoad) inst).getDest(), entity);
                    continue;
                }
            } else if (inst instanceof IRCall && ((IRCall) inst).getFuncName().equals("__string.copy")) {
                if (((IRVariable) ((IRCall) inst).getArgs().get(0)).isParameter()) {
                    var entity = ((IRCall) inst).getArgs().get(1);
                    if (entity instanceof IRVariable && reg2entity.containsKey((IRVariable) entity)) {
                        entity = reg2entity.get(entity);
                    }
                    var2entity.put((IRVariable) ((IRCall) inst).getArgs().get(0), entity);
                    continue;
                }
            }
            var uses = inst.getUses();
            for (var use : uses) {
                if (reg2entity.containsKey(use)) {
                    inst.replaceUse(use, reg2entity.get(use));
                }
                if (var2entity.containsKey(use)) {
                    throw new OPTError("Mem2Reg: use of variable in uses replace");
                }
            }
            newInstList.add(inst);
        }

        block.setInsts(newInstList);
        for (var use : block.getReturnInst().getUses()) {
            if (reg2entity.containsKey(use)) {
                block.getReturnInst().replaceUse(use, reg2entity.get(use));
            }
        }
        for (var succ : block.getSuccessors()) {
            var phiList = succ.getPhiList();
            for (var key : phiList.keySet()) {
                var entity = var2entity.get(key);
                if (entity == null) {
                    entity = new IRLiteral(phiList.get(key).getType(), "0");
                }
                phiList.get(key).getVals().add(entity);
                phiList.get(key).getLabels().add(block.getLabelName());
            }
        }
        for (var Domchild : block.getDomChildren()) {
            var var2entity2 = new TreeMap<IRVariable, IREntity>(var2entity);
            var reg2entity2 = new TreeMap<IRVariable, IREntity>(reg2entity);
            renameBlock(Domchild, var2entity2, reg2entity2);
        }
    }
}
