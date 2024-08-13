package Compiler.Src.IR.Node.Stmt;

import java.util.ArrayList;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Entity.IREntity;
import Compiler.Src.IR.Node.Inst.IRInst;
import Compiler.Src.IR.Node.util.IRLabel;
import Compiler.Src.Util.Error.BaseError;


@lombok.Getter
@lombok.Setter
public class IRIf extends IRStmt  {
    public static int count = 0;
    private IRLabel condLabel, bodyLabel, elseLabel;

    public IRIf(ArrayList<IRInst> insts, IREntity dest) {
        super(insts, dest);
    }

    public static int addCount() {
        return ++count;
      }
    @Override
    public <T> T accept(IRVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }
}
