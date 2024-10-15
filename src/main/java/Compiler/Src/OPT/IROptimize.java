package Compiler.Src.OPT;

import Compiler.Src.IR.Node.IRRoot;

public class IROptimize {
    public void visit(IRRoot root)
    {
        new CFGBuilder().visit(root);
        new Mem2Reg().visit(root);
        new Tail().visit(root);
        new Inlining().visit(root);
        new ADCE().visit(root);
        new Tail().visit(root);
        new SCCP().visit(root);
        new CSE().visit(root);
        new RovB().visit(root);
        // new LivenessAnalysis().visit(root);
        new LiveAnalysis().visit(root);
        return;
    }
}
