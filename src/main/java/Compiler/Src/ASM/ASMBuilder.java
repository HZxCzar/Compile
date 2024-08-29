package Compiler.Src.ASM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;

import Compiler.Src.ASM.Entity.*;
import Compiler.Src.ASM.Node.*;
import Compiler.Src.ASM.Node.Global.*;
import Compiler.Src.ASM.Node.Inst.Arithmetic.*;
import Compiler.Src.ASM.Node.Inst.Control.*;
import Compiler.Src.ASM.Node.Inst.Memory.*;
import Compiler.Src.ASM.Node.Inst.Presudo.*;
import Compiler.Src.ASM.Node.Stmt.*;
import Compiler.Src.ASM.Node.Util.ASMLabel;
import Compiler.Src.ASM.Util.*;
import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Entity.*;
import Compiler.Src.IR.Node.*;
import Compiler.Src.IR.Node.Def.*;
import Compiler.Src.IR.Node.Inst.*;
import Compiler.Src.IR.Node.Stmt.*;
import Compiler.Src.IR.Type.*;
import Compiler.Src.Util.Error.ASMError;
import Compiler.Src.Util.Error.BaseError;
import Compiler.Src.Util.Error.OPTError;

public class ASMBuilder extends ASMControl implements IRVisitor<ASMNode> {
    @Override
    public ASMNode visit(IRNode node) throws BaseError {
        throw new ASMError("Unknown IR node type");
    }

    @Override
    public ASMNode visit(IRRoot node) throws BaseError {
        var root = new ASMRoot();
        for (var def : node.getDefs()) {
            if (def instanceof IRStrDef) {
                root.getStrs().add((ASMStrDef) def.accept(this));
            } else {
                var vars = (ASMVarDef) def.accept(this);
                if (vars != null) {
                    root.getVars().add(vars);
                }
            }
        }
        for (var func : node.getFuncs()) {
            root.getFuncs().add((ASMFuncDef) func.accept(this));
        }
        return root;
    }

    @Override
    public ASMNode visit(IRFuncDef node) throws BaseError {
        label2block = new TreeMap<>();
        var funcDef = new ASMFuncDef(node.getName(), node.getParams().size());
        funcBlocks = new ArrayList<>();
        counter = new ASMCounter();
        var paramCount = 0;
        var initStmt = new ASMBlock(new ASMLabel(node.getName()));
        var offsetStack = (4 * (node.getParams().size() - 8) + 15) / 16 * 16;
        for (var param : node.getParams()) {
            var paramInst = (ASMStmt) param.accept(this);
            var paramDest = paramInst.getDest();
            if (paramCount < 8) {
                initStmt.appendInsts(StoreAt(getArgReg(paramCount), 4 * ((ASMVirtualReg) paramDest).getOffset()));
            } else {
                initStmt.appendInsts(LoadAt(regs.getT1(), offsetStack - 4 * (paramCount - 7)));
                initStmt.appendInsts(StoreAt(regs.getT1(), 4 * ((ASMVirtualReg) paramDest).getOffset()));
            }
            paramCount++;
        }

        funcBlocks.add(initStmt);
        for (var block : node.getBlockstmts()) {
            CalcCFG(block);
        }
        for (var block : node.getBlockstmts()) {
            block.accept(this);
        }
        for (var block : funcBlocks) {
            block.PhiMove(this);
        }
        funcDef.setBlocks(funcBlocks);
        // funcDef.Formolize(this);
        Formolize(funcDef);
        return funcDef;
    }

    public void CalcCFG(IRBlock node) {
        var block = new ASMBlock(new ASMLabel(node.getLabelName().getLabel()));
        label2block.put(node.getLabelName().getLabel(), block);
        if (node.getReturnInst() instanceof IRRet) {
            block.setSuccessor(new ArrayList<>());
        } else if (node.getReturnInst() instanceof IRBranch) {
            if (((IRBranch) node.getReturnInst()).isJump()) {
                block.setSuccessor(new ArrayList<String>(
                        Arrays.asList(((IRBranch) node.getReturnInst()).getTrueLabel().getLabel())));
            } else {
                var trueLabel = ((IRBranch) node.getReturnInst()).getTrueLabel().getLabel();
                var falseLabel = ((IRBranch) node.getReturnInst()).getFalseLabel().getLabel();
                block.setSuccessor(new ArrayList<>());
                block.getSuccessor().add(trueLabel);
                block.getSuccessor().add(falseLabel);
            }
        } else {
            throw new ASMError("Unknown return inst");
        }
    }

    @Override
    public ASMNode visit(IRStrDef node) throws BaseError {
        var str = node.getValue_old()
                .replace("\\", "\\\\")
                .replace("\n", "\\n")
                .replace("\0", "")
                .replace("\t", "\\t")
                .replace("\"", "\\\"");
        var StrDef = new ASMStrDef(node.getVars().getValue().substring(1), str);
        return StrDef;
    }

    @Override
    public ASMNode visit(IRBlock node) throws BaseError {
        var block = label2block.get(node.getLabelName().getLabel());

        // Phi remove
        var label2new = new TreeMap<String, String>();
        for (var phi : node.getPhiList().values()) {
            var destInst = (ASMStmt) phi.getDest().accept(this);
            for (var label : phi.getLabels()) {
                var blockLabel = label.getLabel();
                if (!label2block.containsKey(blockLabel)) {
                    throw new ASMError("ASM: phi label not found");
                }
                var predBlock = label2block.get(blockLabel);
                var srcInst = (ASMStmt) phi.getVals().get(phi.getLabels().indexOf(label)).accept(this);
                if (predBlock.getSuccessor().size() == 1) {
                    predBlock.getPhiStmt().appendInsts(srcInst);
                    predBlock.getPhiStmt().appendInsts(destInst);
                    if (predBlock.getSrc2dest().containsKey((ASMVirtualReg) srcInst.getDest())) {
                        predBlock.getSrc2dest().get((ASMVirtualReg) srcInst.getDest())
                                .add((ASMVirtualReg) destInst.getDest());
                    } else {
                        predBlock.getSrc2dest().put((ASMVirtualReg) srcInst.getDest(),
                                new ArrayList<>(Arrays.asList((ASMVirtualReg) destInst.getDest())));
                    }

                } else {
                    ASMBlock midBlock;
                    if (!label2new.containsKey(blockLabel)) {
                        midBlock = new ASMBlock(new ASMLabel(
                                node.getLabelName().getLabel() + "." + blockLabel + ".PhiCreate."
                                        + (++CreateblockCnt)));
                        funcBlocks.add(midBlock);
                        label2new.put(blockLabel, midBlock.getLabel().getLabel());
                        label2block.put(midBlock.getLabel().getLabel(), midBlock);
                        predBlock.replaceLabel(block.getLabel().getLabel(), midBlock.getLabel().getLabel());
                        var jumpInst = new ASMStmt();
                        jumpInst.addInst(new ASMJump(node.getLabelName().getLabel()));
                        midBlock.setReturnInst(jumpInst);
                    } else {
                        midBlock = label2block.get(label2new.get(blockLabel));
                    }

                    midBlock.getPhiStmt().appendInsts(srcInst);
                    midBlock.getPhiStmt().appendInsts(destInst);
                    if (midBlock.getSrc2dest().containsKey((ASMVirtualReg) srcInst.getDest())) {
                        midBlock.getSrc2dest().get((ASMVirtualReg) srcInst.getDest())
                                .add((ASMVirtualReg) destInst.getDest());
                    } else {
                        midBlock.getSrc2dest().put((ASMVirtualReg) srcInst.getDest(),
                                new ArrayList<>(Arrays.asList((ASMVirtualReg) destInst.getDest())));
                    }
                }
            }
        }

        for (var stmt : node.getInsts()) {
            block.appendInsts((ASMStmt) stmt.accept(this));
        }
        var returnInst = (ASMStmt) node.getReturnInst().accept(this);
        block.setReturnInst(returnInst);
        funcBlocks.add(block);
        return new ASMStmt();
    }

    @Override
    public ASMNode visit(IRGlobalDef node) throws BaseError {
        if (node.getVars().getType() instanceof IRStructType) {
            return null;
        }
        return new ASMVarDef(node.getVars().getValue().substring(1), 0);
    }

    @Override
    public ASMNode visit(IRAlloca node) throws BaseError {
        throw new OPTError("Alloca should be eliminated");
        // var instList = new ASMStmt();
        // var DestInst = (ASMStmt) node.getDest().accept(this);
        // var allocaDest = DestInst.getDest();
        // var Dest = new ASMVirtualReg(counter);
        // if (allocaDest instanceof ASMPhysicalReg) {
        // throw new ASMError("not supose to use this in Naive ASM");
        // }
        // instList.addInst(new ASMLi(regs.getA0(), 4 * ((ASMVirtualReg)
        // Dest).getOffset()));
        // instList.addInst(new ASMArithR("add", regs.getA0(), regs.getSp(),
        // regs.getA0()));
        // instList.addInst(new ASMLi(regs.getT0(), 4 * ((ASMVirtualReg)
        // allocaDest).getOffset()));
        // instList.addInst(new ASMArithR("add", regs.getT0(), regs.getT0(),
        // regs.getSp()));
        // instList.addInst(new ASMStore("sw", regs.getA0(), 0, regs.getT0()));
        // return instList;
    }

    @Override
    public ASMNode visit(IRArith node) throws BaseError {
        var instList = new ASMStmt();
        var destInst = (ASMStmt) node.getDest().accept(this);
        instList.appendInsts(destInst);
        var dest = destInst.getDest();
        if ((node.getLhs() instanceof IRLiteral)) {
            var content = (((IRLiteral) node.getLhs()).getValue().equals("null") ? 0
                    : Integer.parseInt(((IRLiteral) node.getLhs()).getValue()));
            instList.addInst(new ASMLi(regs.getA1(), content));
        } else {
            var lhsInst = (ASMStmt) node.getLhs().accept(this);
            instList.appendInsts(lhsInst);
            var lhsDest = lhsInst.getDest();
            instList.appendInsts(LoadAt(regs.getA1(), 4 * ((ASMVirtualReg) lhsDest).getOffset()));
        }

        if ((node.getRhs() instanceof IRLiteral)) {
            var content = (((IRLiteral) node.getRhs()).getValue().equals("null") ? 0
                    : Integer.parseInt(((IRLiteral) node.getRhs()).getValue()));
            instList.addInst(new ASMLi(regs.getA2(), content));
        } else {
            var rhsInst = (ASMStmt) node.getRhs().accept(this);
            instList.appendInsts(rhsInst);
            var rhsDest = rhsInst.getDest();
            instList.appendInsts(LoadAt(regs.getA2(), 4 * ((ASMVirtualReg) rhsDest).getOffset()));
        }

        var op = node.getOp();
        switch (op) {
            case "add" -> {
                instList.addInst(new ASMArithR("add", regs.getA0(), regs.getA1(), regs.getA2()));
            }
            case "sub" -> {
                instList.addInst(new ASMArithR("sub", regs.getA0(), regs.getA1(), regs.getA2()));
            }
            case "mul" -> {
                instList.addInst(new ASMArithR("mul", regs.getA0(), regs.getA1(), regs.getA2()));
            }
            case "sdiv" -> {
                instList.addInst(new ASMArithR("div", regs.getA0(), regs.getA1(), regs.getA2()));
            }
            case "srem" -> {
                instList.addInst(new ASMArithR("rem", regs.getA0(), regs.getA1(), regs.getA2()));
            }
            case "shl" -> {
                instList.addInst(new ASMArithR("sll", regs.getA0(), regs.getA1(), regs.getA2()));
            }
            case "ashr" -> {
                instList.addInst(new ASMArithR("sra", regs.getA0(), regs.getA1(), regs.getA2()));
            }
            case "and" -> {
                instList.addInst(new ASMArithR("and", regs.getA0(), regs.getA1(), regs.getA2()));
            }
            case "or" -> {
                instList.addInst(new ASMArithR("or", regs.getA0(), regs.getA1(), regs.getA2()));
            }
            case "xor" -> {
                instList.addInst(new ASMArithR("xor", regs.getA0(), regs.getA1(), regs.getA2()));
            }
            default -> throw new ASMError("Unknown Binary operation");
        }
        instList.appendInsts(StoreAt(regs.getA0(), 4 * ((ASMVirtualReg) dest).getOffset()));
        return instList;
    }

    @Override
    public ASMNode visit(IRIcmp node) throws BaseError {
        var InstList = new ASMStmt();
        var destInst = (ASMStmt) node.getDest().accept(this);
        InstList.appendInsts(destInst);
        var dest = destInst.getDest();
        if ((node.getLhs() instanceof IRLiteral)) {
            var content = (((IRLiteral) node.getLhs()).getValue().equals("null") ? 0
                    : Integer.parseInt(((IRLiteral) node.getLhs()).getValue()));
            InstList.addInst(new ASMLi(regs.getA1(), content));
        } else {
            var lhsInst = (ASMStmt) node.getLhs().accept(this);
            InstList.appendInsts(lhsInst);
            var lhsDest = lhsInst.getDest();
            InstList.appendInsts(LoadAt(regs.getA1(), 4 * ((ASMVirtualReg) lhsDest).getOffset()));
        }

        if ((node.getRhs() instanceof IRLiteral)) {
            var content = (((IRLiteral) node.getRhs()).getValue().equals("null") ? 0
                    : Integer.parseInt(((IRLiteral) node.getRhs()).getValue()));
            InstList.addInst(new ASMLi(regs.getA2(), content));
        } else {
            var rhsInst = (ASMStmt) node.getRhs().accept(this);
            InstList.appendInsts(rhsInst);
            var rhsDest = rhsInst.getDest();
            InstList.appendInsts(LoadAt(regs.getA2(), 4 * ((ASMVirtualReg) rhsDest).getOffset()));
        }
        var cond = node.getCond();
        switch (cond) {
            case "eq" -> {
                InstList.addInst(new ASMArithR("xor", regs.getA0(), regs.getA1(), regs.getA2()));
                InstList.addInst(new ASMUnarry("seqz", regs.getA0(), regs.getA0()));
            }
            case "ne" -> {
                InstList.addInst(new ASMArithR("xor", regs.getA0(), regs.getA1(), regs.getA2()));
                InstList.addInst(new ASMUnarry("snez", regs.getA0(), regs.getA0()));
            }
            case "slt" -> {
                InstList.addInst(new ASMArithR("slt", regs.getA0(), regs.getA1(), regs.getA2()));
            }
            case "sgt" -> {
                InstList.addInst(new ASMArithR("slt", regs.getA0(), regs.getA2(), regs.getA1()));
            }
            case "sle" -> {
                InstList.addInst(new ASMArithR("slt", regs.getA0(), regs.getA2(), regs.getA1()));
                InstList.addInst(new ASMUnarry("seqz", regs.getA0(), regs.getA0()));
            }
            case "sge" -> {
                InstList.addInst(new ASMArithR("slt", regs.getA0(), regs.getA1(), regs.getA2()));
                InstList.addInst(new ASMUnarry("seqz", regs.getA0(), regs.getA0()));
            }
            default -> throw new ASMError("Unknown Binary operation");
        }
        InstList.appendInsts(StoreAt(regs.getA0(), 4 * ((ASMVirtualReg) dest).getOffset()));
        return InstList;
    }

    @Override
    public ASMNode visit(IRBranch node) throws BaseError {
        var InstList = new ASMStmt();
        if (node.isJump()) {
            InstList.addInst(new ASMJump(node.getTrueLabel().getLabel()));
        } else {
            var condInst = (ASMStmt) node.getCond().accept(this);
            var condDest = condInst.getDest();
            InstList.appendInsts(condInst);
            InstList.appendInsts(LoadAt(regs.getA0(), 4 * ((ASMVirtualReg) condDest).getOffset()));
            InstList.addInst(new ASMUnarry("seqz", regs.getA0(), regs.getA0()));
            InstList.addInst(new ASMBezq(regs.getA0(), node.getTrueLabel().getLabel()));
            InstList.addInst(new ASMJump(node.getFalseLabel().getLabel()));
        }
        return InstList;
    }

    @Override
    public ASMNode visit(IRCall node) throws BaseError {
        var InstList = new ASMStmt();
        int argNum = 0;
        int offset = 0;
        var args = new ArrayList<ASMStmt>();
        if (node.getFuncName().equals("__malloc_array") || node.getFuncName().equals("_malloc")) {
            if (node.getArgs().size() > 2) {
                throw new ASMError("malloc should have 1 or 2 args");
            }
            if (node.getFuncName().equals("__malloc_array")) {
                var sizeInst = (ASMStmt) node.getArgs().get(0).accept(this);
                args.add(sizeInst);
                InstList.appendInsts(sizeInst);
                IRLiteral typearg = (IRLiteral) node.getArgs().get(1);
                int value = Integer.parseInt(typearg.getValue());
                if (value % 4 != 0) {
                    var typearg_new = new IRLiteral(typearg.getType(), String.valueOf(value + 4 - value % 4));
                    var typeInst = (ASMStmt) typearg_new.accept(this);
                    args.add(typeInst);
                    InstList.appendInsts(typeInst);
                } else {
                    var typeInst = (ASMStmt) typearg.accept(this);
                    args.add(typeInst);
                    InstList.appendInsts(typeInst);
                }
            } else {
                IRLiteral typearg = (IRLiteral) node.getArgs().get(0);
                int value = Integer.parseInt(typearg.getValue());
                if (value % 4 != 0) {
                    var typearg_new = new IRLiteral(typearg.getType(), String.valueOf(value + 4 - value % 4));
                    var typeInst = (ASMStmt) typearg_new.accept(this);
                    args.add(typeInst);
                    InstList.appendInsts(typeInst);
                } else {
                    var typeInst = (ASMStmt) typearg.accept(this);
                    args.add(typeInst);
                    InstList.appendInsts(typeInst);
                }
            }
        } else {
            for (var arg : node.getArgs()) {
                var argInst = (ASMStmt) arg.accept(this);
                args.add(argInst);
                InstList.appendInsts(argInst);
            }
        }
        for (int i = 0; i < args.size(); i++) {
            var argInst = args.get(i);
            var argDest = argInst.getDest();
            if (argNum < 8) {
                InstList.appendInsts(LoadAt(getArgReg(argNum), 4 * ((ASMVirtualReg) argDest).getOffset()));
                // InstList.addInst(new ASMLi(regs.getT0(), 4 * ((ASMVirtualReg)
                // argDest).getOffset()));
                // InstList.addInst(new ASMArithR("add", regs.getT0(), regs.getT0(),
                // regs.getSp()));
                // InstList.addInst(
                // new ASMLoad("lw", getArgReg(argNum), 0, regs.getT0()));
            } else {
                ++offset;
                // InstList.addInst(new ASMLi(regs.getT0(), 4 * ((ASMVirtualReg)
                // argDest).getOffset()));
                // InstList.addInst(new ASMArithR("add", regs.getT0(), regs.getT0(),
                // regs.getSp()));
                // InstList.addInst(
                // new ASMLoad("lw", regs.getT0(), 0, regs.getT0()));
                InstList.appendInsts(LoadAt(regs.getT2(), 4 * ((ASMVirtualReg) argDest).getOffset()));
                InstList.appendInsts(StoreAt(regs.getT2(), -4 * offset));
                // InstList.addInst(new ASMLi(regs.getT2(), -4 * offset));
                // InstList.addInst(new ASMArithR("add", regs.getT2(), regs.getT2(),
                // regs.getSp()));
                // InstList.addInst(new ASMStore("sw", regs.getT0(), 0, regs.getT2()));
            }
            ++argNum;
        }
        var offsetStack = (4 * offset + 15) / 16 * 16;
        if (offset != 0) {
            InstList.addInst(new ASMLi(regs.getT0(), -offsetStack));
            InstList.addInst(new ASMArithR("add", regs.getSp(), regs.getSp(), regs.getT0()));
        }
        InstList.addInst(new ASMCall(node.getFuncName()));
        if (offset != 0) {
            InstList.addInst(new ASMLi(regs.getT0(), offsetStack));
            InstList.addInst(new ASMArithR("add", regs.getSp(), regs.getSp(), regs.getT0()));
        }
        if (node.getDest() != null) {
            var destInst = (ASMStmt) node.getDest().accept(this);
            var dest = destInst.getDest();
            InstList.appendInsts(StoreAt(regs.getA0(), 4 * ((ASMVirtualReg) dest).getOffset()));
            // InstList.addInst(new ASMLi(regs.getT0(), 4 * ((ASMVirtualReg) dest).getOffset()));
            // InstList.addInst(new ASMArithR("add", regs.getT0(), regs.getT0(), regs.getSp()));
            // InstList.addInst(new ASMStore("sw", regs.getA0(), 0, regs.getT0()));
        }
        return InstList;
    }

    @Override
    public ASMNode visit(IRGetelementptr node) throws BaseError {
        var InstList = new ASMStmt();
        var ptrInst = (ASMStmt) node.getPtr().accept(this);
        var destInst = (ASMStmt) node.getDest().accept(this);
        var indexInst = (ASMStmt) node.getInfolist().get(node.getInfolist().size() - 1).accept(this);
        InstList.appendInsts(ptrInst);
        InstList.appendInsts(destInst);
        InstList.appendInsts(indexInst);
        var ptrDest = ptrInst.getDest();
        var destDest = destInst.getDest();
        var indexDest = indexInst.getDest();
        InstList.appendInsts(LoadAt(regs.getA1(), 4 * ((ASMVirtualReg) ptrDest).getOffset()));
        // InstList.addInst(new ASMLi(regs.getT0(), 4 * ((ASMVirtualReg) ptrDest).getOffset()));
        // InstList.addInst(new ASMArithR("add", regs.getT0(), regs.getT0(), regs.getSp()));
        // InstList.addInst(new ASMLoad("lw", regs.getA1(), 0, regs.getT0()));
        InstList.appendInsts(LoadAt(regs.getA2(), 4 * ((ASMVirtualReg) indexDest).getOffset()));
        // InstList.addInst(new ASMLi(regs.getT0(), 4 * ((ASMVirtualReg) indexDest).getOffset()));
        // InstList.addInst(new ASMArithR("add", regs.getT0(), regs.getT0(), regs.getSp()));
        // InstList.addInst(new ASMLoad("lw", regs.getA2(), 0, regs.getT0()));
        InstList.addInst(new ASMArithI("slli", regs.getA2(), regs.getA2(), 2));
        InstList.addInst(new ASMArithR("add", regs.getA0(), regs.getA1(), regs.getA2()));
        InstList.appendInsts(StoreAt(regs.getA0(), 4 * ((ASMVirtualReg) destDest).getOffset()));
        // InstList.addInst(new ASMLi(regs.getT0(), 4 * ((ASMVirtualReg) destDest).getOffset()));
        // InstList.addInst(new ASMArithR("add", regs.getT0(), regs.getT0(), regs.getSp()));
        // InstList.addInst(new ASMStore("sw", regs.getA0(), 0, regs.getT0()));
        return InstList;
    }

    @Override
    public ASMNode visit(IRRet node) throws BaseError {
        var InstList = new ASMStmt();
        if (node.isVoidtype()) {
            InstList.addInst(new ASMRet());
        } else {
            var valueInst = (ASMStmt) node.getValue().accept(this);
            InstList.appendInsts(valueInst);
            var valueDest = valueInst.getDest();
            // InstList.addInst(new ASMLi(regs.getT0(), 4 * ((ASMVirtualReg) valueDest).getOffset()));
            // InstList.addInst(new ASMArithR("add", regs.getT0(), regs.getT0(), regs.getSp()));
            // InstList.addInst(new ASMLoad("lw", regs.getA0(), 0, regs.getT0()));
            InstList.appendInsts(LoadAt(regs.getA0(), 4 * ((ASMVirtualReg) valueDest).getOffset()));
            InstList.addInst(new ASMRet());
        }
        return InstList;
    }

    @Override
    public ASMNode visit(IRLoad node) throws BaseError {
        var InstList = new ASMStmt();
        var destInst = (ASMStmt) node.getDest().accept(this);
        var ptrInst = (ASMStmt) node.getPtr().accept(this);
        InstList.appendInsts(destInst);
        InstList.appendInsts(ptrInst);
        var dest = destInst.getDest();
        var ptrDest = ptrInst.getDest();
        InstList.appendInsts(LoadAt(regs.getA0(), 4 * ((ASMVirtualReg) ptrDest).getOffset()));
        // InstList.addInst(new ASMLi(regs.getT0(), 4 * ((ASMVirtualReg) ptrDest).getOffset()));
        // InstList.addInst(new ASMArithR("add", regs.getT0(), regs.getT0(), regs.getSp()));
        // InstList.addInst(new ASMLoad("lw", regs.getA0(), 0, regs.getT0()));
        InstList.addInst(new ASMLoad("lw", regs.getA0(), 0, regs.getA0()));
        InstList.appendInsts(StoreAt(regs.getA0(), 4 * ((ASMVirtualReg) dest).getOffset()));
        // InstList.addInst(new ASMLi(regs.getT0(), 4 * ((ASMVirtualReg) dest).getOffset()));
        // InstList.addInst(new ASMArithR("add", regs.getT0(), regs.getT0(), regs.getSp()));
        // InstList.addInst(new ASMStore("sw", regs.getA0(), 0, regs.getT0()));
        return InstList;
    }

    @Override
    public ASMNode visit(IRPhi node) throws BaseError {
        throw new ASMError("IRPhi should be eliminated");
    }

    @Override
    public ASMNode visit(IRStore node) throws BaseError {
        var InstList = new ASMStmt();
        var srcInst = (ASMStmt) node.getSrc().accept(this);
        var destInst = (ASMStmt) node.getDest().accept(this);
        InstList.appendInsts(srcInst);
        InstList.appendInsts(destInst);
        var src = srcInst.getDest();
        var dest = destInst.getDest();
        InstList.appendInsts(LoadAt(regs.getA1(), 4 * ((ASMVirtualReg) src).getOffset()));
        // InstList.addInst(new ASMLi(regs.getT0(), 4 * ((ASMVirtualReg) src).getOffset()));
        // InstList.addInst(new ASMArithR("add", regs.getT0(), regs.getT0(), regs.getSp()));
        // InstList.addInst(new ASMLoad("lw", regs.getA1(), 0, regs.getT0()));
        InstList.appendInsts(LoadAt(regs.getA0(), 4 * ((ASMVirtualReg) dest).getOffset()));
        // InstList.addInst(new ASMLi(regs.getT0(), 4 * ((ASMVirtualReg) dest).getOffset()));
        // InstList.addInst(new ASMArithR("add", regs.getT0(), regs.getT0(), regs.getSp()));
        // InstList.addInst(new ASMLoad("lw", regs.getA0(), 0, regs.getT0()));
        InstList.addInst(new ASMStore("sw", regs.getA1(), 0, regs.getA0()));
        return InstList;
    }

    @Override
    public ASMNode visit(IREntity node) throws BaseError {
        throw new ASMError("Unknown entity type");
    }

    @Override
    public ASMNode visit(IRVariable node) throws BaseError {
        var instList = new ASMStmt();
        if (node.isGlobal()) {
            var destReg = new ASMVirtualReg(counter);
            instList.addInst(new ASMLoadLabel(regs.getA0(), node.getValue().substring(1)));
            instList.addInst(new ASMLi(regs.getT0(), 4 * destReg.getOffset()));
            instList.addInst(new ASMArithR("add", regs.getT0(), regs.getT0(), regs.getSp()));
            instList.addInst(new ASMStore("sw", regs.getA0(), 0, regs.getT0()));
            instList.setDest(destReg);
        } else {
            var existReg = counter.name2reg.get(node.getValue());
            if (existReg == null) {
                var destReg = new ASMVirtualReg(counter);
                counter.name2reg.put(node.getValue(), destReg);
                instList.setDest(destReg);
            } else {
                if (existReg instanceof ASMVirtualReg) {
                    instList.setDest(existReg);
                } else if (existReg instanceof ASMPhysicalReg) {
                    instList.setDest(existReg);
                } else {
                    throw new ASMError("Unknown register type");
                }
            }
        }
        return instList;
    }

    @Override
    public ASMNode visit(IRLiteral node) throws BaseError {
        var InstList = new ASMStmt();
        var destReg = new ASMVirtualReg(counter);
        // var dest=new
        // ASMImm(node.getValue().equals("null")?0:Integer.parseInt(node.getValue()));
        InstList.addInst(
                new ASMLi(regs.getA0(), node.getValue().equals("null") ? 0 : Integer.parseInt(node.getValue())));
        // InstList.addInst(new ASMLi(regs.getT0(), 4 * destReg.getOffset()));
        // InstList.addInst(new ASMArithR("add", regs.getT0(), regs.getT0(),
        // regs.getSp()));
        // InstList.addInst(new ASMStore("sw", regs.getA0(), 0, regs.getT0()));
        InstList.appendInsts(StoreAt(regs.getA0(), 4 * destReg.getOffset()));
        InstList.setDest(destReg);
        return InstList;
    }
}
