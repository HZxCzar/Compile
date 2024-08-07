package Compile.Src.Util.ScopeUtil;

import Compile.Src.Util.Info.*;

import java.util.TreeMap;

// @lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class FuncScope extends BaseScope {
    private boolean Exit;
    
    public FuncScope(BaseScope parent,BaseInfo info) {
        super(parent, info);
        this.Exit = false;
    }

    @Override
    public void declare(BaseInfo var) {
        if(var instanceof VarInfo)
        {
            vars.put(var.getName(), (VarInfo)var);
        }
        else if(var instanceof FuncInfo)
        {
            funcs.put(var.getName(), (FuncInfo)var);
        }
        else{
            throw new Error("ClassScope.declare(BaseInfo) should not be called"+var.getPos());
        }
    }
}
