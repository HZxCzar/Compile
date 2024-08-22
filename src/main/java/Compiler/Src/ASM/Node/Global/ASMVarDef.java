package Compiler.Src.ASM.Node.Global;

import Compiler.Src.ASM.Node.ASMNode;

@lombok.Getter
@lombok.Setter
public class ASMVarDef extends ASMNode {
    private String name;
    private int value;

    public ASMVarDef(String name, int value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return "";
    }

}
