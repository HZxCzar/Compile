package Compiler.Src.ASM.Node;

import java.util.ArrayList;
import Compiler.Src.ASM.Node.Global.*;

@lombok.Getter
@lombok.Setter
public class ASMRoot extends ASMNode {
    private ArrayList<ASMVarDef> vars;
    private ArrayList<ASMStrDef> strs;
    private ArrayList<ASMFuncDef> funcs;

    public ASMRoot() {
        vars = new ArrayList<ASMVarDef>();
        strs = new ArrayList<ASMStrDef>();
        funcs = new ArrayList<ASMFuncDef>();
    }

    @Override
    public String toString() {
        return "";
    }
}
