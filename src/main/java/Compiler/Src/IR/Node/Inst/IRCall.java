package Compiler.Src.IR.Node.Inst;

import java.util.ArrayList;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Entity.IREntity;
import Compiler.Src.IR.Entity.IRVariable;
import Compiler.Src.IR.Type.IRType;
import Compiler.Src.Util.Error.BaseError;
import Compiler.Src.Util.ScopeUtil.GlobalScope;

@lombok.Getter
@lombok.Setter
public class IRCall extends IRInst {
    private IRVariable dest;
    private IRType type;
    private String funcName;
    private ArrayList<IREntity> args;

    public IRCall(String funcName, ArrayList<IREntity> args) {
        this.type = GlobalScope.irVoidType;
        this.dest = null;
        this.funcName = funcName;
        this.args = args;
    }

    public IRCall(IRVariable dest, IRType type, String funcName, ArrayList<IREntity> args) {
        this.dest = dest;
        this.type = type;
        this.funcName = funcName;
        this.args = args;
    }

    @Override
    public <T> T accept(IRVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        String str = (dest == null ? "" : dest.getValue() + " = ") + "call " + type.toString();
        str += " @" + funcName + "(";
        for (int i = 0; i < args.size(); i++) {
            str += args.get(i).getType().toString() + " " + args.get(i).getValue();
            if (i != args.size() - 1) {
                str += ", ";
            }
        }
        str += ")";
        return str;
    }
}
