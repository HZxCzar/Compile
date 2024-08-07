package Compile.Src.Util.ScopeUtil;

import java.util.TreeMap;

import Compile.Src.Util.Info.*;

// @lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class BaseScope {
    protected BaseScope parent;
    protected BaseInfo info;
    protected TreeMap<String, VarInfo> vars;

    public BaseScope(BaseScope parent, BaseInfo info) {
        this.parent = parent;
        this.info = info;
        this.vars = new TreeMap<String, VarInfo>();
    }

    public boolean containsVars(String name) {
        return vars.containsKey(name);
    }

    public void declare(BaseInfo var) {
        if(var instanceof VarInfo)
        {
            vars.put(var.getName(), var);
        }
        else{
            throw("BaseScope.declare(BaseInfo) should not be called"+var.getPos());
        }
    }
}