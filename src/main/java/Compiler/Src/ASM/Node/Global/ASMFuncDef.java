package Compiler.Src.ASM.Node.Global;

import java.util.ArrayList;

import Compiler.Src.ASM.ASMVisitor;
import Compiler.Src.ASM.Node.ASMNode;
import Compiler.Src.ASM.Node.Stmt.*;
import Compiler.Src.ASM.Node.Util.ASMLabel;
import Compiler.Src.ASM.Util.ASMControl;
import Compiler.Src.Util.Error.ASMError;
import Compiler.Src.ASM.Node.Inst.Arithmetic.*;
import Compiler.Src.ASM.Node.Inst.Control.ASMJump;
import Compiler.Src.ASM.Node.Inst.Memory.ASMLoad;
import Compiler.Src.ASM.Node.Inst.Memory.ASMStore;
import Compiler.Src.ASM.Node.Inst.Presudo.ASMBezq;
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
        var total = ((4 * (paramCount + control.getCounter().allocaCount)) + 15) / 16 * 16;
        initBlock.addInst(0, new ASMArithI("addi", control.getRegs().getSp(), control.getRegs().getSp(), -total));
        initBlock.addInst(1, new ASMStore("sw", control.getRegs().getRa(), total - 4, control.getRegs().getSp()));
        initBlock.addInst(2, new ASMStore("sw", control.getRegs().getS0(), total - 8, control.getRegs().getSp()));
        initBlock.addInst(3, new ASMArithI("addi", control.getRegs().getT1(), control.getRegs().getSp(), total));
        var jumpStmt = new ASMStmt();
        var jumpInst = new ASMJump(name + "." + getBlocks().get(1).getLabel().getLabel());
        jumpStmt.addInst(jumpInst);
        // initBlock.addInst(jumpInst);
        initBlock.setReturnInst(jumpStmt);
        for (int i = 1; i < blocks.size(); i++) {
            var block = blocks.get(i);
            String Labelname_old = block.getLabel().getLabel();
            String Labelname_new = name + "." + Labelname_old;
            block.setLabel(new ASMLabel(Labelname_new));
            var size = block.getReturnInst().getInsts().size();
            for (int j = 0; j < size; j++) {
                var inst = block.getReturnInst().getInsts().get(j);
                if (inst instanceof ASMJump) {
                    ((ASMJump) inst).addFuncName(name);
                } 
                if (inst instanceof ASMBezq) {
                    ((ASMBezq) inst).addFuncName(name);
                }
                if (inst instanceof ASMRet) {
                    if (j != size - 1) {
                        throw new ASMError("ret should be the last instruction in a block");
                    } else {
                        block.getReturnInst().addInst(j,
                                new ASMLoad("lw", control.getRegs().getRa(), total - 4, control.getRegs().getSp()));
                        block.getReturnInst().addInst(j + 1,
                                new ASMLoad("lw", control.getRegs().getS0(), total - 8, control.getRegs().getSp()));
                        block.getReturnInst().addInst(j + 2,
                                new ASMArithI("addi", control.getRegs().getSp(), control.getRegs().getSp(),
                                        total));
                    }
                    break;
                } 
            }
            // var lastInst = block.getReturnInst().getInsts().get(size - 1);
            // if (lastInst instanceof ASMJump) {
            // ((ASMJump) lastInst).addFuncName(name);
            // }
            // if (lastInst instanceof ASMRet) {
            // block.getReturnInst().addInst(size - 1,
            // new ASMArithI("addi", control.getRegs().getSp(), control.getRegs().getSp(),
            // 4 * (control.getCounter().allocaCount)));
            // }
        }
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
