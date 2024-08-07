package Compiler.Src.Util.Info;

import java.util.ArrayList;
import java.util.TreeMap;

import Compiler.Src.AST.Node.Def.ASTVarDef;
import Compiler.Src.AST.Node.Def.ASTFuncDef;
@lombok.Getter
@lombok.Setter
public class ClassInfo extends BaseInfo {
    private FuncInfo construtor;
    public TreeMap<String,VarInfo> vars;
    public TreeMap<Stirng,FuncInfo> funcs;
    public ClassInfo(String name,FuncInfo constructor,ArrayList<ASTVarDef> vars,ArrayList<ASTFuncDef> funcs)
    {
        super(name);
        this.constructor=constructor;
        this.vars=new TreeMap<String,VarInfo>();
        this.funcs=new TreeMap<Stirng,FuncInfo>();
        for(var v:vars)
        {
            this.vars.put(v.getName(),(VarInfo)v.getInfo());
        }
        for(var func:funcs)
        {
            this.funcs.put(func.getName(),(FuncInfo)func.getInfo());
        }
    }
    @Override
    public String str()
    {
        String ret="Class "+getName()+"{\n";
        for(var vname:vars.keySet())
        {
            ret+=vars.get(vname).str()+"\n";
        }
        for(var funcname:funcs.keySet())
        {
            ret+=funcs.get(funcname).str()+"\n";
        }
        ret+="}\n";
        return ret;
    }
}
