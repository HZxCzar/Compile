package Compiler.Src.ASM.Node.Inst;

import Compiler.Src.ASM.ASMVisitor;
import Compiler.Src.ASM.Node.ASMNode;
import Compiler.Src.Util.Error.ASMError;

public abstract class ASMInst extends ASMNode {
    @Override
    public String toString() {
        throw new ASMError("Instruction should not be printed");
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
