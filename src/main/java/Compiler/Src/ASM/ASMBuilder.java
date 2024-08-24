package Compiler.Src.ASM;

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
                root.getVars().add(vars);
            }
        }
        for (var func : node.getFuncs()) {
            root.getFuncs().add((ASMFuncDef) func.accept(this));
        }
        return root;
    }

    @Override
    public ASMNode visit(IRFuncDef node) throws BaseError {
        var funcDef = new ASMFuncDef(node.getName(), node.getParams().size());
        counter = new ASMCounter();
        var paramCount = 0;
        var initStmt = new ASMBlock(new ASMLabel(node.getName()));
        var offsetStack = (4 * (node.getParams().size() - 8) + 15) / 16 * 16;
        for (var param : node.getParams()) {
            var paramInst = (ASMStmt) param.accept(this);
            var paramDest = paramInst.getDest();
            if (paramCount < 8) {
                initStmt.addInst(
                        new ASMStore("sw", getArgReg(paramCount), 4 * ((ASMStackReg) paramDest).getOffset(),
                                regs.getSp()));
            } else {
                initStmt.addInst(new ASMLoad("lw", regs.getT0(), offsetStack - 4 * (paramCount - 7), regs.getT1()));
                initStmt.addInst(
                        new ASMStore("sw", regs.getT0(), 4 * ((ASMStackReg) paramDest).getOffset(), regs.getSp()));
            }
            paramCount++;
        }

        funcDef.addBlock(initStmt);
        for (var block : node.getBlockstmts()) {
            funcDef.addBlock((ASMBlock) block.accept(this));
        }
        funcDef.Formolize(this);
        return funcDef;
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
        var block = new ASMBlock(new ASMLabel(node.getLabelName().getLabel()));
        for (var stmt : node.getInsts()) {
            block.appendInsts((ASMStmt) stmt.accept(this));
        }
        var returnInst = (ASMStmt) node.getReturnInst().accept(this);
        block.setReturnInst(returnInst);
        return block;
    }

    @Override
    public ASMNode visit(IRGlobalDef node) throws BaseError {
        if (node.getVars().getType() instanceof IRStructType) {
            return new ASMVarDef(".zero", ((IRStructType) node.getVars().getType()).getSize());
        }
        return new ASMVarDef(node.getVars().getValue().substring(1), 0);
    }

    @Override
    public ASMNode visit(IRAlloca node) throws BaseError {
        var instList = new ASMStmt();
        var DestInst = (ASMStmt) node.getDest().accept(this);
        var allocaDest = DestInst.getDest();
        var Dest = new ASMStackReg(counter);
        if (allocaDest instanceof ASMPhysicalReg) {
            throw new ASMError("not supose to use this in Naive ASM");
        }
        instList.addInst(new ASMArithI("addi", regs.getA0(), regs.getSp(), 4 * ((ASMStackReg) Dest).getOffset()));
        instList.addInst(new ASMStore("sw", regs.getA0(), 4 * ((ASMStackReg) allocaDest).getOffset(), regs.getSp()));
        return instList;
    }

    @Override
    public ASMNode visit(IRArith node) throws BaseError {
        var instList = new ASMStmt();
        var destInst = (ASMStmt) node.getDest().accept(this);
        var lhsInst = (ASMStmt) node.getLhs().accept(this);
        var rhsInst = (ASMStmt) node.getRhs().accept(this);
        instList.appendInsts(destInst);
        instList.appendInsts(lhsInst);
        instList.appendInsts(rhsInst);
        var dest = destInst.getDest();
        var lhsDest = lhsInst.getDest();
        var rhsDest = rhsInst.getDest();
        instList.addInst(new ASMLoad("lw", regs.getA1(), 4 * ((ASMStackReg) lhsDest).getOffset(), regs.getSp()));
        instList.addInst(new ASMLoad("lw", regs.getA2(), 4 * ((ASMStackReg) rhsDest).getOffset(), regs.getSp()));
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
        instList.addInst(new ASMStore("sw", regs.getA0(), 4 * ((ASMStackReg) dest).getOffset(), regs.getSp()));
        return instList;
    }

    @Override
    public ASMNode visit(IRIcmp node) throws BaseError {
        var InstList = new ASMStmt();
        var destInst = (ASMStmt) node.getDest().accept(this);
        var lhsInst = (ASMStmt) node.getLhs().accept(this);
        var rhsInst = (ASMStmt) node.getRhs().accept(this);
        InstList.appendInsts(destInst);
        InstList.appendInsts(lhsInst);
        InstList.appendInsts(rhsInst);
        var dest = destInst.getDest();
        var lhsDest = lhsInst.getDest();
        var rhsDest = rhsInst.getDest();
        InstList.addInst(new ASMLoad("lw", regs.getA1(), 4 * ((ASMStackReg) lhsDest).getOffset(), regs.getSp()));
        InstList.addInst(new ASMLoad("lw", regs.getA2(), 4 * ((ASMStackReg) rhsDest).getOffset(), regs.getSp()));
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
        InstList.addInst(new ASMStore("sw", regs.getA0(), 4 * ((ASMStackReg) dest).getOffset(), regs.getSp()));
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
            InstList.addInst(new ASMLoad("lw", regs.getA0(), 4 * ((ASMStackReg) condDest).getOffset(), regs.getSp()));
            InstList.addInst(new ASMBezq(regs.getA0(), node.getFalseLabel().getLabel()));
            InstList.addInst(new ASMJump(node.getTrueLabel().getLabel()));
        }
        return InstList;
    }

    @Override
    public ASMNode visit(IRCall node) throws BaseError {
        var InstList = new ASMStmt();
        int argNum = 0;
        int offset = 0;
        for (var arg : node.getArgs()) {
            var argInst = (ASMStmt) arg.accept(this);
            InstList.appendInsts(argInst);
            var argDest = argInst.getDest();
            if (argNum < 8) {
                InstList.addInst(
                        new ASMLoad("lw", getArgReg(argNum), 4 * ((ASMStackReg) argDest).getOffset(), regs.getSp()));
            } else {
                InstList.addInst(
                        new ASMLoad("lw", regs.getT0(), 4 * ((ASMStackReg) argDest).getOffset(), regs.getSp()));
                InstList.addInst(new ASMStore("sw", regs.getT0(), -4 * offset, regs.getSp()));
                ++offset;
            }
            ++argNum;
        }
        var offsetStack = (4 * offset + 15) / 16 * 16;
        if (offset != 0) {
            InstList.addInst(new ASMArithI("addi", regs.getSp(), regs.getSp(), -offsetStack));
        }
        InstList.addInst(new ASMCall(node.getFuncName()));
        if (offset != 0) {
            InstList.addInst(new ASMArithI("addi", regs.getSp(), regs.getSp(), offsetStack));
        }
        if (node.getDest() != null) {
            var destInst = (ASMStmt) node.getDest().accept(this);
            var dest = destInst.getDest();
            InstList.addInst(new ASMStore("sw", regs.getA0(), 4 * ((ASMStackReg) dest).getOffset(), regs.getSp()));
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
        InstList.addInst(new ASMLoad("lw", regs.getA1(), 4 * ((ASMStackReg) ptrDest).getOffset(), regs.getSp()));
        InstList.addInst(new ASMLoad("lw", regs.getA2(), 4 * ((ASMStackReg) indexDest).getOffset(), regs.getSp()));
        InstList.addInst(new ASMArithI("slli", regs.getA2(), regs.getA2(), 2));
        InstList.addInst(new ASMArithR("add", regs.getA0(), regs.getA1(), regs.getA2()));
        InstList.addInst(new ASMStore("sw", regs.getA0(), 4 * ((ASMStackReg) destDest).getOffset(), regs.getSp()));
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
            InstList.addInst(new ASMLoad("lw", regs.getA0(), 4 * ((ASMStackReg) valueDest).getOffset(), regs.getSp()));
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
        InstList.addInst(new ASMLoad("lw", regs.getA0(), 4 * ((ASMStackReg) ptrDest).getOffset(), regs.getSp()));
        InstList.addInst(new ASMLoad("lw", regs.getA0(), 0, regs.getA0()));
        InstList.addInst(new ASMStore("sw", regs.getA0(), 4 * ((ASMStackReg) dest).getOffset(), regs.getSp()));
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
        InstList.addInst(new ASMLoad("lw", regs.getA1(), 4 * ((ASMStackReg) src).getOffset(), regs.getSp()));
        InstList.addInst(new ASMLoad("lw", regs.getA0(), 4 * ((ASMStackReg) dest).getOffset(), regs.getSp()));
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
            var destReg = new ASMStackReg(counter);
            instList.addInst(new ASMLoadLabel(regs.getA0(), node.getValue().substring(1)));
            instList.addInst(new ASMStore("sw", regs.getA0(), 4 * destReg.getOffset(), regs.getSp()));
            instList.setDest(destReg);
        } else {
            var existReg = counter.name2reg.get(node.getValue());
            if (existReg == null) {
                var destReg = new ASMStackReg(counter);
                counter.name2reg.put(node.getValue(), destReg);
                instList.setDest(destReg);
            } else {
                if (existReg instanceof ASMStackReg) {
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
        var destReg = new ASMStackReg(counter);
        // var dest=new
        // ASMImm(node.getValue().equals("null")?0:Integer.parseInt(node.getValue()));
        InstList.addInst(
                new ASMLi(regs.getA0(), node.getValue().equals("null") ? 0 : Integer.parseInt(node.getValue())));
        InstList.addInst(new ASMStore("sw", regs.getA0(), 4 * destReg.getOffset(), regs.getSp()));
        InstList.setDest(destReg);
        return InstList;
    }
}
