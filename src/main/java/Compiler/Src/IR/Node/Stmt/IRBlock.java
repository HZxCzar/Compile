package Compiler.Src.IR.Node.Stmt;

import java.util.ArrayList;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Node.Inst.IRInst;
import Compiler.Src.IR.Node.util.IRLabel;
import Compiler.Src.Util.Error.BaseError;

@lombok.Getter
@lombok.Setter
public class IRBlock extends IRStmt {
    private IRLabel labelName;

    private IRInst returnInst;

    public IRBlock(IRLabel labelName) {
        this.labelName = labelName;
        this.returnInst = null;
    }

    @Override
    public <T> T accept(IRVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        String str = labelName.toString() + ":\n";
        for (var inst : getInsts()) {
            str += "  " + inst.toString() + "\n";
        }
        str += "  " +returnInst.toString()+"\n";
        return str;
    }
}
