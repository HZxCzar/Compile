package Compiler.Src.IR.Node.Inst;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Entity.IREntity;
import Compiler.Src.IR.Entity.IRVariable;
import Compiler.Src.IR.Type.IRType;
import Compiler.Src.Util.Error.BaseError;
// import Compiler.Src.Util.ScopeUtil.GlobalScope;

@lombok.Getter
@lombok.Setter
public class IRArith extends IRInst {
    private String op;
    private IRType type;
    private IREntity lhs, rhs;
    private IRVariable dest;

    public IRArith(IRVariable dest, String op,IRType type, IREntity lhs, IREntity rhs) {
        this.dest = dest;
        this.type = type;
        this.lhs = lhs;
        this.rhs = rhs;
        this.op = op;
    }

    @Override
    public <T> T accept(IRVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }
}
