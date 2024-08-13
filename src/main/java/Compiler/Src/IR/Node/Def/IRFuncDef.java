package Compiler.Src.IR.Node.Def;

import java.util.ArrayList;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Node.Stmt.IRBlock;
import Compiler.Src.IR.Type.IRType;
import Compiler.Src.Util.Error.BaseError;

@lombok.Value
@lombok.EqualsAndHashCode(callSuper=true)
public class IRFuncDef extends IRDef  {
    private String name;
    private ArrayList<IRType> params;
    private IRType returnType;
    private IRBlock blockstmts;

    public IRFuncDef(String name, ArrayList<IRType> params, IRType returnType, IRBlock blockstmts) {
        this.name = name;
        this.params = params;
        this.returnType = returnType;
        this.blockstmts = blockstmts;
    }
    
    @Override
    public <T> T accept(IRVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }
}
