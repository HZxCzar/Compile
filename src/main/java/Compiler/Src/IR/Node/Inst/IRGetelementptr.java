package Compiler.Src.IR.Node.Inst;

import java.util.ArrayList;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Entity.IREntity;
import Compiler.Src.IR.Entity.IRVariable;
import Compiler.Src.Util.Error.BaseError;

@lombok.Getter
@lombok.Setter
public class IRGetelementptr extends IRInst {
  private String type;
  private IRVariable dest;
  private IREntity ptr;
  private ArrayList<IREntity> infolist;

  // private ArrayList<IRVariable> indexlist;
  public IRGetelementptr(IRVariable dest, String type, IREntity ptr, ArrayList<IREntity> info) {
    this.type = type;
    this.dest = dest;
    this.ptr = ptr;
    this.infolist = info;
  }

  @Override
  public <T> T accept(IRVisitor<T> visitor) throws BaseError {
    return visitor.visit(this);
  }

  @Override
  public String toString() {
    String str= dest.getValue() + " = getelementptr " + type.toString() + ", " + ptr.toString() + ", ";
    for(int i=0;i<infolist.size();i++){
      str += infolist.get(i).getType().toString() + " " + infolist.get(i).getValue();
      if(i != infolist.size()-1){
        str += ", ";
      }
    }
    return str;
  }

  @Override
    public ArrayList<IRVariable> getUses() {
        ArrayList<IRVariable> res = new ArrayList<>();
        if (ptr instanceof IRVariable) {
            res.add((IRVariable) ptr);
        }
        for(var info: infolist){
          if(info instanceof IRVariable){
            res.add((IRVariable) info);
          }
        }
        return res;
    }

    @Override
    public void replaceUse(IRVariable oldVar, IREntity newVar) {
        if(ptr.equals(oldVar)){
          ptr = newVar;
        }
        for(int i=0;i<infolist.size();i++){
          if(infolist.get(i).equals(oldVar)){
            infolist.set(i, newVar);
          }
        }
    }
}
