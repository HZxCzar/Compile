package Compiler.Src.IR.Entity;

import Compiler.Src.IR.Type.IRType;

@lombok.Getter
@lombok.Setter
public class IRFunc extends IREntity {
    private IRVariable caller;
    private IRType returnType;

    public IRFunc(String value, IRVariable caller, IRType returnType) {
        super(new IRType("function"), value);
        this.caller = caller;
        this.returnType = returnType;
    }

    @Override
    public String toString(){
        return "@" + getValue();
    }
}
