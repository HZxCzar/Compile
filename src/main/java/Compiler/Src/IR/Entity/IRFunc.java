package Compiler.Src.IR.Entity;

import Compiler.Src.IR.Type.IRType;

@lombok.Getter
@lombok.Setter
public class IRFunc extends IREntity{
    private IRVariable caller;
    private IRType returnType;
    public IRFunc(IRVariable caller, IRType returnType) {
        super(null, null);
        this.caller = caller;
        this.returnType = returnType;
    }
}
