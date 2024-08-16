package Compiler.Src.Util.ScopeUtil;

import java.util.TreeMap;

import Compiler.Src.Util.Info.*;
import Compiler.Src.Util.Error.*;

// @lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class BaseScope {
    protected BaseScope parent;
    protected BaseInfo info;
    protected int scopedep;
    protected TreeMap<String, VarInfo> vars;
    protected TreeMap<String, VarInfo> IRvars;

    public BaseScope(BaseScope parent, BaseInfo info) {
        this.parent = parent;
        this.info = info;
        this.vars = new TreeMap<String, VarInfo>();
        this.IRvars = new TreeMap<String, VarInfo>();
        if(parent!=null){
            scopedep=parent.scopedep+1;
        }
        else{
            scopedep=0;
        }
    }

    public boolean contains(String name) {
        return vars.containsKey(name);
    }

    public boolean IRcontains(String name) {
        return IRvars.containsKey(name);
    }

    public void IRdeclare(String name) {
        if (vars.containsKey(name)) {
            IRvars.put(name, vars.get(name));
        } else {
            throw new Error("BaseScope.declare(IRInfo) should not be called");
        }
    }

    // public void arrayInitDeclare(String name, VarInfo var) {
    //     IRvars.put(name, var);
    // }

    public void declare(BaseInfo var) {
        if (var instanceof VarInfo) {
            vars.put(var.getName(), (VarInfo) var);
        } else {
            throw new Error("BaseScope.declare(BaseInfo) should not be called");
        }
    }

    public FuncInfo containsFuncs(String name) throws ScopeError {
        throw new ScopeError("no containsFuncs");
        // return null;
    }

    public ClassInfo containsClasses(String name) throws ScopeError {
        throw new ScopeError("no containsClasses");
        // return null;
    }

    public VarInfo containsVars(String name) throws ScopeError {
        if (vars.containsKey(name)) {
            return vars.get(name);
        }
        return null;
    }

    public BaseInfo BackSearch(String name) {
        if (vars.containsKey(name)) {
            return vars.get(name);
        } else if (this.parent != null) {
            return parent.BackSearch(name);
        }
        return null;
    }

    public BaseInfo IRBackSearch(String name) {
        if (IRvars.containsKey(name)) {
            return IRvars.get(name);
        } else if (this.parent != null) {
            return parent.IRBackSearch(name);
        }
        return null;
    }

    public BaseScope IRBackSearchScope(String name) {
        if (IRvars.containsKey(name)) {
            return this;
        } else if (this.parent != null) {
            return parent.IRBackSearchScope(name);
        }
        return null;
    }
}