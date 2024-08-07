package Compile.Src.Util.ScopeUtil;

import Compile.Src.Util.Info.*;

import java.util.TreeMap;

// @lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class GlobalScope extends BaseScope {
    private TreeMap<String, FuncInfo> funcs;
    private TreeMap<String, ClassInfo> classes;

    public GlobalScope(BaseScope parent,BaseInfo info) {
        super(parent, info);
        this.funcs = new TreeMap<String, FuncInfo>();
        this.classes = new TreeMap<String, ClassInfo>();
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
        else if(var instanceof ClassInfo){
            classes.put(var.getName(), (ClassInfo)var);
        }
        else{
            throw new Error("GlobalScope.declare(BaseInfo) should not be called"+var.getPos());
        }
    }

    public boolean containsFuncs(String name) {
        return funcs.containsKey(name);
    }

    public boolean containsClasses(String name) {
        return classes.containsKey(name);
    }
}
