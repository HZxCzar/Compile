package Compiler.Src.IR.Node.Inst;

import java.util.ArrayList;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Entity.IREntity;
import Compiler.Src.IR.Entity.IRVariable;
import Compiler.Src.IR.Node.IRNode;
import Compiler.Src.IR.Node.util.IRLabel;
import Compiler.Src.Util.Error.BaseError;
import Compiler.Src.Util.Error.IRError;
import Compiler.Src.Util.Error.OPTError;

public class IRInst extends IRNode implements Comparable<IRInst> {
    public IRInst() {
    }

    @Override
    public <T> T accept(IRVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }

    public ArrayList<IRVariable> getUses() {
        throw new IRError("IRInst.getUses() is not implemented");
    }

    public void replaceUse(IRVariable oldVar, IREntity newVar) {
        throw new IRError("IRInst.replaceUse() is not implemented");
    }

    @Override
    public int compareTo(IRInst o) {
        IRVariable lhsDest, rhsDest;
        String lhsname, rhsname;
        if(this instanceof IRLabel && o instanceof IRLabel)
        {
            return ((IRLabel) this).getLabel().compareTo(((IRLabel) o).getLabel());
        }
        if (this instanceof IRCall) {
            lhsDest = ((IRCall) this).getDest();
            lhsname = ((IRCall) this).getFuncName();
        } else if (this instanceof IRGetelementptr) {
            lhsDest = ((IRGetelementptr) this).getDest();
            lhsname = ((IRGetelementptr) this).getType();
        } else if (this instanceof IRArith) {
            lhsDest = ((IRArith) this).getDest();
            lhsname = ((IRArith) this).getOp();
        } else if (this instanceof IRAlloca) {
            lhsDest = ((IRAlloca) this).getDest();
            lhsname = ((IRAlloca) this).getType().getTypeName();
        } else if (this instanceof IRIcmp) {
            lhsDest = ((IRIcmp) this).getDest();
            lhsname = null;
        } else if (this instanceof IRLoad) {
            lhsDest = ((IRLoad) this).getDest();
            lhsname = null;
        } else if (this instanceof IRPhi) {
            lhsDest = ((IRPhi) this).getDest();
            lhsname = null;
        } 
        else {
            throw new OPTError("IRInst.compareTo() is not implemented");
        }

        if (o instanceof IRCall) {
            rhsDest = ((IRCall) o).getDest();
            rhsname = ((IRCall) o).getFuncName();
        } else if (o instanceof IRGetelementptr) {
            rhsDest = ((IRGetelementptr) o).getDest();
            rhsname = ((IRGetelementptr) o).getType();
        } else if (o instanceof IRArith) {
            rhsDest = ((IRArith) o).getDest();
            rhsname = ((IRArith) o).getOp();
        } else if (o instanceof IRAlloca) {
            rhsDest = ((IRAlloca) o).getDest();
            rhsname = ((IRAlloca) o).getType().getTypeName();
        } else if (o instanceof IRIcmp) {
            rhsDest = ((IRIcmp) o).getDest();
            rhsname = null;
        } else if (o instanceof IRLoad) {
            rhsDest = ((IRLoad) o).getDest();
            rhsname = null;
        } else if (o instanceof IRPhi) {
            rhsDest = ((IRPhi) o).getDest();
            rhsname = null;
        } else {
            throw new OPTError("IRInst.compareTo() is not implemented");
        }

        var compare = lhsDest.compareTo(rhsDest);
        if (compare != 0) {
            return compare;
        }

        if (lhsname == null || rhsname == null) {
            if(lhsname == null && rhsname == null){
                return 0;
            }
            return lhsname == null ? -1 : 1;
        }
        return lhsname.compareTo(rhsname);
    }
}
