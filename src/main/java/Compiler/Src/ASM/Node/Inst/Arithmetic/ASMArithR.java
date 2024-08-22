package Compiler.Src.ASM.Node.Inst.Arithmetic;

import Compiler.Src.ASM.Entity.ASMReg;
import Compiler.Src.ASM.Node.Inst.ASMInst;

@lombok.Getter
@lombok.Setter
public class ASMArithR extends ASMInst {
    private String op;
    private ASMReg dest, lhs, rhs;
}
