package Compiler.Src.OPT;

import Compiler.Src.IR.Node.IRRoot;
import Compiler.Src.IR.Node.Def.IRFuncDef;
import Compiler.Src.IR.Node.Stmt.IRBlock;

public class Mem2Reg {

    private IRFuncDef currentFunc;

    public void visit(IRRoot root) {
        for (var func : root.getFuncs()) {
            visit(func);
        }
    }

    public void visit(IRFuncDef func) {
        currentFunc = func;
        buildDom(func);
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
            calcDF(block);
        }
        return;
    }

    public boolean calcIdom(IRBlock block) {
        IRBlock newIdom = null;
        for (var pred : block.getPredecessors()) {
            if (pred.getIdom() != null) {
                newIdom = pred;
                break;
            } else {
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
}
