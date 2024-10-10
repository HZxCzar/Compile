package Compiler.Src.OPT;

import Compiler.Src.IR.Node.IRRoot;

public class IROptimize {
    public void visit(IRRoot root)
    {
        new Inlining().visit(root);
        new CFGBuilder().visit(root);
        new Mem2Reg().visit(root);
        new SCCP().visit(root);
        new ADCE().visit(root);
        // new CSE().visit(root);
        return;
    }
}
