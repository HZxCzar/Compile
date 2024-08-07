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
    public boolean containsFuncs(String name) {
        return funcs.containsKey(name);
    }

    @Override
    public boolean containsClasses(String name) {
        return classes.containsKey(name);
    }
}
