package Compiler.Src.OPT;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.TreeMap;

import org.antlr.v4.runtime.misc.Pair;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Entity.IREntity;
import Compiler.Src.IR.Entity.IRLiteral;
import Compiler.Src.IR.Entity.IRVariable;
import Compiler.Src.IR.Node.IRNode;
import Compiler.Src.IR.Node.IRRoot;
import Compiler.Src.IR.Node.Def.IRFuncDef;
import Compiler.Src.IR.Node.Def.IRGlobalDef;
import Compiler.Src.IR.Node.Def.IRStrDef;
import Compiler.Src.IR.Node.Inst.*;
import Compiler.Src.IR.Node.Stmt.IRBlock;
import Compiler.Src.IR.Node.util.IRLabel;
import Compiler.Src.IR.Type.IRStructType;
import Compiler.Src.IR.Type.IRType;
import Compiler.Src.Util.Error.BaseError;
import Compiler.Src.Util.Error.OPTError;
import Compiler.Src.Util.ScopeUtil.GlobalScope;

public class DCE implements IRVisitor<OPTError> {
    // private HashSet<IRVariable> W;
    private TreeMap<IRVariable, Pair<IRBlock, IRInst>> Var2Def = new TreeMap<>();
    private TreeMap<IRVariable, IRGlobalDef> Var2GDef = new TreeMap<>();
    private TreeMap<IRVariable, HashSet<IRInst>> Var2Use = new TreeMap<>();
    private IRBlock currentBlock;
    // private HashSet<IRInst> SideEffectInst = new HashSet<IRInst>();
    // private HashSet<IRGlobalDef> GSideEffectInst = new HashSet<IRGlobalDef>();

    @Override
    public OPTError visit(IRRoot root) throws BaseError {
        Collect(root);
        // BackWard();
        Run(root);
        return new OPTError();
    }

    public void Collect(IRRoot root) throws BaseError {
        for (var def : root.getDefs()) {
            def.accept(this);
        }
        for (var func : root.getFuncs()) {
            func.accept(this);
        }
    }

    // public void BackWard() {
    // var queue = new LinkedList<IRInst>();
    // for (var inst : SideEffectInst) {
    // queue.add(inst);
    // }
    // while (!queue.isEmpty()) {
    // var inst = queue.poll();
    // for (var use : inst.getUses()) {
    // if (use.isGlobal()) {
    // var unit = Var2GDef.get(use);
    // GSideEffectInst.add(unit);
    // } else {
    // var unit = Var2Def.get(use);
    // if (unit == null) {
    // continue;
    // }
    // if (unit.b != null && !SideEffectInst.contains(unit.b)) {
    // SideEffectInst.add(unit.b);
    // queue.add(unit.b);
    // }
    // }
    // }
    // }
    // }

    public void Run(IRRoot root) {
        var W = new LinkedList<IRVariable>();
        for (var entry : Var2Def.entrySet()) {
            var key = entry.getKey();
            W.add(key);
            if (Var2Use.get(key) == null) {
                Var2Use.put(key, new HashSet<IRInst>());
            }
        }
        for (var entry : Var2GDef.entrySet()) {
            var key = entry.getKey();
            W.add(key);
            if (Var2Use.get(key) == null) {
                Var2Use.put(key, new HashSet<IRInst>());
            }
        }
        while (!W.isEmpty()) {
            var v = W.poll();
            if (!Var2Use.get(v).isEmpty()) {
                continue;
            }
            if (v.isGlobal()) {
                var S = Var2GDef.get(v);
                if (S == null) {
                    continue;
                }
                root.RemoveDef(S);
                Var2GDef.remove(v);
            } else {
                var P = Var2Def.get(v);
                if (P == null || P.b == null) {
                    continue;
                }
                var S = P.b;
                if (isSideEffect(S)) {
                    P.a.RemoveInst(S);
                    Var2Def.remove(v);
                    for (var x : S.getUses()) {
                        Var2Use.get(x).remove(S);
                        W.add((IRVariable) x);
                    }
                }
            }
        }
        // while (!W.isEmpty()) {
        // var v = W.poll();
        // if (!Var2Use.get(v).isEmpty()) {
        // continue;
        // }
        // if (v.isGlobal()) {
        // var S = Var2GDef.get(v);
        // if (GSideEffectInst.contains(S)) {
        // continue;
        // }
        // root.RemoveDef(S);
        // } else {
        // var P = Var2Def.get(v);
        // var S = P.b;
        // if (SideEffectInst.contains(S)) {
        // continue;
        // }
        // if (S == null) {
        // continue;
        // }
        // P.a.RemoveInst(S);
        // for (var x : S.getUses()) {
        // Var2Use.get(x).remove(S);
        // W.add((IRVariable) x);
        // }
        // }
        // }
    }

    public boolean isSideEffect(IRInst inst) {
        if (inst instanceof IRCall) {
            return false;
        } else if (inst instanceof IRArith) {
            return true;
        } else if (inst instanceof IRLoad) {
            return true;
        } else if (inst instanceof IRPhi) {
            return true;
        } else if (inst instanceof IRIcmp) {
            return true;
        } else if (inst instanceof IRGetelementptr) {
            return true;
        } else {
            throw new OPTError("Invalid DefInst in DCE");
        }
    }

    @Override
    public OPTError visit(IRFuncDef funcDef) throws BaseError {
        for (var para : funcDef.getParams()) {
            Var2Def.put(para, new Pair<>(funcDef.getOrder2Block().get(0), null));
        }
        for (var block : funcDef.getOrder2Block()) {
            block.accept(this);
        }
        return new OPTError();
    }

    @Override
    public OPTError visit(IRBlock block) {
        currentBlock = block;
        for (var inst : block.getPhiList().values()) {
            inst.accept(this);
        }
        for (var inst : block.getInsts()) {
            inst.accept(this);
        }
        block.getReturnInst().accept(this);
        return new OPTError();
    }

    @Override
    public OPTError visit(IRNode node) throws BaseError {
        return new OPTError("DCE: IRNode");
    }

    @Override
    public OPTError visit(IRGlobalDef node) throws BaseError {
        if (!(node.getVars().getType() instanceof IRStructType)) {
            // W.add(node.getVars());
            Var2GDef.put(node.getVars(), node);
        }
        return new OPTError("DCE: IRGlobalDef");
    }

    @Override
    public OPTError visit(IRStrDef node) throws BaseError {
        // W.add(node.getVars());
        Var2GDef.put(node.getVars(), node);
        return new OPTError("DCE: IRStrDef");
    }

    @Override
    public OPTError visit(IRAlloca node) throws BaseError {
        throw new OPTError("DCE: IRAlloca");
        // return new OPTError();
    }

    @Override
    public OPTError visit(IRArith node) throws BaseError {
        Var2Def.put(node.getDest(), new Pair<>(currentBlock, node));
        for (var use : node.getUses()) {
            var unit = Var2Use.get(use);
            if (unit == null) {
                unit = new HashSet<IRInst>();
                unit.add(node);
                Var2Use.put(use, unit);
            } else {
                unit.add(node);
            }
        }
        return new OPTError();
    }

    @Override
    public OPTError visit(IRBranch node) throws BaseError {
        // SideEffectInst.add(node);
        for (var use : node.getUses()) {
            var unit = Var2Use.get(use);
            if (unit == null) {
                unit = new HashSet<IRInst>();
                unit.add(node);
                Var2Use.put(use, unit);
            } else {
                unit.add(node);
            }
        }
        return new OPTError();
    }

    @Override
    public OPTError visit(IRCall node) throws BaseError {
        // SideEffectInst.add(node);
        if (node.getDest() != null) {
            Var2Def.put(node.getDest(), new Pair<>(currentBlock, node));
        }
        for (var use : node.getUses()) {
            var unit = Var2Use.get(use);
            if (unit == null) {
                unit = new HashSet<IRInst>();
                unit.add(node);
                Var2Use.put(use, unit);
            } else {
                unit.add(node);
            }
        }
        return new OPTError();
    }

    @Override
    public OPTError visit(IRGetelementptr node) throws BaseError {
        Var2Def.put(node.getDest(), new Pair<>(currentBlock, node));
        for (var use : node.getUses()) {
            var unit = Var2Use.get(use);
            if (unit == null) {
                unit = new HashSet<IRInst>();
                unit.add(node);
                Var2Use.put(use, unit);
            } else {
                unit.add(node);
            }
        }
        return new OPTError();
    }

    @Override
    public OPTError visit(IRRet node) throws BaseError {
        // SideEffectInst.add(node);
        for (var use : node.getUses()) {
            var unit = Var2Use.get(use);
            if (unit == null) {
                unit = new HashSet<IRInst>();
                unit.add(node);
                Var2Use.put(use, unit);
            } else {
                unit.add(node);
            }
        }
        // if (node.getValue() != null && !(node.getValue() instanceof IRLiteral)) {
        // var unit = new HashSet<IRInst>();
        // unit.add(node);
        // Var2Use.put((IRVariable) node.getValue(), unit);
        // }
        return new OPTError();
    }

    @Override
    public OPTError visit(IRLoad node) throws BaseError {
        Var2Def.put(node.getDest(), new Pair<>(currentBlock, node));
        for (var use : node.getUses()) {
            var unit = Var2Use.get(use);
            if (unit == null) {
                unit = new HashSet<IRInst>();
                unit.add(node);
                Var2Use.put(use, unit);
            } else {
                unit.add(node);
            }
        }
        return new OPTError();
    }

    @Override
    public OPTError visit(IRPhi node) throws BaseError {
        Var2Def.put(node.getDest(), new Pair<>(currentBlock, node));
        for (var use : node.getUses()) {
            var unit = Var2Use.get(use);
            if (unit == null) {
                unit = new HashSet<IRInst>();
                unit.add(node);
                Var2Use.put(use, unit);
            } else {
                unit.add(node);
            }
        }
        return new OPTError();
    }

    @Override
    public OPTError visit(IRIcmp node) throws BaseError {
        Var2Def.put(node.getDest(), new Pair<>(currentBlock, node));
        for (var use : node.getUses()) {
            var unit = Var2Use.get(use);
            if (unit == null) {
                unit = new HashSet<IRInst>();
                unit.add(node);
                Var2Use.put(use, unit);
            } else {
                unit.add(node);
            }
        }
        return new OPTError();
    }

    @Override
    public OPTError visit(IRStore node) throws BaseError {
        // SideEffectInst.add(node);
        for (var use : node.getUses()) {
            var unit = Var2Use.get(use);
            if (unit == null) {
                unit = new HashSet<IRInst>();
                unit.add(node);
                Var2Use.put(use, unit);
            } else {
                unit.add(node);
            }
        }
        return new OPTError();
    }

    @Override
    public OPTError visit(IREntity node) throws BaseError {
        return new OPTError();
    }

    @Override
    public OPTError visit(IRVariable node) throws BaseError {
        return new OPTError();
    }

    @Override
    public OPTError visit(IRLiteral node) throws BaseError {
        return new OPTError();
    }
}
