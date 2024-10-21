package Compiler.Src.OPT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashMap;
import java.util.HashMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Stack;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.HashMap;

import org.antlr.v4.runtime.misc.Pair;

import Compiler.Src.IR.IRVisitor;
import Compiler.Src.IR.Entity.IREntity;
import Compiler.Src.IR.Entity.IRLiteral;
import Compiler.Src.IR.Entity.IRVariable;
import Compiler.Src.IR.Node.IRNode;
import Compiler.Src.IR.Node.IRRoot;
import Compiler.Src.IR.Node.Def.IRFuncDef;
import Compiler.Src.IR.Node.Def.IRGlobalDef;
import Compiler.Src.IR.Node.Def.IRStrDef;
import Compiler.Src.IR.Node.Inst.*;
import Compiler.Src.IR.Node.Stmt.IRBlock;
import Compiler.Src.IR.Node.util.IRLabel;
import Compiler.Src.IR.Type.IRStructType;
import Compiler.Src.IR.Type.IRType;
import Compiler.Src.IR.Util.InstCounter;
import Compiler.Src.Util.Error.BaseError;
import Compiler.Src.Util.Error.OPTError;
import Compiler.Src.Util.ScopeUtil.GlobalScope;

public class Other {
    public void visit(IRRoot root) {
        new CFGBuilder().visit(root);
        root.getFuncs().forEach(func -> replaceLoad(func));
    }

    public void replaceLoad(IRFuncDef func) {
        HashMap<IRVariable, IRVariable> rep = new HashMap<>();
        for (var block : func.getBlockstmts()) {
            HashMap<IRVariable, IRVariable> var2base = new HashMap<>();
            for (int i = 0; i < block.getInsts().size(); ++i) {
                var inst = block.getInsts().get(i);
                for (var var : inst.getUses()) {
                    if (rep.containsKey(var)) {
                        inst.replaceUse(var, rep.get(var));
                    }
                }
                if (inst instanceof IRLoad) {
                    var load = (IRLoad) inst;
                    if (!var2base.containsKey(load.getPtr())) {
                        var2base.put(load.getPtr(), null);
                    }
                    if (var2base.get(load.getPtr()) == null) {
                        var2base.put(load.getPtr(), load.getDef());
                    } else {
                        rep.put(load.getDest(), var2base.get(load.getPtr()));
                        block.getInsts().remove(i);
                        --i;
                    }
                } else if (inst instanceof IRStore) {
                    var2base.put(((IRStore) inst).getDest(), null);
                }
            }
        }
    }
}
