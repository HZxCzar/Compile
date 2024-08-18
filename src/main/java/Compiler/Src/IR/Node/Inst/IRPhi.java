package Compiler.Src.IR.Node.Inst;

import java.util.ArrayList;

import Compiler.Src.IR.Node.util.IRLabel;
import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Entity.*;
import Compiler.Src.IR.Type.IRType;
import Compiler.Src.Util.Error.BaseError;

@lombok.Getter
@lombok.Setter
public class IRPhi extends IRInst {
    private IRVariable dest;
    private IRType type;
    private ArrayList<IREntity> vals;
    private ArrayList<IRLabel> labels;
    public IRPhi(IRVariable dest, IRType type, ArrayList<IREntity> vals, ArrayList<IRLabel> labels) {
        this.dest = dest;
        this.type = type;
        this.vals = vals;
        this.labels = labels;
    }
    @Override
    public <T> T accept(IRVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }

    @Override
  public String toString() {
    var str = dest.getValue() + " = phi " + type.toString() + " ";
    for (int i = 0; i < vals.size(); i++) {
      str += "[ " + vals.get(i).toString() + ", " + labels.get(i).toString() + " ]";
      if (i != vals.size() - 1) {
        str += ", ";
      }
    }
    return str;
  }
}
