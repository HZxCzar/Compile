package Compile.Src.Semantic;

import Compile.Src.AST.ASTVisitor;
import Compile.Src.AST.Node.ASTRoot;
import Compile.Src.AST.Node.ASTNode;
import Compile.Src.AST.Node.DefNode.*;
import Compile.Src.Util.Error.SBCError;
import Compile.Src.Util.Info.FuncInfo;
import Compile.Src.Util.ScopeUtil.*;
import Compile.Src.Semantic.*;

public class SymbolCollector extends ScopeControl implements ASTVisitor<SBCError> {
    public SBCError visit(ASTNode node) throws SBCError {
        throw ("SymbolCollector.visit(ASTNode) should not be called"+node.getPos());
    }

    public SBCError visit(ASTRoot node) throws SBCError {
        node.addScope(null);
        enterScope(node.getScope());
        msg = new SBCError();
        for(ASTDef def: node.getDefNodes()){
            if(def instanceof ASTClassDef || def instanceof ASTFuncDef){
                if(currentScope.containsVars(def.getName())){
                    return new SBCError( def.getName()+" is redefined" + def.getPos());
                }else{
                    currentScope.declare(def.getInfo());
                }
            }
        }
        for(ASTDef def: node.getDefNodes()){
            if(def instanceof ASTClassDef || def instanceof ASTFuncDef){
                msg.append(def.accept(this));
            }
        }
        if(node.getScope().get("main", "func") == null){
            return new SBCError("Main function is not defined" + node.getPos());
        }
        return msg;
    }

    public SBCError visit(ASTClassDef node)throws SBCError{
        node.addScope(currentScope);
        enterScope((ClassScope)node.getScope());
        msg = new SBCError();
        for(ASTFuncDef def:node.getFuncs())
        {
            if(currentScope.containsFuncs(def.getName())){
                return new SBCError(def.getName()+" is redefined" + def.getPos());
            }
            else{
                currentScope.declare(def.getInfo());
            }
        }
        for(var def:node.getFuncs()){
            msg.append(def.accept(this));
        }
        return msg;
    }

    public SBCError visit(ASTFuncDef node)throws SBCError{
        node.addScope(currentScope);
        enterScope((FuncScope)node.getScope());
        if(node.getName()=="main")
        {
            if(node.getParams().size()>0)
            {
                return new SBCError("Main function can not have args\n");
            }
            else if(((FuncInfo)node.getInfo()).getFunctype().equals(null))
            {

            }
        }
    }
}
