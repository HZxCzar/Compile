package Compiler.Src.IR.Node.Inst;

import java.util.ArrayList;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Entity.IREntity;
import Compiler.Src.IR.Entity.IRVariable;
import Compiler.Src.IR.Node.IRNode;
import Compiler.Src.Util.Error.BaseError;
import Compiler.Src.Util.Error.IRError;

public class IRInst extends IRNode {
    public IRInst() {
    }
    @Override
    public <T> T accept(IRVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }

    public ArrayList<IRVariable> getUses() {
        throw new IRError("IRInst.getUses() is not implemented");
    }

    public void replaceUse(IRVariable oldVar, IREntity newVar) {
        throw new IRError("IRInst.replaceUse() is not implemented");
    }
}
