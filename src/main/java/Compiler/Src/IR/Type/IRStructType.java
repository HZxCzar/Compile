package Compiler.Src.IR.Type;

import java.util.ArrayList;

public class IRStructType extends IRType {
    public ArrayList<IRType> members;
    public IRStructType(String typeName, ArrayList<IRType> members) {
        super(typeName);
        this.members = members;
    }
}
