package Compiler.Src.IR.Node;

import java.util.ArrayList;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Node.Def.IRFuncDef;
import Compiler.Src.IR.Node.Def.IRGlobalDef;
import Compiler.Src.Util.Error.BaseError;

public class IRRoot extends IRNode {
    private ArrayList<IRGlobalDef> defs;
    private ArrayList<IRFuncDef> funcs;
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
}
