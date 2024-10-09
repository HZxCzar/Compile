package Compiler.Src.OPT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashMap;
import java.util.HashMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.HashMap;
import java.util.HashMap;

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
import Compiler.Src.IR.Util.InstCounter;
import Compiler.Src.Util.Error.BaseError;
import Compiler.Src.Util.Error.OPTError;
import Compiler.Src.Util.ScopeUtil.GlobalScope;
import lombok.Builder.Default;

public class SSA implements IRVisitor<OPTError> {
    private HashSet<IRInst> EreaseWorkSet = new HashSet<>();
    private HashMap<IRVariable, IRInst> Var2Def = new HashMap<>();
    private HashMap<IRVariable, IRGlobalDef> Var2GDef = new HashMap<>();
    private HashMap<IRVariable, HashSet<IRInst>> Var2Use = new HashMap<>();
    private HashMap<IRInst, IRBlock> Inst2Block = new HashMap<>();
    private IRBlock currentBlock;

    // code move
    private HashMap<IRVariable, Pair<IRBlock, IRInst>> Var2Pair = new HashMap<>();

    // SCCP
    private HashMap<IRVariable, Pair<Integer, IREntity>> V;
    private HashSet<IRBlock> Excutable;

    @Override
    public OPTError visit(IRRoot root) throws BaseError {
        Collect(root);
        Run(root);
        root.getFuncs().forEach(func -> SCCP(func));
        // Erease(root);
        // CodeMove(root);
        return new OPTError();
    }

    public void SCCP(IRFuncDef func) {
        V = new HashMap<>();
        Excutable = new HashSet<>();
        Var2Use = new HashMap<IRVariable, HashSet<IRInst>>();
        var WorkListV = new HashSet<IRVariable>();
        var WorkListB = new HashSet<IRBlock>();
        var label2Block = new HashMap<IRLabel, IRBlock>();
        for (var arg : func.getParams()) {
            Var2Use.put(arg, new HashSet<IRInst>());
            V.put(arg, new Pair<>(2, null));
            WorkListV.add(arg);
        }
        for (var block : func.getBlockstmts()) {
            label2Block.put(block.getLabelName(), block);
            for (var phiInst : block.getPhiList().values()) {
                V.put(phiInst.getDef(), new Pair<>(0, null));
                if (Var2Use.get(phiInst.getDef()) == null) {
                    Var2Use.put(phiInst.getDef(), new HashSet<IRInst>());
                }
                for (var val : phiInst.getVals()) {
                    if (val instanceof IRVariable) {
                        if (Var2Use.get(val) == null) {
                            Var2Use.put((IRVariable) val, new HashSet<IRInst>());
                        }
                        Var2Use.get(val).add(phiInst);
                    }
                }
            }
            for (var inst : block.getInsts()) {
                for (var use : inst.getUses()) {
                    if (Var2Use.get(use) == null) {
                        Var2Use.put((IRVariable) use, new HashSet<IRInst>());
                    }
                    Var2Use.get(use).add(inst);
                }
                if (inst.getDef() != null) {
                    if (Var2Use.get(inst.getDef()) == null) {
                        Var2Use.put(inst.getDef(), new HashSet<IRInst>());
                    }
                    V.put(inst.getDef(), new Pair<>(0, null));
                }
            }
            for (var use : block.getReturnInst().getUses()) {
                if (Var2Use.get(use) == null) {
                    Var2Use.put((IRVariable) use, new HashSet<IRInst>());
                }
                Var2Use.get(use).add(block.getReturnInst());
            }
        }
        for (var keys : Var2Use.keySet()) {
            if (V.get(keys) == null) {
                V.put(keys, new Pair<>(2, null));
            }
        }
        Excutable.add(func.getBlockstmts().get(0));
        WorkListB.add(func.getBlockstmts().get(0));
        while (!WorkListV.isEmpty() || !WorkListB.isEmpty()) {
            while (!WorkListB.isEmpty()) {
                var block = WorkListB.iterator().next();
                WorkListB.remove(block);
                for (var phiInst : block.getPhiList().values()) {
                    if (phiInst.getDef().getValue().equals("%.tmp.binary.15")) {
                        int a = 1;
                    }
                    // 6
                    boolean fact6 = false;
                    HashSet<IREntity> tmp = new HashSet<>();
                    for (int i = 0; i < phiInst.getLabels().size(); ++i) {
                        var val = phiInst.getVals().get(i);
                        var label = phiInst.getLabels().get(i);
                        if (Excutable.contains(label2Block.get(label))
                                && (val instanceof IRLiteral || V.get(val).a == 1)) {
                            if (!tmp.isEmpty() && !tmp.contains(val)) {
                                fact6 = true;
                                break;
                            }
                            tmp.add(val);
                        }
                    }
                    if (fact6) {
                        if (V.get(phiInst.getDef()).a < 2) {
                            V.put(phiInst.getDef(), new Pair<>(2, null));
                            WorkListV.add(phiInst.getDef());
                        }
                        continue;
                    }

                    // 8
                    boolean fact8 = false;
                    for (int i = 0; i < phiInst.getLabels().size(); ++i) {
                        var val = phiInst.getVals().get(i);
                        var label = phiInst.getLabels().get(i);
                        if (Excutable.contains(label2Block.get(label)) && val instanceof IRVariable
                                && V.get(val).a == 2) {
                            fact8 = true;
                            break;
                        }
                    }
                    if (fact8) {
                        if (V.get(phiInst.getDef()).a < 2) {
                            V.put(phiInst.getDef(), new Pair<>(2, null));
                            WorkListV.add(phiInst.getDef());
                        }
                        continue;
                    }

                    if (phiInst.getDef().getValue().equals("%b.depth.2.tags.0.1.1.PhiBlock.0")) {
                        int a = 1;
                        // System.out.println(V.get(phiInst.getDef()).a);
                    }
                    // 9
                    boolean fact9 = true;
                    IREntity entity = null;
                    for (int i = 0; i < phiInst.getLabels().size(); ++i) {
                        var val = phiInst.getVals().get(i);
                        var label = phiInst.getLabels().get(i);
                        if (Excutable.contains(label2Block.get(label))
                                && (val instanceof IRLiteral || V.get(val).a == 1)) {
                            if (entity == null) {
                                entity = val instanceof IRLiteral ? val : V.get(val).b;
                            } else if (!entity.equals(val instanceof IRLiteral ? val : V.get(val).b)) {
                                fact9 = false;
                                break;
                            }
                        }
                    }
                    fact9 = entity == null ? false : fact9;
                    if (fact9) {
                        if (phiInst.getDef().getValue().equals("%b.depth.2.tags.0.1.1.PhiBlock.0")) {
                            int a = 1;
                            // System.out.println(V.get(phiInst.getDef()).a);
                        }
                        if (V.get(phiInst.getDef()).a < 1) {
                            V.put(phiInst.getDef(), new Pair<>(1, entity));
                            WorkListV.add(phiInst.getDef());
                        }
                    }
                }
                for (var inst : block.getInsts()) {
                    if (inst instanceof IRLoad) {
                        if (V.get(inst.getDef()).a < 2) {
                            V.put(inst.getDef(), new Pair<>(2, null));
                            WorkListV.add(inst.getDef());
                        }
                    } else if (inst instanceof IRCall && inst.getDef() != null) {
                        if (V.get(inst.getDef()).a < 2) {
                            V.put(inst.getDef(), new Pair<>(2, null));
                            WorkListV.add(inst.getDef());
                        }
                    } else if (inst instanceof IRArith) {
                        if (((((IRArith) inst).getLhs() instanceof IRVariable)
                                && (V.get(((IRArith) inst).getLhs()).a == 2))
                                || ((((IRArith) inst).getRhs() instanceof IRVariable)
                                        && (V.get(((IRArith) inst).getRhs()).a == 2))) {
                            if (V.get(inst.getDef()).a < 2) {
                                V.put(inst.getDef(), new Pair<>(2, null));
                                WorkListV.add(inst.getDef());
                            }
                        } else {
                            IREntity tmp = ((IRArith) inst).Innercompute(V);
                            if (tmp != null) {
                                if (V.get(inst.getDef()).a < 1) {
                                    V.put(inst.getDef(), new Pair<>(1, tmp));
                                    WorkListV.add(inst.getDef());
                                }
                            }
                        }
                    } else if (inst instanceof IRIcmp) {
                        if (((IRIcmp) inst).getDef().getValue().equals(".tmp.binary.13")) {
                            int a = 1;
                        }
                        if (inst.getDef().getValue().equals("%.tmp.binary.18")) {
                            int a = 1;
                        }
                        if (((((IRIcmp) inst).getLhs() instanceof IRVariable)
                                && (V.get(((IRIcmp) inst).getLhs()).a == 2))
                                || ((((IRIcmp) inst).getRhs() instanceof IRVariable)
                                        && (V.get(((IRIcmp) inst).getRhs()).a == 2))) {
                            if (V.get(inst.getDef()).a < 2) {
                                V.put(inst.getDef(), new Pair<>(2, null));
                                WorkListV.add(inst.getDef());
                            }
                        } else {
                            IREntity tmp = ((IRIcmp) inst).Innercompute(V);
                            if (tmp != null) {
                                if (V.get(inst.getDef()).a < 1) {
                                    V.put(inst.getDef(), new Pair<>(1, tmp));
                                    WorkListV.add(inst.getDef());
                                }
                            }
                        }
                    } else if (inst instanceof IRGetelementptr) {
                        if (((((IRGetelementptr) inst).getPtr() instanceof IRVariable)
                                && (V.get(((IRGetelementptr) inst).getPtr()).a == 2))
                                || ((((IRGetelementptr) inst).getInfolist()
                                        .get(((IRGetelementptr) inst).getInfolist().size() - 1) instanceof IRVariable)
                                        && (V.get(((IRGetelementptr) inst).getInfolist()
                                                .get(((IRGetelementptr) inst).getInfolist().size() - 1)).a == 2))) {
                            if (V.get(inst.getDef()).a < 2) {
                                V.put(inst.getDef(), new Pair<>(2, null));
                                WorkListV.add(inst.getDef());
                            }

                        } else {
                            IREntity tmp = ((IRGetelementptr) inst).Innercompute(V);
                            if (tmp != null) {
                                if (V.get(inst.getDef()).a < 1) {
                                    V.put(inst.getDef(), new Pair<>(1, tmp));
                                    WorkListV.add(inst.getDef());
                                }
                            }
                        }
                    }
                }
                if (block.getSuccessors().size() == 1 && !Excutable.contains(block.getSuccessors().iterator().next())) {
                    Excutable.add(block.getSuccessors().iterator().next());
                    WorkListB.add(block.getSuccessors().iterator().next());
                    for (var succ : block.getSuccessors().iterator().next().getSuccessors()) {
                        if (Excutable.contains(succ)) {
                            WorkListB.add(succ);
                        }
                    }
                } else if (block.getReturnInst() instanceof IRBranch
                        && ((IRBranch) block.getReturnInst()).getCond() instanceof IRLiteral) {
                    var inst = block.getReturnInst();
                    if (((IRBranch) inst).getCond().getValue().equals("1")) {
                        if (!Excutable
                                .contains(label2Block.get(((IRBranch) inst).getTrueLabel()))) {
                            WorkListB.add(label2Block.get(((IRBranch) inst).getTrueLabel()));
                            Excutable.add(label2Block.get(((IRBranch) inst).getTrueLabel()));
                            for (var succ : label2Block.get(((IRBranch) inst).getTrueLabel()).getSuccessors()) {
                                if (Excutable.contains(succ)) {
                                    WorkListB.add(succ);
                                }
                            }
                        }
                    } else if (((IRBranch) inst).getCond().getValue().equals("0")) {
                        if (!Excutable
                                .contains(label2Block.get(((IRBranch) inst).getFalseLabel()))) {
                            WorkListB.add(label2Block.get(((IRBranch) inst).getFalseLabel()));
                            Excutable.add(label2Block.get(((IRBranch) inst).getFalseLabel()));
                            for (var succ : label2Block.get(((IRBranch) inst).getFalseLabel()).getSuccessors()) {
                                if (Excutable.contains(succ)) {
                                    WorkListB.add(succ);
                                }
                            }
                        }
                    } else {
                        throw new OPTError("Invalid value in branch cond in SCCP");
                    }
                }
            }
            while (!WorkListV.isEmpty()) {
                var var = WorkListV.iterator().next();
                WorkListV.remove(var);
                if (var.getValue().equals("%.tmp.binary.13")) {
                    int a = 1;
                }
                for (var inst : Var2Use.get(var)) {
                    if (inst instanceof IRPhi) {
                        var phiInst = (IRPhi) inst;
                        // 6
                        boolean fact6 = false;
                        HashSet<IREntity> tmp = new HashSet<>();
                        for (int i = 0; i < phiInst.getLabels().size(); ++i) {
                            var val = phiInst.getVals().get(i);
                            var label = phiInst.getLabels().get(i);
                            if (Excutable.contains(label2Block.get(label))
                                    && (val instanceof IRLiteral || V.get(val).a == 1)) {
                                if (!tmp.isEmpty() && !tmp.contains(val)) {
                                    fact6 = true;
                                    break;
                                }
                                tmp.add(val);
                            }
                        }
                        if (fact6) {
                            if (V.get(phiInst.getDef()).a < 2) {
                                V.put(phiInst.getDef(), new Pair<>(2, null));
                                WorkListV.add(phiInst.getDef());
                            }
                            continue;
                        }

                        // 8
                        boolean fact8 = false;
                        for (int i = 0; i < phiInst.getLabels().size(); ++i) {
                            var val = phiInst.getVals().get(i);
                            var label = phiInst.getLabels().get(i);
                            if (Excutable.contains(label2Block.get(label)) && val instanceof IRVariable
                                    && V.get(val).a == 2) {
                                fact8 = true;
                                break;
                            }
                        }
                        if (fact8) {
                            if (V.get(phiInst.getDef()).a < 2) {
                                V.put(phiInst.getDef(), new Pair<>(2, null));
                                WorkListV.add(phiInst.getDef());
                            }
                            continue;
                        }

                        // 9
                        boolean fact9 = true;
                        IREntity entity = null;
                        for (int i = 0; i < phiInst.getLabels().size(); ++i) {
                            var val = phiInst.getVals().get(i);
                            var label = phiInst.getLabels().get(i);
                            if (Excutable.contains(label2Block.get(label))
                                    && (val instanceof IRLiteral || V.get(val).a == 1)) {
                                if (entity == null) {
                                    entity = val instanceof IRLiteral ? val : V.get(val).b;
                                } else if (!entity.equals(val instanceof IRLiteral ? val : V.get(val).b)) {
                                    fact9 = false;
                                    break;
                                }
                            }
                        }
                        fact9 = entity == null ? false : fact9;
                        if (fact9) {
                            if (phiInst.getDef().getValue().equals("%call.2")) {
                                int a = 1;
                                // System.out.println(V.get(phiInst.getDef()).a);
                            }
                            if (V.get(phiInst.getDef()).a < 1) {
                                V.put(phiInst.getDef(), new Pair<>(1, entity));
                                WorkListV.add(phiInst.getDef());
                            }
                        }
                    } else {
                        if (inst instanceof IRLoad) {
                            if (V.get(inst.getDef()).a < 2) {
                                V.put(inst.getDef(), new Pair<>(2, null));
                                WorkListV.add(inst.getDef());
                            }
                        } else if (inst instanceof IRCall && inst.getDef() != null) {
                            if (V.get(inst.getDef()).a < 2) {
                                V.put(inst.getDef(), new Pair<>(2, null));
                                WorkListV.add(inst.getDef());
                            }
                        } else if (inst instanceof IRArith) {
                            if (((((IRArith) inst).getLhs() instanceof IRVariable)
                                    && (V.get(((IRArith) inst).getLhs()).a == 2))
                                    || ((((IRArith) inst).getRhs() instanceof IRVariable)
                                            && (V.get(((IRArith) inst).getRhs()).a == 2))) {
                                if (V.get(inst.getDef()).a < 2) {
                                    V.put(inst.getDef(), new Pair<>(2, null));
                                    WorkListV.add(inst.getDef());
                                }
                            } else {
                                IREntity tmp = ((IRArith) inst).Innercompute(V);
                                if (tmp != null) {
                                    if (V.get(inst.getDef()).a < 1) {
                                        V.put(inst.getDef(), new Pair<>(1, tmp));
                                        WorkListV.add(inst.getDef());
                                    }
                                }
                            }
                        } else if (inst instanceof IRIcmp) {
                            if (((IRIcmp) inst).getLhs().getValue().equals("%call.2")) {
                                int a = 1;
                                // System.out.println(V.get(((IRIcmp) inst).getLhs()).a);
                            }
                            if (((((IRIcmp) inst).getLhs() instanceof IRVariable)
                                    && (V.get(((IRIcmp) inst).getLhs()).a == 2))
                                    || ((((IRIcmp) inst).getRhs() instanceof IRVariable)
                                            && (V.get(((IRIcmp) inst).getRhs()).a == 2))) {
                                if (V.get(inst.getDef()).a < 2) {
                                    V.put(inst.getDef(), new Pair<>(2, null));
                                    WorkListV.add(inst.getDef());
                                }
                            } else {
                                IREntity tmp = ((IRIcmp) inst).Innercompute(V);
                                if (tmp != null) {
                                    if (V.get(inst.getDef()).a < 1) {
                                        V.put(inst.getDef(), new Pair<>(1, tmp));
                                        WorkListV.add(inst.getDef());
                                    }
                                }
                            }
                        } else if (inst instanceof IRGetelementptr) {
                            if (((((IRGetelementptr) inst).getPtr() instanceof IRVariable)
                                    && (V.get(((IRGetelementptr) inst).getPtr()).a == 2))
                                    || ((((IRGetelementptr) inst).getInfolist()
                                            .get(((IRGetelementptr) inst).getInfolist().size()
                                                    - 1) instanceof IRVariable)
                                            && (V.get(((IRGetelementptr) inst).getInfolist()
                                                    .get(((IRGetelementptr) inst).getInfolist().size() - 1)).a == 2))) {
                                if (V.get(inst.getDef()).a < 2) {
                                    V.put(inst.getDef(), new Pair<>(2, null));
                                    WorkListV.add(inst.getDef());
                                }

                            } else {
                                IREntity tmp = ((IRGetelementptr) inst).Innercompute(V);
                                if (tmp != null) {
                                    if (V.get(inst.getDef()).a < 1) {
                                        V.put(inst.getDef(), new Pair<>(1, tmp));
                                        WorkListV.add(inst.getDef());
                                    }
                                }
                            }
                        } else if (inst instanceof IRBranch) {
                            if (!((IRBranch) inst).isJump()) {
                                if (((IRBranch) inst).getCond() instanceof IRVariable) {
                                    if (V.get(((IRBranch) inst).getCond()).a == 2) {
                                        if (!Excutable.contains(label2Block.get(((IRBranch) inst).getTrueLabel()))) {
                                            WorkListB.add(label2Block.get(((IRBranch) inst).getTrueLabel()));
                                            Excutable.add(label2Block.get(((IRBranch) inst).getTrueLabel()));
                                            for (var succ : label2Block.get(((IRBranch) inst).getTrueLabel())
                                                    .getSuccessors()) {
                                                if (Excutable.contains(succ)) {
                                                    WorkListB.add(succ);
                                                }
                                            }
                                        }
                                        if (!Excutable.contains(label2Block.get(((IRBranch) inst).getFalseLabel()))) {
                                            WorkListB.add(label2Block.get(((IRBranch) inst).getFalseLabel()));
                                            Excutable.add(label2Block.get(((IRBranch) inst).getFalseLabel()));
                                            for (var succ : label2Block.get(((IRBranch) inst).getFalseLabel())
                                                    .getSuccessors()) {
                                                if (Excutable.contains(succ)) {
                                                    WorkListB.add(succ);
                                                }
                                            }
                                        }
                                    } else if (V.get(((IRBranch) inst).getCond()).a == 1) {
                                        if (V.get(((IRBranch) inst).getCond()).b.getValue().equals("1")) {
                                            if (!Excutable
                                                    .contains(label2Block.get(((IRBranch) inst).getTrueLabel()))) {
                                                WorkListB.add(label2Block.get(((IRBranch) inst).getTrueLabel()));
                                                Excutable.add(label2Block.get(((IRBranch) inst).getTrueLabel()));
                                                for (var succ : label2Block.get(((IRBranch) inst).getTrueLabel())
                                                        .getSuccessors()) {
                                                    if (Excutable.contains(succ)) {
                                                        WorkListB.add(succ);
                                                    }
                                                }
                                            }
                                        } else if (V.get(((IRBranch) inst).getCond()).b.getValue().equals("0")) {
                                            if (!Excutable
                                                    .contains(label2Block.get(((IRBranch) inst).getFalseLabel()))) {
                                                WorkListB.add(label2Block.get(((IRBranch) inst).getFalseLabel()));
                                                Excutable.add(label2Block.get(((IRBranch) inst).getFalseLabel()));
                                                for (var succ : label2Block.get(((IRBranch) inst).getFalseLabel())
                                                        .getSuccessors()) {
                                                    if (Excutable.contains(succ)) {
                                                        WorkListB.add(succ);
                                                    }
                                                }
                                            }
                                        } else {
                                            throw new OPTError("Invalid value in branch cond in SCCP");
                                        }
                                    }
                                } else if (((IRBranch) inst).getCond() instanceof IRLiteral) {
                                    if (((IRBranch) inst).getCond().getValue().equals("1")) {
                                        if (!Excutable
                                                .contains(label2Block.get(((IRBranch) inst).getTrueLabel()))) {
                                            WorkListB.add(label2Block.get(((IRBranch) inst).getTrueLabel()));
                                            Excutable.add(label2Block.get(((IRBranch) inst).getTrueLabel()));
                                            for (var succ : label2Block.get(((IRBranch) inst).getTrueLabel())
                                                    .getSuccessors()) {
                                                if (Excutable.contains(succ)) {
                                                    WorkListB.add(succ);
                                                }
                                            }
                                        }
                                    } else if (((IRBranch) inst).getCond().getValue().equals("0")) {
                                        if (!Excutable
                                                .contains(label2Block.get(((IRBranch) inst).getFalseLabel()))) {
                                            WorkListB.add(label2Block.get(((IRBranch) inst).getFalseLabel()));
                                            Excutable.add(label2Block.get(((IRBranch) inst).getFalseLabel()));
                                            for (var succ : label2Block.get(((IRBranch) inst).getFalseLabel())
                                                    .getSuccessors()) {
                                                if (Excutable.contains(succ)) {
                                                    WorkListB.add(succ);
                                                }
                                            }
                                        }
                                    } else {
                                        throw new OPTError("Invalid value in branch cond in SCCP");
                                    }
                                } else {
                                    throw new OPTError("Invalid cond in branch in SCCP");
                                }
                            }
                        }
                    }
                }
            }
        }
        var Blockstmts = new ArrayList<IRBlock>();
        for (var block : func.getBlockstmts()) {
            if (Excutable.contains(block)) {
                var PhiList = new HashMap<IRVariable, IRPhi>();
                for (var phiInst : block.getPhiList().values()) {
                    if (phiInst.getDef().getValue().equals("%.tmp.binary.19")) {
                        int a = 1;
                        if (V.get(phiInst.getDef()).a == 0) {
                            int b = 1;
                        }
                    }
                    if (V.get(phiInst.getDef()).a == 2) {
                        var tmp = new IRPhi(phiInst.getId(), phiInst.getDest(), phiInst.getType(),
                                new ArrayList<>(), new ArrayList<>());
                        for (int i = 0; i < phiInst.getVals().size(); ++i) {
                            var val = phiInst.getVals().get(i);
                            var label = phiInst.getLabels().get(i);
                            if (Excutable.contains(label2Block.get(label))) {
                                if (val instanceof IRVariable && V.get((IRVariable) val).a == 1) {
                                    tmp.getVals().add(V.get((IRVariable) val).b);
                                    tmp.getLabels().add(label);
                                } else {
                                    tmp.getVals().add(val);
                                    tmp.getLabels().add(label);
                                }
                            }
                        }
                        PhiList.put(phiInst.getDef(), tmp);
                    }
                }
                block.setPhiList(PhiList);
                var insts = new ArrayList<IRInst>();
                for (var inst : block.getInsts()) {
                    if (inst instanceof IRArith) {
                        if (V.get(((IRArith) inst).getDef()).a == 2) {
                            var tmp = new IRArith(inst.getId(), ((IRArith) inst).getDest(), ((IRArith) inst).getOp(),
                                    ((IRArith) inst).getType(), ((IRArith) inst).getLhs(), ((IRArith) inst).getRhs());
                            if (((IRArith) inst).getLhs() instanceof IRVariable
                                    && V.get(((IRArith) inst).getLhs()).a == 1) {
                                tmp.setLhs(V.get(((IRArith) inst).getLhs()).b);
                            }
                            if (((IRArith) inst).getRhs() instanceof IRVariable
                                    && V.get(((IRArith) inst).getRhs()).a == 1) {
                                tmp.setRhs(V.get(((IRArith) inst).getRhs()).b);
                            }
                            insts.add(tmp);
                        }
                    } else if (inst instanceof IRBranch) {
                        if (((IRBranch) inst).isJump()) {
                            insts.add(inst);
                        } else {
                            if (((IRBranch) inst).getCond() instanceof IRVariable) {
                                if (V.get(((IRBranch) inst).getCond()).a == 1) {
                                    if (V.get(((IRBranch) inst).getCond()).b.getValue().equals("1")) {
                                        insts.add(new IRBranch(inst.getId(), ((IRBranch) inst).getTrueLabel()));
                                    } else if (V.get(((IRBranch) inst).getCond()).b.getValue().equals("0")) {
                                        insts.add(new IRBranch(inst.getId(), ((IRBranch) inst).getFalseLabel()));
                                    } else {
                                        throw new OPTError("Invalid value in branch cond in SCCP");
                                    }
                                } else {
                                    insts.add(inst);
                                }
                            } else {
                                if (((IRBranch) inst).getCond().getValue().equals("1")) {
                                    insts.add(new IRBranch(inst.getId(), ((IRBranch) inst).getTrueLabel()));
                                } else if (((IRBranch) inst).getCond().getValue().equals("0")) {
                                    insts.add(new IRBranch(inst.getId(), ((IRBranch) inst).getFalseLabel()));
                                } else {
                                    throw new OPTError("Invalid value in branch cond in SCCP");
                                }
                            }
                        }
                    } else if (inst instanceof IRCall) {
                        var tmp = new IRCall(inst.getId(), ((IRCall) inst).getDest(), ((IRCall) inst).getType(),
                                ((IRCall) inst).getFuncName(), new ArrayList<>());
                        for (var arg : ((IRCall) inst).getArgs()) {
                            if (arg instanceof IRVariable && V.get((IRVariable) arg).a == 1) {
                                tmp.getArgs().add(V.get((IRVariable) arg).b);
                            } else {
                                tmp.getArgs().add(arg);
                            }
                        }
                        insts.add(tmp);
                    } else if (inst instanceof IRGetelementptr) {
                        if (V.get(((IRGetelementptr) inst).getDef()).a == 2) {
                            var tmp = new IRGetelementptr(inst.getId(), ((IRGetelementptr) inst).getDest(),
                                    ((IRGetelementptr) inst).getType(), ((IRGetelementptr) inst).getPtr(),
                                    new ArrayList<>());
                            for (var arg : ((IRGetelementptr) inst).getInfolist()) {
                                if (arg instanceof IRVariable && V.get((IRVariable) arg).a == 1) {
                                    tmp.getInfolist().add(V.get((IRVariable) arg).b);
                                } else {
                                    tmp.getInfolist().add(arg);
                                }
                            }
                            insts.add(tmp);
                        }
                    } else if (inst instanceof IRIcmp) {
                        if (V.get(((IRIcmp) inst).getDef()).a == 2) {
                            var tmp = new IRIcmp(inst.getId(), ((IRIcmp) inst).getDest(), ((IRIcmp) inst).getCond(),
                                    ((IRIcmp) inst).getType(), ((IRIcmp) inst).getLhs(), ((IRIcmp) inst).getRhs());
                            if (((IRIcmp) inst).getLhs() instanceof IRVariable
                                    && V.get(((IRIcmp) inst).getLhs()).a == 1) {
                                tmp.setLhs(V.get(((IRIcmp) inst).getLhs()).b);
                            }
                            if (((IRIcmp) inst).getRhs() instanceof IRVariable
                                    && V.get(((IRIcmp) inst).getRhs()).a == 1) {
                                tmp.setRhs(V.get(((IRIcmp) inst).getRhs()).b);
                            }
                            insts.add(tmp);
                        }
                    } else if (inst instanceof IRLoad) {
                        if (V.get(((IRLoad) inst).getDef()).a != 2) {
                            throw new OPTError("Invalid Load in SCCP");
                        }
                        insts.add(inst);
                    } else if (inst instanceof IRRet) {
                        if (((IRRet) inst).getValue() != null && ((IRRet) inst).getValue() instanceof IRVariable
                                && V.get(((IRRet) inst).getValue()).a == 1) {
                            insts.add(new IRRet(inst.getId(), V.get(((IRRet) inst).getValue()).b));
                        } else {
                            insts.add(inst);
                        }
                    } else if (inst instanceof IRStore) {
                        if (((IRStore) inst).getSrc() instanceof IRVariable
                                && V.get(((IRStore) inst).getSrc()).a == 1) {
                            insts.add(new IRStore(inst.getId(), ((IRStore) inst).getDest(),
                                    V.get(((IRStore) inst).getSrc()).b));
                        } else {
                            insts.add(inst);
                        }
                    } else {
                        throw new OPTError("Invalid inst in SCCP");
                    }
                }
                block.setInsts(insts);
                var inst = block.getReturnInst();
                if (inst instanceof IRBranch) {
                    if (((IRBranch) inst).isJump()) {
                        block.setReturnInst(inst);
                    } else {
                        if (((IRBranch) inst).getCond() instanceof IRVariable) {
                            if (((IRBranch) inst).getCond().getValue().equals("%.tmp.binary.19")) {
                                int a = 1;
                            }
                            if (V.get(((IRBranch) inst).getCond()).a == 1) {
                                if (V.get(((IRBranch) inst).getCond()).b.getValue().equals("1")) {
                                    block.setReturnInst(new IRBranch(inst.getId(), ((IRBranch) inst).getTrueLabel()));
                                } else if (V.get(((IRBranch) inst).getCond()).b.getValue().equals("0")) {
                                    block.setReturnInst(new IRBranch(inst.getId(), ((IRBranch) inst).getFalseLabel()));
                                } else {
                                    throw new OPTError("Invalid value in branch cond in SCCP");
                                }
                            } else {
                                block.setReturnInst(inst);
                            }
                        } else {
                            if (((IRBranch) inst).getCond().getValue().equals("1")) {
                                block.setReturnInst(new IRBranch(inst.getId(), ((IRBranch) inst).getTrueLabel()));
                            } else if (((IRBranch) inst).getCond().getValue().equals("0")) {
                                block.setReturnInst(new IRBranch(inst.getId(), ((IRBranch) inst).getFalseLabel()));
                            } else {
                                throw new OPTError("Invalid value in branch cond in SCCP");
                            }
                        }
                    }
                } else if (inst instanceof IRRet) {
                    if (((IRRet) inst).getValue() != null && ((IRRet) inst).getValue() instanceof IRVariable
                            && V.get(((IRRet) inst).getValue()).a == 1) {
                        block.setReturnInst(new IRRet(inst.getId(), V.get(((IRRet) inst).getValue()).b));
                    } else {
                        block.setReturnInst(inst);
                    }
                } else {
                    throw new OPTError("Invalid return inst in SCCP");
                }
                Blockstmts.add(block);
            }
        }
        func.setBlockstmts(Blockstmts);
    }

    public void CodeMove(IRRoot root) {
        for (var func : root.getFuncs()) {
            codemove(func);
        }
    }

    public void codemove(IRFuncDef func) {
        Var2Pair = new HashMap<>();
        var WorkList = new HashSet<IRInst>();
        for (var para : func.getParams()) {
            Var2Pair.put(para, null);
        }
        for (var block : func.getOrder2Block()) {
            init(block, WorkList);
        }
        for (var block : func.getOrder2Block()) {
            for (int i = 0; i < block.getInsts().size(); ++i) {
                var inst = block.getInsts().get(i);
                if (!WorkList.contains(inst)) {
                    continue;
                }
                WorkList.remove(inst);
                ArrayList<IRVariable> use = new ArrayList<>();
                for (var unit : inst.getUses()) {
                    if (Var2Pair.get(unit) != null) {
                        use.add(unit);
                    }
                }
                if (use.size() == 0) {
                    continue;
                }
                var pair = FindEarly(block, use);
                if (pair == null) {
                    continue;
                }
                var earlyBlock = pair.a;
                var earlyInst = pair.b;
                int index;
                if (earlyInst != null) {
                    index = earlyBlock.getInsts().indexOf(earlyInst);
                    block.getInsts().remove(i);
                    earlyBlock.getInsts().add(index + 1, inst);
                    Var2Pair.put(inst.getDest(), new Pair<>(earlyBlock, inst));
                } else {
                    index = 0;
                    block.getInsts().remove(i);
                    earlyBlock.getInsts().add(index, inst);
                    Var2Pair.put(inst.getDest(), new Pair<>(earlyBlock, inst));
                }
            }
        }
    }

    public Pair<IRBlock, IRInst> FindEarly(IRBlock block, ArrayList<IRVariable> use) {
        Pair<IRBlock, IRInst> res = null;
        for (var var : use) {
            var pair = Var2Pair.get(var);
            if (pair == null) {
                continue;
            }
            if (pair.b instanceof IRPhi) {
                pair = new Pair<IRBlock, IRInst>(pair.a, null);
            }
            var tmp = block;
            while (tmp.getIdom() != tmp && !tmp.equals(pair.a)) {
                tmp = tmp.getIdom();
            }
            if (!tmp.equals(pair.a)) {
                throw new OPTError("Invalid idom in CodeMove");
            }
            if (res == null) {
                res = pair;
            } else {
                if (pair.a.equals(res.a)) {
                    if (pair.b != null) {
                        var index_res = res.a.getInsts().indexOf(res.b);
                        var index_pair = pair.a.getInsts().indexOf(pair.b);
                        if (index_pair > index_res) {
                            res = pair;
                        }
                    }
                } else {
                    var tmp1 = pair.a;
                    while (tmp1.getIdom() != tmp1 && !tmp1.equals(res.a)) {
                        tmp1 = tmp1.getIdom();
                    }
                    if (tmp1.equals(res.a)) {
                        res = pair;
                    }
                }
            }
        }
        return res;
    }

    public void init(IRBlock block, HashSet<IRInst> WorkList) {
        for (var inst : block.getPhiList().values()) {
            Var2Pair.put(((IRPhi) inst).getDest(), new Pair<>(block, inst));
        }
        for (var inst : block.getInsts()) {
            if (!(inst instanceof IRLoad || inst instanceof IRStore || inst instanceof IRCall)) {
                WorkList.add(inst);
            }
            if (inst instanceof IRArith) {
                Var2Pair.put(((IRArith) inst).getDest(), new Pair<>(block, inst));
            } else if (inst instanceof IRPhi) {
                throw new OPTError("Phi in CodeMove");
            } else if (inst instanceof IRLoad) {
                Var2Pair.put(((IRLoad) inst).getDest(), new Pair<>(block, inst));
            } else if (inst instanceof IRGetelementptr) {
                Var2Pair.put(((IRGetelementptr) inst).getDest(), new Pair<>(block, inst));
            } else if (inst instanceof IRIcmp) {
                Var2Pair.put(((IRIcmp) inst).getDest(), new Pair<>(block, inst));
            } else if (inst instanceof IRCall) {
                if (((IRCall) inst).getDest() != null) {
                    Var2Pair.put(((IRCall) inst).getDest(), new Pair<>(block, inst));
                }
            } else if (inst instanceof IRAlloca) {
                throw new OPTError("Alloca in CodeMove");
            }
            // else{
            // throw new OPTError("Invalid inst in CodeMove");
            // }
        }
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
            // if (inst instanceof IRPhi && ((IRPhi)
            // inst).getDest().getValue().equals("%.tmp.binary.3.inline.1")) {
            // System.out.println("debug");
            // }
            // if (inst instanceof IRPhi && ((IRPhi)
            // inst).getDest().getValue().equals("%.tmp.binary.3.inline.8")) {
            // System.out.println("debug");
            // }
            EreaseWorkSet.add(inst);
        }
        while (!EreaseWorkSet.isEmpty()) {
            var S = EreaseWorkSet.iterator().next();
            // if (S instanceof IRPhi && ((IRPhi)
            // S).getDest().getValue().equals("%.tmp.binary.3.inline.1")) {
            // System.out.println("debug");
            // }
            // if (S instanceof IRPhi && ((IRPhi)
            // S).getDest().getValue().equals("%.tmp.binary.3.inline.8")) {
            // System.out.println("debug");
            // }
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
                    S = new IRArith(++InstCounter.InstCounter, dest, "add", type, literal,
                            new IRLiteral(GlobalScope.irIntType, "0"));
                    block.addFront(S);
                    Inst2Block.put(S, block);
                }
            }
            if (S instanceof IRBranch && ((IRBranch) S).getCond() instanceof IRLiteral) {
                if (((IRBranch) S).isJump())
                    continue;
                var cond = (IRLiteral) ((IRBranch) S).getCond();
                var TrueLabel = ((IRBranch) S).getTrueLabel();
                var FalseLabel = ((IRBranch) S).getFalseLabel();
                IRBlock outBlock, inBlock = null;
                if (cond.getValue().equals("0")) {
                    ((IRBranch) S).setCond(new IRLiteral(GlobalScope.irBoolType, "true"));
                    ((IRBranch) S).setTrueLabel(FalseLabel);
                    ((IRBranch) S).setJump(true);
                    outBlock = Inst2Block.get(S);
                    for (var succ : outBlock.getSuccessors()) {
                        if (succ.getLabelName().equals(TrueLabel)) {
                            inBlock = succ;
                            inBlock.getPredecessors().remove(outBlock);
                            outBlock.getSuccessors().remove(inBlock);
                            break;
                        }
                    }
                } else if (cond.getValue().equals("1")) {
                    ((IRBranch) S).setCond(new IRLiteral(GlobalScope.irBoolType, "true"));
                    ((IRBranch) S).setFalseLabel(TrueLabel);
                    ((IRBranch) S).setJump(true);
                    outBlock = Inst2Block.get(S);
                    for (var succ : outBlock.getSuccessors()) {
                        if (succ.getLabelName().equals(FalseLabel)) {
                            inBlock = succ;
                            inBlock.getPredecessors().remove(outBlock);
                            outBlock.getSuccessors().remove(inBlock);
                            break;
                        }
                    }
                } else {
                    continue;
                }
                RmvPhi(outBlock, inBlock);
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

    public void RmvPhi(IRBlock outBlock, IRBlock inBlock) {
        for (var phi : inBlock.getPhiList().entrySet()) {
            var phiInst = phi.getValue();
            for (int i = 0; i < phiInst.getLabels().size(); ++i) {
                var label = phiInst.getLabels().get(i);
                if (label.equals(outBlock.getLabelName())) {
                    phiInst.getVals().remove(i);
                    phiInst.getLabels().remove(i);
                    if (phiInst.getVals().isEmpty()) {
                        throw new OPTError("PhiInst is empty");
                    }
                    EreaseWorkSet.add(phiInst);
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
        int lhs = lhsStr.equals("null") ? 0 : Integer.parseInt(lhsStr);
        int rhs = rhsStr.equals("null") ? 0 : Integer.parseInt(rhsStr);
        switch (op) {
            case "add":
                return lhs + rhs;
            case "sub":
                return lhs - rhs;
            case "mul":
                return lhs * rhs;
            case "sdiv": {
                if (rhs == 0) {
                    return 0;
                }
                return lhs / rhs;
            }

            case "srem":
                return lhs % rhs;
            case "shl":
                return lhs << rhs;
            case "ashr":
                return lhs >> rhs;
            case "and":
                return lhs & rhs;
            case "or":
                return lhs | rhs;
            case "xor":
                return lhs ^ rhs;
            case "eq":
                return lhs == rhs ? 1 : 0;
            case "ne":
                return lhs != rhs ? 1 : 0;
            case "slt":
                return lhs < rhs ? 1 : 0;
            case "sgt":
                return lhs > rhs ? 1 : 0;
            case "sle":
                return lhs <= rhs ? 1 : 0;
            case "sge":
                return lhs >= rhs ? 1 : 0;
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
    public OPTError visit(IRCall node) throws BaseError {
        // SideEffectInst.add(node);
        Inst2Block.put(node, currentBlock);
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
