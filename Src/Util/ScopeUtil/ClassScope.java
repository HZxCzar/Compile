package Compiler.Src.Util.ScopeUtil;

import Compile.Src.Util.Info.*;
import Compiler.Src.Util.Info.BaseInfo;
import Compiler.Src.Util.Info.FuncInfo;
import Compiler.Src.Util.Info.VarInfo;

import java.util.TreeMap;

// @lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class ClassScope extends BaseScope {
    private TreeMap<String, FuncInfo> funcs;

    public ClassScope(BaseScope parent, BaseInfo info) {
        super(parent, info);
        this.funcs = new TreeMap<String, FuncInfo>();
    }

    @Override
    public void declare(BaseInfo var) {
        if (var instanceof VarInfo) {
            vars.put(var.getName(), (VarInfo) var);
        } else if (var instanceof FuncInfo) {
            funcs.put(var.getName(), (FuncInfo) var);
        } else {
            throw new Error("ClassScope.declare(BaseInfo) should not be called" + var.getPos());
        }
    }

    @Override
    public boolean contains(String name) {
        if (vars.containsKey(name)) {
            return true;
        } else if (funcs.containsKey(name)) {
            return true;
        }
        return false;
    }

    @Override
    public boolean containsFuncs(String name)
    {
        return funcs.containsKey(name);
    }
}
