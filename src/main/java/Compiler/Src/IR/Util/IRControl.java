package Compiler.Src.IR.Util;

import Compiler.Src.Util.ScopeUtil.BaseScope;
import Compiler.Src.Util.ScopeUtil.GlobalScope;
import Compiler.Src.AST.Node.ASTNode;
import Compiler.Src.AST.Node.StatementNode.ASTIfstatement;
import Compiler.Src.AST.Node.Util.ASTScopedNode;
import Compiler.Src.Util.Error.*;

public class IRControl {
    protected GlobalScope globalScope;
    protected BaseScope currentScope;
    protected IRCounter counter;

    public IRControl() {
        this.counter = new IRCounter();
    }

    public void enterASTNode(ASTNode node) {
        if (!(node instanceof ASTScopedNode)) {
            return;
        }
        var scope = ((ASTScopedNode) node).getScope();
        if (scope == null) {
            return;
        }
        currentScope = scope;
        if (globalScope == null) {
            if (!(currentScope instanceof GlobalScope)) {
                throw new IRError("Global scope not found");
            }
            globalScope = (GlobalScope) scope;
        }
    }

    public void enterASTIfNode(ASTIfstatement node, String kind) {
        if (kind.equals("then")) {
            currentScope = node.getIfScope();
        } else if (kind.equals("else")) {
            currentScope = node.getElseScope();
        } else {
            throw new IRError("Invalid if stmt node name");
        }
    }

    public void exitASTNode(ASTNode node) {
        if (node instanceof ASTScopedNode) {
            var scope = ((ASTScopedNode) node).getScope();
            if (scope != null) {
                currentScope = scope.getParent();
            }
        }
    }

    public void exitASTIfNode(ASTIfstatement node, String kind) {
        if (kind.equals("if")) {
            var scope = node.getIfScope();
            if (scope != null) {
                currentScope = scope.getParent();
            } else {
                throw new IRError("If scope not found");
            }
        } else if (kind.equals("else")) {
            var scope = node.getElseScope();
            if (scope != null) {
                currentScope = scope.getParent();
            }
        } else {
            throw new IRError("Invalid if stmt node name");
        }
    }
}
