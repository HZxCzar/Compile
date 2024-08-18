package Compiler.Src.IR.Node.Def;

import java.util.ArrayList;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Entity.IRVariable;
import Compiler.Src.IR.Node.Stmt.IRBlock;
import Compiler.Src.IR.Type.IRType;
import Compiler.Src.Util.Error.BaseError;

@lombok.Getter
@lombok.Setter
public class IRFuncDef extends IRDef {
    private String name;
    private ArrayList<IRVariable> params;
    private IRType returnType;
    private ArrayList<IRBlock> blockstmts;
    private boolean isBuiltIn = false;

    public IRFuncDef(String name, ArrayList<IRVariable> params, IRType returnType, ArrayList<IRBlock> blockstmts) {
        this.name = name;
        this.params = params;
        this.returnType = returnType;
        this.blockstmts = blockstmts;
        this.isBuiltIn = false;
    }

    public IRFuncDef(String name, IRType returnType, ArrayList<IRType> params) {
        this.name = name;
        this.returnType = returnType;
        this.isBuiltIn = true;
        this.params = new ArrayList<IRVariable>();
        for (int i = 0; i < params.size(); i++) {
            this.params.add(new IRVariable(params.get(i), "null"));
        }
    }

    @Override
    public <T> T accept(IRVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }

    @Override
    public String toString() {
        if (isBuiltIn) {
            String str = "declare " + returnType.toString() + " @" + name + "(";
            for (int i = 0; i < params.size(); i++) {
                str += params.get(i).getType().toString();
                if (i != params.size() - 1)
                    str += ", ";
            }
            str += ")\n";
            return str;
        } else {
            String str = "define " + returnType.toString() + " @" + name + "(";
            for (int i = 0; i < params.size(); i++) {
                str += params.get(i).toString();
                if (i != params.size() - 1)
                    str += ", ";
            }
            str += ") {\n";
            for (IRBlock blockstmt : blockstmts) {
                str += blockstmt.toString();
            }
            str += "}\n";
            return str;
        }
    }
}
