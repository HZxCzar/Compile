package Compiler.Src.IR.Node.Inst;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Entity.IREntity;
import Compiler.Src.IR.Type.IRType;
import Compiler.Src.Util.Error.BaseError;
import Compiler.Src.Util.ScopeUtil.GlobalScope;

@lombok.Getter
@lombok.Setter
public class IRRet extends IRInst {
    private boolean voidtype;
    private IRType type;
    private IREntity value;

    public IRRet(IREntity value) {
        this.voidtype = false;
        this.type = value.getType();
        this.value = value;
    }

    public IRRet() {
        this.voidtype = true;
        this.type = GlobalScope.irVoidType;
        this.value = null;
    }

    @Override
    public <T> T accept(IRVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        if(voidtype) return "ret void";
        return "ret " + type.toString() + " " + value.getValue();
    }
}
