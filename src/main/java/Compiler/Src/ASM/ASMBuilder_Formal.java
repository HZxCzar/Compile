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

public class ASMBuilder_Formal extends ASMControl implements IRVisitor<ASMNode> {
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
            block.PhiMove_Formal(this);
        }
        funcDef.setBlocks(funcBlocks);
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
                var src = phi.getVals().get(phi.getLabels().indexOf(label));
                if (predBlock.getSuccessor().size() == 1) {
                    predBlock.getPhiStmt().appendInsts(destInst);
                    ASMVirtualReg SrcDest;
                    if (src instanceof IRVariable) {
                        var SrcInst = (ASMStmt) src.accept(this);
                        predBlock.getPhiStmt().appendInsts(SrcInst);
                        SrcDest = (ASMVirtualReg) SrcInst.getDest();
                    } else {
                        SrcDest = new ASMVirtualReg("Czar", IRLiteral2Int((IRLiteral) src));
                    }
                    if (predBlock.getSrc2dest().containsKey(SrcDest)) {
                        predBlock.getSrc2dest().get(SrcDest).add((ASMVirtualReg) destInst.getDest());
                    } else {
                        predBlock.getSrc2dest().put(SrcDest,
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
                    midBlock.getPhiStmt().appendInsts(destInst);
                    ASMVirtualReg SrcDest;
                    if (src instanceof IRVariable) {
                        var SrcInst = (ASMStmt) src.accept(this);
                        midBlock.getPhiStmt().appendInsts(SrcInst);
                        SrcDest = (ASMVirtualReg) SrcInst.getDest();
                    } else {
                        SrcDest = new ASMVirtualReg("Czar", IRLiteral2Int((IRLiteral) src));
                    }
                    if (midBlock.getSrc2dest().containsKey(SrcDest)) {
                        midBlock.getSrc2dest().get(SrcDest)
                                .add((ASMVirtualReg) destInst.getDest());
                    } else {
                        midBlock.getSrc2dest().put(SrcDest,
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
        // throw new OPTError("Alloca should be eliminated");
        var InstList = new ASMStmt();
        var DestInst = (ASMStmt) node.getDest().accept(this);
        var allocaDest = DestInst.getDest();
        var Dest = new ASMVirtualReg(counter);
        if (allocaDest instanceof ASMPhysicalReg) {
            throw new ASMError("not supose to use this in Naive ASM");
        }
        InstList.addInst(new ASMLi(regs.getA0(), 4 * ((ASMVirtualReg) Dest).getOffset()));
        InstList.addInst(new ASMArithR("add", regs.getA0(), regs.getSp(), regs.getA0()));
        InstList.addInst(new ASMLi(regs.getT0(), 4 * ((ASMVirtualReg) allocaDest).getOffset()));
        InstList.addInst(new ASMArithR("add", regs.getT0(), regs.getT0(), regs.getSp()));
        InstList.addInst(new ASMStore("sw", regs.getA0(), 0, regs.getT0()));
        return InstList;
    }

    @Override
    public ASMNode visit(IRArith node) throws BaseError {
        var InstList = new ASMStmt();
        var DestInst = (ASMStmt) node.getDest().accept(this);
        var op = node.getOp();
        if (ValidImm(node.getLhs()) && ValidImm(node.getRhs())) {
            var lhs = IRLiteral2Int((IRLiteral) node.getLhs());
            var rhs = IRLiteral2Int((IRLiteral) node.getRhs());
            switch (op) {
                case "add" -> {
                    InstList.addInst(new ASMLi(regs.getA0(), lhs + rhs));
                }
                case "sub" -> {
                    InstList.addInst(new ASMLi(regs.getA0(), lhs - rhs));
                }
                case "mul" -> {
                    InstList.addInst(new ASMLi(regs.getA0(), lhs * rhs));
                }
                case "sdiv" -> {
                    if(rhs==0)
                    {
                        InstList.addInst(new ASMLi(regs.getA0(), 0));
                    }
                    else
                    {
                        InstList.addInst(new ASMLi(regs.getA0(), lhs / rhs));
                    }
                }
                case "srem" -> {
                    InstList.addInst(new ASMLi(regs.getA0(), lhs % rhs));
                }
                case "shl" -> {
                    InstList.addInst(new ASMLi(regs.getA0(), lhs << rhs));
                }
                case "ashr" -> {
                    InstList.addInst(new ASMLi(regs.getA0(), lhs >> rhs));
                }
                case "and" -> {
                    InstList.addInst(new ASMLi(regs.getA0(), lhs & rhs));
                }
                case "or" -> {
                    InstList.addInst(new ASMLi(regs.getA0(), lhs | rhs));
                }
                case "xor" -> {
                    InstList.addInst(new ASMLi(regs.getA0(), lhs ^ rhs));
                }
                default -> throw new ASMError("Unknown Binary operation");
            }
        } else if (ValidImm(node.getRhs())) {
            var rhs = IRLiteral2Int((IRLiteral) node.getRhs());
            if (node.getLhs() instanceof IRVariable) {
                var lhsInst = (ASMStmt) node.getLhs().accept(this);
                InstList.appendInsts(lhsInst);
                var lhsDest = lhsInst.getDest();
                InstList.appendInsts(LoadAt(regs.getA1(), 4 * ((ASMVirtualReg) lhsDest).getOffset()));
            } else {
                InstList.addInst(new ASMLi(regs.getA1(), IRLiteral2Int((IRLiteral) node.getLhs())));
            }
            switch (op) {
                case "add" -> {
                    InstList.addInst(new ASMArithI("addi", regs.getA0(), regs.getA1(), rhs));
                }
                case "sub" -> {
                    InstList.addInst(new ASMArithI("addi", regs.getA0(), regs.getA1(), -rhs));
                }
                case "mul" -> {
                    InstList.addInst(new ASMLi(regs.getA2(), rhs));
                    InstList.addInst(new ASMArithR("mul", regs.getA0(), regs.getA1(), regs.getA2()));
                }
                case "sdiv" -> {
                    InstList.addInst(new ASMLi(regs.getA2(), rhs));
                    InstList.addInst(new ASMArithR("div", regs.getA0(), regs.getA1(), regs.getA2()));
                }
                case "srem" -> {
                    InstList.addInst(new ASMLi(regs.getA2(), rhs));
                    InstList.addInst(new ASMArithR("rem", regs.getA0(), regs.getA1(), regs.getA2()));
                }
                case "shl" -> {
                    InstList.addInst(new ASMArithI("slli", regs.getA0(), regs.getA1(), rhs));
                }
                case "ashr" -> {
                    InstList.addInst(new ASMArithI("srai", regs.getA0(), regs.getA1(), rhs));
                }
                case "and" -> {
                    InstList.addInst(new ASMArithI("andi", regs.getA0(), regs.getA1(), rhs));
                }
                case "or" -> {
                    InstList.addInst(new ASMArithI("ori", regs.getA0(), regs.getA1(), rhs));
                }
                case "xor" -> {
                    InstList.addInst(new ASMArithI("xori", regs.getA0(), regs.getA1(), rhs));
                }
                default -> throw new ASMError("Unknown Binary operation");
            }
        } else if (ValidImm(node.getLhs())) {
            var lhs = IRLiteral2Int((IRLiteral) node.getLhs());
            if (node.getRhs() instanceof IRVariable) {
                var rhsInst = (ASMStmt) node.getRhs().accept(this);
                InstList.appendInsts(rhsInst);
                var rhsDest = rhsInst.getDest();
                InstList.appendInsts(LoadAt(regs.getA2(), 4 * ((ASMVirtualReg) rhsDest).getOffset()));
            } else {
                InstList.addInst(new ASMLi(regs.getA2(), IRLiteral2Int((IRLiteral) node.getRhs())));
            }
            switch (op) {
                case "add" -> {
                    InstList.addInst(new ASMArithI("addi", regs.getA0(), regs.getA2(), lhs));
                }
                case "sub" -> {
                    InstList.addInst(new ASMUnarry("neg", regs.getA2(), regs.getA2()));
                    InstList.addInst(new ASMArithI("addi", regs.getA0(), regs.getA2(), lhs));
                }
                case "mul" -> {
                    InstList.addInst(new ASMLi(regs.getA1(), lhs));
                    InstList.addInst(new ASMArithR("mul", regs.getA0(), regs.getA1(), regs.getA2()));
                }
                case "sdiv" -> {
                    InstList.addInst(new ASMLi(regs.getA1(), lhs));
                    InstList.addInst(new ASMArithR("div", regs.getA0(), regs.getA1(), regs.getA2()));
                }
                case "srem" -> {
                    InstList.addInst(new ASMLi(regs.getA1(), lhs));
                    InstList.addInst(new ASMArithR("rem", regs.getA0(), regs.getA1(), regs.getA2()));
                }
                case "shl" -> {
                    InstList.addInst(new ASMLi(regs.getA1(), lhs));
                    InstList.addInst(new ASMArithR("sll", regs.getA0(), regs.getA1(), regs.getA2()));
                }
                case "ashr" -> {
                    InstList.addInst(new ASMLi(regs.getA1(), lhs));
                    InstList.addInst(new ASMArithR("sra", regs.getA0(), regs.getA1(), regs.getA2()));
                }
                case "and" -> {
                    InstList.addInst(new ASMArithI("andi", regs.getA0(), regs.getA2(), lhs));
                }
                case "or" -> {
                    InstList.addInst(new ASMArithI("ori", regs.getA0(), regs.getA2(), lhs));
                }
                case "xor" -> {
                    InstList.addInst(new ASMArithI("xori", regs.getA0(), regs.getA2(), lhs));
                }
                default -> throw new ASMError("Unknown Binary operation");
            }
        } else {
            if ((node.getLhs() instanceof IRVariable) && (node.getRhs() instanceof IRVariable)) {
                var lhsInst = (ASMStmt) node.getLhs().accept(this);
                var rhsInst = (ASMStmt) node.getRhs().accept(this);
                InstList.appendInsts(lhsInst);
                InstList.appendInsts(rhsInst);
                var lhsDest = lhsInst.getDest();
                var rhsDest = rhsInst.getDest();
                InstList.appendInsts(LoadAt(regs.getA1(), 4 * ((ASMVirtualReg) lhsDest).getOffset()));
                InstList.appendInsts(LoadAt(regs.getA2(), 4 * ((ASMVirtualReg) rhsDest).getOffset()));
            } else if (node.getLhs() instanceof IRVariable) {
                var lhsInst = (ASMStmt) node.getLhs().accept(this);
                InstList.appendInsts(lhsInst);
                var lhsDest = lhsInst.getDest();
                InstList.appendInsts(LoadAt(regs.getA1(), 4 * ((ASMVirtualReg) lhsDest).getOffset()));
                InstList.addInst(new ASMLi(regs.getA2(), IRLiteral2Int((IRLiteral) node.getRhs())));
            } else {
                var rhsInst = (ASMStmt) node.getRhs().accept(this);
                InstList.appendInsts(rhsInst);
                var rhsDest = rhsInst.getDest();
                InstList.appendInsts(LoadAt(regs.getA2(), 4 * ((ASMVirtualReg) rhsDest).getOffset()));
                InstList.addInst(new ASMLi(regs.getA1(), IRLiteral2Int((IRLiteral) node.getLhs())));
            }
            switch (op) {
                case "add" -> {
                    InstList.addInst(new ASMArithR("add", regs.getA0(), regs.getA1(), regs.getA2()));
                }
                case "sub" -> {
                    InstList.addInst(new ASMArithR("sub", regs.getA0(), regs.getA1(), regs.getA2()));
                }
                case "mul" -> {
                    InstList.addInst(new ASMArithR("mul", regs.getA0(), regs.getA1(), regs.getA2()));
                }
                case "sdiv" -> {
                    InstList.addInst(new ASMArithR("div", regs.getA0(), regs.getA1(), regs.getA2()));
                }
                case "srem" -> {
                    InstList.addInst(new ASMArithR("rem", regs.getA0(), regs.getA1(), regs.getA2()));
                }
                case "shl" -> {
                    InstList.addInst(new ASMArithR("sll", regs.getA0(), regs.getA1(), regs.getA2()));
                }
                case "ashr" -> {
                    InstList.addInst(new ASMArithR("sra", regs.getA0(), regs.getA1(), regs.getA2()));
                }
                case "and" -> {
                    InstList.addInst(new ASMArithR("and", regs.getA0(), regs.getA1(), regs.getA2()));
                }
                case "or" -> {
                    InstList.addInst(new ASMArithR("or", regs.getA0(), regs.getA1(), regs.getA2()));
                }
                case "xor" -> {
                    InstList.addInst(new ASMArithR("xor", regs.getA0(), regs.getA1(), regs.getA2()));
                }
                default -> throw new ASMError("Unknown Binary operation");
            }
        }
        InstList.appendInsts(StoreAt(regs.getA0(), 4 * ((ASMVirtualReg) DestInst.getDest()).getOffset()));
        return InstList;
    }

    @Override
    public ASMNode visit(IRIcmp node) throws BaseError {
        var InstList = new ASMStmt();
        var DestInst = (ASMStmt) node.getDest().accept(this);
        var cond = node.getCond();
        if (ValidImm(node.getLhs()) && ValidImm(node.getRhs())) {
            var lhs = IRLiteral2Int((IRLiteral) node.getLhs());
            var rhs = IRLiteral2Int((IRLiteral) node.getRhs());
            switch (cond) {
                case "eq" -> {
                    InstList.addInst(new ASMLi(regs.getA0(), lhs == rhs ? 1 : 0));
                }
                case "ne" -> {
                    InstList.addInst(new ASMLi(regs.getA0(), lhs != rhs ? 1 : 0));
                }
                case "slt" -> {
                    InstList.addInst(new ASMLi(regs.getA0(), lhs < rhs ? 1 : 0));
                }
                case "sgt" -> {
                    InstList.addInst(new ASMLi(regs.getA0(), lhs > rhs ? 1 : 0));
                }
                case "sle" -> {
                    InstList.addInst(new ASMLi(regs.getA0(), lhs <= rhs ? 1 : 0));
                }
                case "sge" -> {
                    InstList.addInst(new ASMLi(regs.getA0(), lhs >= rhs ? 1 : 0));
                }
                default -> throw new ASMError("Unknown Binary operation");
            }
        } else if (ValidImm(node.getRhs())) {
            var rhs = IRLiteral2Int((IRLiteral) node.getRhs());
            if (node.getLhs() instanceof IRVariable) {
                var lhsInst = (ASMStmt) node.getLhs().accept(this);
                InstList.appendInsts(lhsInst);
                var lhsDest = lhsInst.getDest();
                InstList.appendInsts(LoadAt(regs.getA1(), 4 * ((ASMVirtualReg) lhsDest).getOffset()));
            } else {
                InstList.addInst(new ASMLi(regs.getA1(), IRLiteral2Int((IRLiteral) node.getLhs())));
            }
            switch (cond) {
                case "eq" -> {
                    InstList.addInst(new ASMArithI("xori", regs.getA0(), regs.getA1(), rhs));
                    InstList.addInst(new ASMUnarry("seqz", regs.getA0(), regs.getA0()));
                }
                case "ne" -> {
                    InstList.addInst(new ASMArithI("xori", regs.getA0(), regs.getA1(), rhs));
                    InstList.addInst(new ASMUnarry("snez", regs.getA0(), regs.getA0()));
                }
                case "slt" -> {
                    InstList.addInst(new ASMArithI("slti", regs.getA0(), regs.getA1(), rhs));
                }
                case "sgt" -> {
                    InstList.addInst(new ASMArithI("slti", regs.getA0(), regs.getA1(), rhs + 1));
                    InstList.addInst(new ASMUnarry("seqz", regs.getA0(), regs.getA0()));
                }
                case "sle" -> {
                    InstList.addInst(new ASMArithI("slti", regs.getA0(), regs.getA1(), rhs + 1));
                }
                case "sge" -> {
                    InstList.addInst(new ASMArithI("slti", regs.getA0(), regs.getA1(), rhs));
                    InstList.addInst(new ASMUnarry("seqz", regs.getA0(), regs.getA0()));
                }
                default -> throw new ASMError("Unknown Binary operation");
            }
        } else if (ValidImm(node.getLhs())) {
            var lhs = IRLiteral2Int((IRLiteral) node.getLhs());
            if (node.getRhs() instanceof IRVariable) {
                var rhsInst = (ASMStmt) node.getRhs().accept(this);
                InstList.appendInsts(rhsInst);
                var rhsDest = rhsInst.getDest();
                InstList.appendInsts(LoadAt(regs.getA2(), 4 * ((ASMVirtualReg) rhsDest).getOffset()));
            } else {
                InstList.addInst(new ASMLi(regs.getA2(), IRLiteral2Int((IRLiteral) node.getRhs())));
            }
            switch (cond) {
                case "eq" -> {
                    InstList.addInst(new ASMArithI("xori", regs.getA0(), regs.getA2(), lhs));
                    InstList.addInst(new ASMUnarry("seqz", regs.getA0(), regs.getA0()));
                }
                case "ne" -> {
                    InstList.addInst(new ASMArithI("xori", regs.getA0(), regs.getA2(), lhs));
                    InstList.addInst(new ASMUnarry("snez", regs.getA0(), regs.getA0()));
                }
                case "slt" -> {
                    InstList.addInst(new ASMArithI("slti", regs.getA0(), regs.getA2(), lhs + 1));
                    InstList.addInst(new ASMUnarry("seqz", regs.getA0(), regs.getA0()));
                }
                case "sgt" -> {
                    InstList.addInst(new ASMArithI("slti", regs.getA0(), regs.getA2(), lhs));
                }
                case "sle" -> {
                    InstList.addInst(new ASMArithI("slti", regs.getA0(), regs.getA2(), lhs));
                    InstList.addInst(new ASMUnarry("seqz", regs.getA0(), regs.getA0()));
                }
                case "sge" -> {
                    InstList.addInst(new ASMArithI("slti", regs.getA0(), regs.getA2(), lhs + 1));
                }
                default -> throw new ASMError("Unknown Binary operation");
            }
        } else {
            if ((node.getLhs() instanceof IRVariable) && (node.getRhs() instanceof IRVariable)) {
                var lhsInst = (ASMStmt) node.getLhs().accept(this);
                var rhsInst = (ASMStmt) node.getRhs().accept(this);
                InstList.appendInsts(lhsInst);
                InstList.appendInsts(rhsInst);
                var lhsDest = lhsInst.getDest();
                var rhsDest = rhsInst.getDest();
                InstList.appendInsts(LoadAt(regs.getA1(), 4 * ((ASMVirtualReg) lhsDest).getOffset()));
                InstList.appendInsts(LoadAt(regs.getA2(), 4 * ((ASMVirtualReg) rhsDest).getOffset()));
            } else if (node.getLhs() instanceof IRVariable) {
                var lhsInst = (ASMStmt) node.getLhs().accept(this);
                InstList.appendInsts(lhsInst);
                var lhsDest = lhsInst.getDest();
                InstList.appendInsts(LoadAt(regs.getA1(), 4 * ((ASMVirtualReg) lhsDest).getOffset()));
                InstList.addInst(new ASMLi(regs.getA2(), IRLiteral2Int((IRLiteral) node.getRhs())));
            } else {
                var rhsInst = (ASMStmt) node.getRhs().accept(this);
                InstList.appendInsts(rhsInst);
                var rhsDest = rhsInst.getDest();
                InstList.appendInsts(LoadAt(regs.getA2(), 4 * ((ASMVirtualReg) rhsDest).getOffset()));
                InstList.addInst(new ASMLi(regs.getA1(), IRLiteral2Int((IRLiteral) node.getLhs())));
            }
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
        }
        InstList.appendInsts(StoreAt(regs.getA0(), 4 * ((ASMVirtualReg) DestInst.getDest()).getOffset()));
        return InstList;
    }

    @Override
    public ASMNode visit(IRBranch node) throws BaseError {
        var InstList = new ASMStmt();
        if (node.isJump()) {
            InstList.addInst(new ASMJump(node.getTrueLabel().getLabel()));
        } else {
            if (node.getCond() instanceof IRVariable) {
                var condInst = (ASMStmt) node.getCond().accept(this);
                var condDest = condInst.getDest();
                InstList.appendInsts(condInst);
                InstList.appendInsts(LoadAt(regs.getA0(), 4 * ((ASMVirtualReg) condDest).getOffset()));
            } else {
                InstList.addInst(new ASMLi(regs.getA0(), IRLiteral2Int((IRLiteral) node.getCond())));
            }
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
        var ComputeInst = new ASMStmt();
        if (node.getFuncName().equals("__malloc_array") || node.getFuncName().equals("_malloc")) {
            if (node.getArgs().size() > 2) {
                throw new ASMError("malloc should have 1 or 2 args");
            }
            if (node.getFuncName().equals("__malloc_array")) {
                var arg1 = node.getArgs().get(0);
                if (arg1 instanceof IRVariable) {
                    var argInst = (ASMStmt) arg1.accept(this);
                    ComputeInst.appendInsts(argInst);
                    InstList.appendInsts(LoadAt(regs.getA0(), 4 * ((ASMVirtualReg) argInst.getDest()).getOffset()));
                } else {
                    InstList.addInst(new ASMLi(regs.getA0(), IRLiteral2Int((IRLiteral) arg1)));
                }
                ++argNum;
                var arg2 = node.getArgs().get(1);
                if (arg2 instanceof IRVariable) {
                    throw new ASMError("arg should be literal");
                } else {
                    var value = IRLiteral2Int((IRLiteral) arg2);
                    InstList.addInst(new ASMLi(regs.getA1(), value % 4 != 0 ? value + 4 - value % 4 : value));
                }
                ++argNum;
            } else {
                var arg1 = node.getArgs().get(0);
                if (arg1 instanceof IRVariable) {
                    throw new ASMError("arg should be literal");
                } else {
                    var value = IRLiteral2Int((IRLiteral) arg1);
                    InstList.addInst(new ASMLi(regs.getA0(), value % 4 != 0 ? value + 4 - value % 4 : value));
                }
                ++argNum;
            }
        } else {
            for (var arg : node.getArgs()) {
                if (argNum < 8) {
                    if (arg instanceof IRVariable) {
                        var argInst = (ASMStmt) arg.accept(this);
                        ComputeInst.appendInsts(argInst);
                        InstList.appendInsts(
                                LoadAt(getArgReg(argNum), 4 * ((ASMVirtualReg) argInst.getDest()).getOffset()));
                    } else {
                        InstList.addInst(new ASMLi(getArgReg(argNum), IRLiteral2Int((IRLiteral) arg)));
                    }
                } else {
                    ++offset;
                    if (arg instanceof IRVariable) {
                        var argInst = (ASMStmt) arg.accept(this);
                        ComputeInst.appendInsts(argInst);
                        InstList.appendInsts(LoadAt(regs.getT2(), 4 * ((ASMVirtualReg) argInst.getDest()).getOffset()));
                        InstList.appendInsts(StoreAt(regs.getT2(), -4 * offset));
                    } else {
                        InstList.addInst(new ASMLi(regs.getT2(), IRLiteral2Int((IRLiteral) arg)));
                        InstList.appendInsts(StoreAt(regs.getT2(), -4 * offset));
                    }
                }
                ++argNum;
            }
        }
        InstList.appendInsts(0, ComputeInst);
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
        }
        return InstList;
    }

    @Override
    public ASMNode visit(IRGetelementptr node) throws BaseError {
        var InstList = new ASMStmt();
        var DestInst = (ASMStmt) node.getDest().accept(this);
        InstList.appendInsts(DestInst);
        var DestDest = DestInst.getDest();
        var Ptr= node.getPtr();
        if(Ptr instanceof IRLiteral){
            if(!((IRLiteral) Ptr).getValue().equals("null")){
                throw new OPTError("Literal ptr should be null");
            }
            InstList.addInst(new ASMLi(regs.getA0(), 0));
        }
        else{
            var PtrInst = (ASMStmt) node.getPtr().accept(this);
            InstList.appendInsts(PtrInst);
            var PtrDest = PtrInst.getDest();
            InstList.appendInsts(LoadAt(regs.getA1(), 4 * ((ASMVirtualReg) PtrDest).getOffset()));
        }
        var IndexEntity = (IREntity) node.getInfolist().get(node.getInfolist().size() - 1);
        if (IndexEntity instanceof IRVariable) {
            var IndexInst = (ASMStmt) IndexEntity.accept(this);
            InstList.appendInsts(IndexInst);
            var IndexDest = IndexInst.getDest();
            InstList.appendInsts(LoadAt(regs.getA2(), 4 * ((ASMVirtualReg) IndexDest).getOffset()));
        } else {
            InstList.addInst(new ASMLi(regs.getA2(), IRLiteral2Int((IRLiteral) IndexEntity)));
        }
        InstList.addInst(new ASMArithI("slli", regs.getA2(), regs.getA2(), 2));
        InstList.addInst(new ASMArithR("add", regs.getA0(), regs.getA1(), regs.getA2()));
        InstList.appendInsts(StoreAt(regs.getA0(), 4 * ((ASMVirtualReg) DestDest).getOffset()));
        return InstList;
    }

    @Override
    public ASMNode visit(IRRet node) throws BaseError {
        var InstList = new ASMStmt();
        if (node.isVoidtype()) {
            InstList.addInst(new ASMRet());
        } else {
            if (node.getValue() instanceof IRVariable) {
                var DestInst = (ASMStmt) node.getValue().accept(this);
                InstList.appendInsts(LoadAt(regs.getA0(), 4 * ((ASMVirtualReg) DestInst.getDest()).getOffset()));
                InstList.addInst(new ASMRet());
            } else if (node.getValue() instanceof IRLiteral) {
                var Literal = IRLiteral2Int((IRLiteral) node.getValue());
                InstList.addInst(new ASMLi(regs.getA0(), Literal));
                InstList.addInst(new ASMRet());
            } else {
                throw new ASMError("Return value Error");
            }
        }
        return InstList;
    }

    @Override
    public ASMNode visit(IRLoad node) throws BaseError {
        var InstList = new ASMStmt();
        var DestInst = (ASMStmt) node.getDest().accept(this);
        var PtrInst = (ASMStmt) node.getPtr().accept(this);
        InstList.appendInsts(DestInst);
        InstList.appendInsts(PtrInst);
        var Dest = DestInst.getDest();
        var PtrDest = PtrInst.getDest();
        InstList.appendInsts(LoadAt(regs.getA0(), 4 * ((ASMVirtualReg) PtrDest).getOffset()));
        InstList.addInst(new ASMLoad("lw", regs.getA0(), 0, regs.getA0()));
        InstList.appendInsts(StoreAt(regs.getA0(), 4 * ((ASMVirtualReg) Dest).getOffset()));
        return InstList;
    }

    @Override
    public ASMNode visit(IRPhi node) throws BaseError {
        throw new OPTError("Phi should be directly translated");
    }

    @Override
    public ASMNode visit(IRStore node) throws BaseError {
        var InstList = new ASMStmt();
        var DestInst = (ASMStmt) node.getDest().accept(this);
        InstList.appendInsts(DestInst);
        var Dest = DestInst.getDest();
        if (node.getSrc() instanceof IRVariable) {
            var SrcInst = (ASMStmt) node.getSrc().accept(this);
            InstList.appendInsts(SrcInst);
            var SrcDest = SrcInst.getDest();
            InstList.appendInsts(LoadAt(regs.getA1(), 4 * ((ASMVirtualReg) SrcDest).getOffset()));
        } else {
            InstList.addInst(new ASMLi(regs.getA1(), IRLiteral2Int((IRLiteral) node.getSrc())));
        }
        InstList.appendInsts(LoadAt(regs.getA0(), 4 * ((ASMVirtualReg) Dest).getOffset()));
        InstList.addInst(new ASMStore("sw", regs.getA1(), 0, regs.getA0()));
        return InstList;
    }

    @Override
    public ASMNode visit(IREntity node) throws BaseError {
        throw new ASMError("Unknown entity type");
    }

    @Override
    public ASMNode visit(IRVariable node) throws BaseError {
        var InstList = new ASMStmt();
        var entity = (ASMVirtualReg) counter.name2reg.get(node.getValue());
        if (node.isGlobal()) {
            // if (entity == null) {
            entity = new ASMVirtualReg(counter);
            counter.name2reg.put(node.getValue(), entity);
            InstList.addInst(new ASMLoadLabel(regs.getA0(), node.getValue().substring(1)));
            InstList.appendInsts(StoreAt(regs.getA0(), 4 * entity.getOffset()));
            // }
        } else {
            if (entity == null) {
                entity = new ASMVirtualReg(counter);
                counter.name2reg.put(node.getValue(), entity);
            }
        }
        InstList.setDest(entity);
        return InstList;
    }

    @Override
    public ASMNode visit(IRLiteral node) throws BaseError {
        throw new ASMError("Literal should be eliminated");
        // return new ASMStmt();
    }
}
