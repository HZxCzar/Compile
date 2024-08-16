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

    public static int count = 0;

    public IRBlock(IRLabel labelName) {
    this.labelName = labelName;
    this.returnInst = null;
  }

    public static int addCount() {
        return ++count;
    }

    @Override
    public <T> T accept(IRVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }
}
