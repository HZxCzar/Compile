package Compiler.Src.IR.Node.Def;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Entity.IRVariable;
import Compiler.Src.Util.Error.BaseError;


@lombok.Getter
@lombok.Setter
public class IRGlobalDef extends IRDef {
    private IRVariable vars;
    public IRGlobalDef(IRVariable vars) {
        this.vars = vars;
    }
    @Override
    public <T> T accept(IRVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }
}
