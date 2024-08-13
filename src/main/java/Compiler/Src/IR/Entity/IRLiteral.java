package Compiler.Src.IR.Entity;

import Compiler.Src.IR.Type.IRType;

@lombok.Getter
@lombok.Setter
public class IRLiteral extends IREntity {
    public IRLiteral(IRType type,String value) {
        super(type, value);
    }
}
