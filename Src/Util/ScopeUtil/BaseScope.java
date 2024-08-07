package Compiler.Src.Util.ScopeUtil;

import java.util.TreeMap;

import Compile.Src.Util.Info.*;
import Compiler.Src.Util.Info.BaseInfo;
import Compiler.Src.Util.Info.VarInfo;
import Compiler.Src.Util.Error.*;

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

    @Override
    public boolean contains(String name) {
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

    @Override
    public boolean containsFuncs(String name) throws ScopeError
    {
        throw new ScopeError("no containsFuncs");
        return false;
    }

    @Override
    public boolean containsClasses(String name)throws ScopeError {
        throw new ScopeError("no containsClasses");
        return false;
    }
}