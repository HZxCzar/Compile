package Compiler.Src.IR.Entity;

import Compiler.Src.IR.Type.IRType;

@lombok.Setter
@lombok.Getter
public class IREntity{
    private IRType type;
    private String value;
    public IREntity(IRType type, String value) {
        this.type = type;
        this.value = value;
    }
}
