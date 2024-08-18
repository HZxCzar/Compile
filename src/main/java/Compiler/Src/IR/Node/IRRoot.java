package Compiler.Src.IR.Node;

import java.util.ArrayList;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Node.Def.IRFuncDef;
import Compiler.Src.IR.Node.Def.IRGlobalDef;
import Compiler.Src.Util.BasicType;
import Compiler.Src.Util.Error.BaseError;

public class IRRoot extends IRNode {
    private ArrayList<IRGlobalDef> defs;
    private ArrayList<IRFuncDef> funcs;

    public IRRoot() {
        defs = new ArrayList<IRGlobalDef>();
        funcs = new ArrayList<IRFuncDef>();
    }
    @Override
    public <T> T accept(IRVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }
    public void addDef(IRGlobalDef def) {
        defs.add(def);
      }
    
      public void addFunc(IRFuncDef func) {
        funcs.add(func);
      }

    @Override
    public String toString() {
        String str = "";
        for (var def : defs) {
            str += def.toString() + "\n";
        }
        str+="\n";
        for (var func : BasicType.irBuiltInFuncs) {
            str += func.toString() + "\n";
        }
        str+="\n";
        for (var func : funcs) {
            str += func.toString() + "\n\n";
        }
        return str;
    }
}
