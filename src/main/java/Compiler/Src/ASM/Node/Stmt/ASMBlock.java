package Compiler.Src.ASM.Node.Stmt;

import Compiler.Src.ASM.ASMVisitor;
import Compiler.Src.ASM.Node.Util.ASMLabel;

@lombok.Getter
@lombok.Setter
public class ASMBlock extends ASMStmt {
    private ASMLabel label;
    private ASMStmt returnInst;

    public ASMBlock(ASMLabel label) {
        this.label = label;
        this.returnInst = null;
    }

    @Override
    public <T> T accept(ASMVisitor<T> visitor) {
        return visitor.visit(this);
    }
}