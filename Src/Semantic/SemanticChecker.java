package Compiler.Src.Semantic;

import Compiler.Src.AST.ASTVisitor;
import Compiler.Src.AST.Node.ASTRoot;
import Compiler.Src.AST.Node.ASTNode;
import Compiler.Src.AST.Node.DefNode.*;
import Compiler.Src.AST.Node.ExprNode.*;
import Compiler.Src.AST.Node.ExprNode.ExprUnitNode.*;
import Compiler.Src.AST.Node.StatementNode.*;
import Compiler.Src.Util.Error.*;
import Compiler.Src.Util.Info.*;
import Compiler.Src.Util.ScopeUtil.*;
import Compiler.Src.Semantic.*;

public class SemanticChecker extends ScopeControl implements ASTVisitor<SMCError> {
    public SMCError visit(ASTNode node) {
        return new SMCError("SMC should not visit ASTNode\n");
    }

    public SMCError visit(ASTRoot node) {
        node.addScope(null);
        enterScope((GlobalScope) node.getScope());
        var msg = new SMCError();
        for (var def : node.getDefNodes()) {
            msg.append(def.accept(this));
        }
        exitScope();
        return msg;
    }

    public SMCError visit(ASTClassDef node){
        node.addScope(currentScope);
        enterScope((ClassScope)node.getScope());
        var msg=new SMCError();
        msg.append(node.getConstructor().accept(this));
        for(var def:node.getFuncs())
        return msg;
    }
}
