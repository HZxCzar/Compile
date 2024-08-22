package Compiler.Src.ASM;

import Compiler.Src.ASM.Node.ASMNode;
import Compiler.Src.ASM.Util.ASMControl;
import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Entity.IREntity;
import Compiler.Src.IR.Entity.IRLiteral;
import Compiler.Src.IR.Entity.IRVariable;
import Compiler.Src.IR.Node.IRNode;
import Compiler.Src.IR.Node.IRRoot;
import Compiler.Src.IR.Node.Def.IRFuncDef;
import Compiler.Src.IR.Node.Def.IRGlobalDef;
import Compiler.Src.IR.Node.Inst.IRAlloca;
import Compiler.Src.IR.Node.Inst.IRArith;
import Compiler.Src.IR.Node.Inst.IRBranch;
import Compiler.Src.IR.Node.Inst.IRCall;
import Compiler.Src.IR.Node.Inst.IRGetelementptr;
import Compiler.Src.IR.Node.Inst.IRLoad;
import Compiler.Src.IR.Node.Inst.IRPhi;
import Compiler.Src.IR.Node.Inst.IRRet;
import Compiler.Src.IR.Node.Inst.IRStore;
import Compiler.Src.IR.Node.Stmt.IRBlock;
import Compiler.Src.Util.Error.BaseError;

public class ASMBuilder extends ASMControl implements IRVisitor<ASMNode> {
    public ASMNode visit(IRNode node) throws BaseError {
        return null;
    }

    public ASMNode visit(IRRoot node) throws BaseError {
        return null;
    }

    public ASMNode visit(IRFuncDef node) throws BaseError {
        return null;
    }

    public ASMNode visit(IRBlock node) throws BaseError

    {
        return null;
    }

    public ASMNode visit(IRGlobalDef node) throws BaseError {
        return null;
    }

    public ASMNode visit(IRAlloca node) throws BaseError {
        return null;
    }

    public ASMNode visit(IRArith node) throws BaseError {
        return null;
    }

    public ASMNode visit(IRBranch node) throws BaseError {
        return null;
    }

    public ASMNode visit(IRCall node) throws BaseError {
        return null;
    }

    public ASMNode visit(IRGetelementptr node) throws BaseError {
        return null;
    }

    public ASMNode visit(IRRet node) throws BaseError {
        return null;
    }

    public ASMNode visit(IRLoad node) throws BaseError {
        return null;
    }

    public ASMNode visit(IRPhi node) throws BaseError {
        return null;
    }

    public ASMNode visit(IRStore node) throws BaseError {
        return null;
    }

    public ASMNode visit(IREntity node) throws BaseError {
        return null;
    }

    public ASMNode visit(IRVariable node) throws BaseError {
        return null;
    }

    public ASMNode visit(IRLiteral node) throws BaseError {
        return null;
    }
}
