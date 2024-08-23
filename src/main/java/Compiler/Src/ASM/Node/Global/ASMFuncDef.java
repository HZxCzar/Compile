package Compiler.Src.ASM.Node.Global;

import java.sql.Blob;
import java.util.ArrayList;

import Compiler.Src.ASM.ASMVisitor;
import Compiler.Src.ASM.Node.ASMNode;
import Compiler.Src.ASM.Node.Stmt.*;
import Compiler.Src.ASM.Node.Util.ASMLabel;
import Compiler.Src.ASM.Util.ASMControl;
import Compiler.Src.ASM.Util.ASMCounter;
import Compiler.Src.ASM.Node.Inst.Arithmetic.*;
import Compiler.Src.ASM.Node.Inst.Control.ASMJump;
import Compiler.Src.ASM.Node.Inst.Presudo.ASMRet;

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

    public void Formolize(ASMControl control) {
        var initBlock = blocks.get(0);
        initBlock.addInst(0, new ASMArithI("addi", control.getRegs().getSp(), control.getRegs().getSp(),
                -4 * (control.getCounter().allocaCount)));
        var jumpStmt = new ASMStmt();
        var jumpInst = new ASMJump(getBlocks().get(1).getLabel().getLabel());
        jumpStmt.addInst(jumpInst);
        // initBlock.addInst(jumpInst);
        initBlock.setReturnInst(jumpStmt);
        for (int i = 1; i < blocks.size(); i++) {
            var block = blocks.get(i);
            String Labelname_old = block.getLabel().getLabel();
            String Labelname_new = name + "." + Labelname_old;
            block.setLabel(new ASMLabel(Labelname_new));
            var size = block.getReturnInst().getInsts().size();
            if (block.getReturnInst().getInsts().get(size - 1) instanceof ASMRet) {
                block.getReturnInst().addInst(size - 1,
                        new ASMArithI("addi", control.getRegs().getSp(), control.getRegs().getSp(),
                                4 * (control.getCounter().allocaCount)));
            }
        }
    }

    public void addBlock(ASMBlock block) {
        blocks.add(block);
    }

    @Override
    public String toString() {
        // String str = " .global" + name + "\n";
        String str = "";
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
