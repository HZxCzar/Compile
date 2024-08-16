package Compiler.Src.IR.Node.Stmt;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Node.Inst.*;
import Compiler.Src.IR.Node.util.IRLabel;
import Compiler.Src.Util.Error.BaseError;

@lombok.Getter
@lombok.Setter
public class IRLoop extends IRStmt {
    public static int count = 0;

    public IRLoop(int num, IRStmt init, IRStmt cond, IRStmt update, IRStmt body) {
        var condLabel = new IRLabel("loop." + String.valueOf(num) + ".condLabel");
        var updateLabel = new IRLabel("loop." + String.valueOf(num) + ".updateLabel");
        var bodyLabel = new IRLabel("loop." + String.valueOf(num) + ".bodyLabel");
        var endLabel = new IRLabel("loop." + String.valueOf(num) + ".endLabel");
        if (init != null) {
            addBlockInsts(init);
        }
        addInsts(new IRBranch(condLabel));
        addInsts(condLabel);
        if (cond != null) {
            addBlockInsts(cond);
            addInsts(new IRBranch(cond.getDest(),bodyLabel,endLabel));
        } else {
            addInsts(new IRBranch(bodyLabel));
        }
        addInsts(bodyLabel);
        addBlockInsts(body);
        addInsts(new IRBranch(updateLabel));
        addInsts(updateLabel);
        if (update != null) {
            addBlockInsts(update);
        }
        addInsts(new IRBranch(condLabel));
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
