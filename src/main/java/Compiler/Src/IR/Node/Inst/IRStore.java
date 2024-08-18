package Compiler.Src.IR.Node.Inst;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Entity.IREntity;
import Compiler.Src.IR.Entity.IRVariable;
import Compiler.Src.IR.Type.IRType;
import Compiler.Src.Util.Error.BaseError;
// import Compiler.Src.Util.ScopeUtil.GlobalScope;

@lombok.Getter
@lombok.Setter
public class IRStore extends IRInst {
    private IRVariable dest;
    private IREntity src;
    public IRStore(IRVariable dest, IREntity src) {
    this.dest = dest;
    this.src = src;
  }
    @Override
    public <T> T accept(IRVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }

    @Override
  public String toString() {
    return "store " + src.toString() + ", " + dest.toString();
  }
}
