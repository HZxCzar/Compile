package Compiler.Src.OPT;

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
import Compiler.Src.IR.Type.IRStructType;
import Compiler.Src.Util.Error.BaseError;
import Compiler.Src.Util.Error.OPTError;
import Compiler.Src.Util.ScopeUtil.GlobalScope;

public class SSA implements IRVisitor<OPTError> {
    private HashSet<IRInst> EreaseWorkSet = new HashSet<>();
    private TreeMap<IRVariable, IRInst> Var2Def = new TreeMap<>();
    private TreeMap<IRVariable, IRGlobalDef> Var2GDef = new TreeMap<>();
    private TreeMap<IRVariable, HashSet<IRInst>> Var2Use = new TreeMap<>();
    private TreeMap<IRInst, IRBlock> Inst2Block = new TreeMap<>();
    private IRBlock currentBlock;

    @Override
    public OPTError visit(IRRoot root) throws BaseError {
        Collect(root);
        Run(root);
        Erease(root);
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
                var S = Var2Def.get(v);
                if (S == null) {
                    continue;
                }
                if (isSideEffect(S)) {
                    var block = Inst2Block.get(S);
                    block.RemoveInst(S);
                    Inst2Block.remove(S);
                    Var2Def.remove(v);
                    for (var x : S.getUses()) {
                        Var2Use.get(x).remove(S);
                        W.add((IRVariable) x);
                    }
                }
            }
        }
    }

    public void Erease(IRRoot root) {
        EreaseWorkSet = new HashSet<>();
        for (var inst : Inst2Block.keySet()) {
            EreaseWorkSet.add(inst);
        }
        while (!EreaseWorkSet.isEmpty()) {
            var S = EreaseWorkSet.iterator().next();
            EreaseWorkSet.remove(S);
            if (S instanceof IRPhi) {
                boolean flag = true;
                var literal = ((IRPhi) S).getVals().get(0);
                for (var val : ((IRPhi) S).getVals()) {
                    if (val instanceof IRVariable) {
                        flag = false;
                        break;
                    } else {
                        if (!((IRLiteral) val).equals((IRLiteral) literal)) {
                            flag = false;
                            break;
                        }
                    }
                }
                if (flag) {
                    var type = ((IRPhi) S).getType();
                    var dest = ((IRPhi) S).getDest();
                    var block = Inst2Block.get(S);
                    block.RemoveInst(S);
                    S = new IRArith(dest, "add", type, literal, new IRLiteral(GlobalScope.irIntType, "0"));
                    block.addFront(S);
                    Inst2Block.put(S, block);
                }
            }
            var unitPair = isConstAssign(S);
            if (unitPair != null) {
                var block = Inst2Block.get(S);
                block.RemoveInst(S);
                var dest = unitPair.a;
                var literal = unitPair.b;
                for (var useInst : Var2Use.get(dest)) {
                    useInst.replaceUse(dest, literal);
                    EreaseWorkSet.add(useInst);
                }
            }
        }
    }

    public Pair<IRVariable, IRLiteral> isConstAssign(IRInst S) {
        if (S instanceof IRArith) {
            if (((IRArith) S).getLhs() instanceof IRLiteral && ((IRArith) S).getRhs() instanceof IRLiteral) {
                var Literal = new IRLiteral(GlobalScope.irIntType,
                        String.valueOf(InnerCompute(((IRLiteral) ((IRArith) S).getLhs()).getValue(),
                                ((IRLiteral) ((IRArith) S).getRhs()).getValue(), ((IRArith) S).getOp())));
                return new Pair<IRVariable, IRLiteral>(((IRArith) S).getDest(), Literal);
            }
            return null;
        } else if (S instanceof IRIcmp) {
            if (((IRIcmp) S).getLhs() instanceof IRLiteral && ((IRIcmp) S).getRhs() instanceof IRLiteral) {
                var Literal = new IRLiteral(GlobalScope.irIntType,
                        String.valueOf(InnerCompute(((IRLiteral) ((IRIcmp) S).getLhs()).getValue(),
                                ((IRLiteral) ((IRIcmp) S).getRhs()).getValue(), ((IRIcmp) S).getCond())));
                return new Pair<IRVariable, IRLiteral>(((IRIcmp) S).getDest(), Literal);
            }
            return null;
        }
        return null;
    }

    public int InnerCompute(String lhsStr, String rhsStr, String op) {
        switch (op) {
            case "add":
                return Integer.parseInt(lhsStr) + Integer.parseInt(rhsStr);
            case "sub":
                return Integer.parseInt(lhsStr) - Integer.parseInt(rhsStr);
            case "mul":
                return Integer.parseInt(lhsStr) * Integer.parseInt(rhsStr);
            case "sdiv":
                return Integer.parseInt(lhsStr) / Integer.parseInt(rhsStr);
            case "srem":
                return Integer.parseInt(lhsStr) % Integer.parseInt(rhsStr);
            case "shl":
                return Integer.parseInt(lhsStr) << Integer.parseInt(rhsStr);
            case "ashr":
                return Integer.parseInt(lhsStr) >> Integer.parseInt(rhsStr);
            case "and":
                return Integer.parseInt(lhsStr) & Integer.parseInt(rhsStr);
            case "or":
                return Integer.parseInt(lhsStr) | Integer.parseInt(rhsStr);
            case "xor":
                return Integer.parseInt(lhsStr) ^ Integer.parseInt(rhsStr);
            case "eq":
                return Integer.parseInt(lhsStr) == Integer.parseInt(rhsStr) ? 1 : 0;
            case "ne":
                return Integer.parseInt(lhsStr) != Integer.parseInt(rhsStr) ? 1 : 0;
            case "slt":
                return Integer.parseInt(lhsStr) < Integer.parseInt(rhsStr) ? 1 : 0;
            case "sgt":
                return Integer.parseInt(lhsStr) > Integer.parseInt(rhsStr) ? 1 : 0;
            case "sle":
                return Integer.parseInt(lhsStr) <= Integer.parseInt(rhsStr) ? 1 : 0;
            case "sge":
                return Integer.parseInt(lhsStr) >= Integer.parseInt(rhsStr) ? 1 : 0;
            default:
                throw new OPTError("Invalid op in InnerCompute");
        }
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
            Var2Def.put(para, null);
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
        Var2Def.put(node.getDest(), node);
        Inst2Block.put(node, currentBlock);
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
            Var2Def.put(node.getDest(), node);
            Inst2Block.put(node, currentBlock);
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
        Var2Def.put(node.getDest(), node);
        Inst2Block.put(node, currentBlock);
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
        Var2Def.put(node.getDest(), node);
        Inst2Block.put(node, currentBlock);
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
        Var2Def.put(node.getDest(), node);
        Inst2Block.put(node, currentBlock);
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
        Var2Def.put(node.getDest(), node);
        Inst2Block.put(node, currentBlock);
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
