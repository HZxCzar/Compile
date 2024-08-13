package Compiler.Src.IR.Entity;

import Compiler.Src.IR.Type.IRType;

@lombok.Getter
@lombok.Setter
public class IRVariable extends IREntity {
    public IRVariable(IRType type, String value) {
        super(type, value);
    }
}
