package Compiler.Src.ASM.Node.Global;

import java.util.ArrayList;

import Compiler.Src.ASM.Node.ASMNode;
import Compiler.Src.ASM.Node.Stmt.*;

@lombok.Getter
@lombok.Setter
public class ASMFuncDef extends ASMNode {
    private String name;
    private int paramCount;
    private ArrayList<ASMBlock> blocks;

    public ASMFuncDef(String name, int paramCount) {
        this.name = name;
        this.paramCount = paramCount;
        blocks = new ArrayList<ASMBlock>();
    }

    @Override
    public String toString() {
        return "";
    }
}
