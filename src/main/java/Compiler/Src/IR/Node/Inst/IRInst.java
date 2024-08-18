package Compiler.Src.IR.Node.Inst;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Node.IRNode;
import Compiler.Src.Util.Error.BaseError;

public class IRInst extends IRNode {
    public IRInst() {
    }
    @Override
    public <T> T accept(IRVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }
}
