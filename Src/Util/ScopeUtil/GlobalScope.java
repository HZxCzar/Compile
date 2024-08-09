package Compiler.Src.Util.ScopeUtil;

import Compiler.Src.Util.Info.*;
import Compiler.Src.Util.*;

import java.util.TreeMap;

// @lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class GlobalScope extends BaseScope implements BasicType {
    private TreeMap<String, FuncInfo> funcs;
    private TreeMap<String, ClassInfo> classes;

    public GlobalScope(BaseScope parent, BaseInfo info) {
        super(parent, info);
        this.funcs = new TreeMap<String, FuncInfo>();
        this.classes = new TreeMap<String, ClassInfo>();
        for(FuncInfo func:BasicType)
        {
            this.funcs.put(func.getName(), func);
        }
        for(ClassInfo cls:BasicType)
        {
            this.classes.put(cls.getName(), cls);
        }
    }

    @Override
    public void declare(BaseInfo var) {
        if (var instanceof VarInfo) {
            vars.put(var.getName(), (VarInfo) var);
        } else if (var instanceof FuncInfo) {
            funcs.put(var.getName(), (FuncInfo) var);
        } else if (var instanceof ClassInfo) {
            classes.put(var.getName(), (ClassInfo) var);
        } else {
            throw new Error("GlobalScope.declare(BaseInfo) should not be called" + var.getPos());
        }
    }

    @Override
    public boolean contains(String name) {
        if (vars.containsKey(name)) {
            return true;
        } else if (funcs.containsKey(name)) {
            return true;
        } else if (classes.containsKey(name)) {
            return true;
        }
        return false;
    }

    @Override
    public FuncInfo containsFuncs(String name) {
        if(funcs.containsKey(name))
        {
            return funcs.get(name);
        }
        else{
            return null;
        }
    }

    @Override
    public ClassInfo containsClasses(String name) {
        if(classes.containsKey(name))
        {
            return classes.get(name);
        }
        else{
            return null;
        };
    }

    @Override
    public BaseInfo BackSearch(String name)
    {
        if(vars.containsKey(name))
        {
            return vars.get(name);
        }
        else if(funcs.containsKey(name))
        {
            return funcs.get(name);
        }
        else if(classes.containsKey(name))
        {
            return classes.get(name);
        }
        else if(this.parent!=null)
        {
            return parent.BackSearch(name);
        }
        return null;
    }
}
