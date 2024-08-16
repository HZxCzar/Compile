package Compiler.Src.IR.Node.Stmt;

import java.util.ArrayList;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Entity.IREntity;
import Compiler.Src.IR.Node.Inst.*;
import Compiler.Src.IR.Node.util.IRLabel;
import Compiler.Src.Util.Error.BaseError;


@lombok.Getter
@lombok.Setter
public class IRIf extends IRStmt  {
    public static int count = 0;
    private IRLabel condLabel, bodyLabel, elseLabel;

    public IRIf(int num, IRStmt cond, IRStmt body, IRStmt elseBody) {
        var condLabel = new IRLabel("if." + String.valueOf(num) + ".cond");
        var bodyLabel = new IRLabel("if." + String.valueOf(num) + ".body");
        var elseLabel = new IRLabel("if." + String.valueOf(num) + ".else");
        var endLabel = new IRLabel("if." + String.valueOf(num) + ".end");
        addInsts(new IRBranch(condLabel));
        cond.addFront(condLabel);
        addBlockInsts(cond);
        this.condLabel = cond.getLastLabel();
        addInsts(
            new IRBranch(cond.getDest(), body == null ? endLabel : bodyLabel,
                elseBody == null ? endLabel: elseLabel));
        if (body != null) {
          body.addFront(bodyLabel);
          this.bodyLabel = body.getLastLabel();
          addBlockInsts(body);
          addInsts(new IRBranch(endLabel));
        }
        if (elseBody != null) {
          elseBody.addFront(elseLabel);
          this.elseLabel = elseBody.getLastLabel();
          addBlockInsts(elseBody);
          addInsts(new IRBranch(endLabel));
        }
        addInsts(endLabel);
      }

    public static int addCount() {
        return ++count;
      }
    @Override
    public <T> T accept(IRVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }
}
