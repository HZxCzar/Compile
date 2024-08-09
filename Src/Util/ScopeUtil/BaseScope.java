package Compiler.Src.Util.ScopeUtil;

import java.util.TreeMap;

import Compile.Src.Util.Info.*;
import Compiler.Src.Util.Info.BaseInfo;
import Compiler.Src.Util.Info.ClassInfo;
import Compiler.Src.Util.Info.FuncInfo;
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
    public FuncInfo containsFuncs(String name) throws ScopeError
    {
        throw new ScopeError("no containsFuncs");
        return null;
    }

    @Override
    public ClassInfo containsClasses(String name)throws ScopeError {
        throw new ScopeError("no containsClasses");
        return null;
    }

    @Override
    public BaseInfo BackSearch(String name)
    {
        if(vars.containsKey(name))
        {
            return vars.get(name);
        }
        else if(this.parent!=null)
        {
            return parent.BackSearch(name);
        }
        return null;
    }
}