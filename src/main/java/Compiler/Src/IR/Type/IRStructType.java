package Compiler.Src.IR.Type;

import java.util.ArrayList;

public class IRStructType extends IRType {
    public ArrayList<IRType> members;
    public IRStructType(String typeName, ArrayList<IRType> members) {
        super(typeName);
        this.members = members;
    }
    @Override
  public String toString() {
    String str="{ ";
    for (int i = 0; i < members.size(); i++) {
      str += members.get(i).toString();
      if (i != members.size() - 1)
        str += ", ";
    }
    str += " }";
    return str;
  }
}
