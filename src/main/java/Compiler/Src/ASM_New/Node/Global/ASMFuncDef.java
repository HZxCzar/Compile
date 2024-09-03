package Compiler.Src.ASM_New.Node.Global;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import Compiler.Src.ASM_New.ASMVisitor;
import Compiler.Src.ASM_New.Node.ASMNode;
import Compiler.Src.ASM_New.Node.Stmt.*;
import Compiler.Src.ASM_New.Node.Util.ASMLabel;
import Compiler.Src.ASM_New.Util.ASMControl;
import Compiler.Src.ASM_New.Util.ASMCounter;
import Compiler.Src.Util.Error.ASMError;
import Compiler.Src.ASM_New.Node.Inst.Arithmetic.*;
import Compiler.Src.ASM_New.Node.Inst.Control.ASMJump;
import Compiler.Src.ASM_New.Node.Inst.Memory.ASMLoad;
import Compiler.Src.ASM_New.Node.Inst.Memory.ASMStore;
import Compiler.Src.ASM_New.Node.Inst.Presudo.ASMBezq;
import Compiler.Src.ASM_New.Node.Inst.Presudo.ASMLi;
import Compiler.Src.ASM_New.Node.Inst.Presudo.ASMRet;

@lombok.Getter
@lombok.Setter
public class ASMFuncDef extends ASMNode {
    private String name;
    private int paramCount;
    private ArrayList<ASMBlock> blocks;
    private ArrayList<ASMBlock> order2Block;

    public int StackSize;

    public ASMFuncDef(String name, int paramCount) {
        this.name = name;
        this.paramCount = paramCount;
        this.blocks = new ArrayList<ASMBlock>();
        this.order2Block = new ArrayList<ASMBlock>();
        this.StackSize = 1;
    }

    public void addBlock(ASMBlock block) {
        blocks.add(block);
    }

    @Override
    public String toString() {
        // String str = " .global" + name + "\n";
        String str = ".globl " + name + "\n";
        for (var block : blocks) {
            str += block.toString() + "\n";
        }
        return str;
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
