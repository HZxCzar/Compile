package Compiler.Src.IR.Node.Def;

import java.util.ArrayList;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Entity.IRVariable;
import Compiler.Src.IR.Node.Stmt.IRBlock;
import Compiler.Src.IR.Type.IRType;
import Compiler.Src.Util.Error.BaseError;

@lombok.Getter
@lombok.Setter
public class IRFuncDef extends IRDef  {
    private String name;
    private ArrayList<IRVariable> params;
    private IRType returnType;
    private ArrayList<IRBlock> blockstmts;

    public IRFuncDef(String name, ArrayList<IRVariable> params, IRType returnType, ArrayList<IRBlock> blockstmts) {
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
