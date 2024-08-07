package Compile.Src.Semantic;

import Compile.Src.AST.ASTVisitor;
import Compile.Src.AST.Node.ASTRoot;
import Compile.Src.AST.Node.ASTNode;
import Compile.Src.AST.Node.DefNode.*;
import Compile.Src.Util.Error.SBCError;
import Compile.Src.Util.ScopeUtil.*;

public class ScopeControl {
  protected GlobalScope globalScope;
  protected BaseScope currentScope;

  public void enterScope(BaseScope scope) {
    if (globalScope == null) {
      globalScope = (GlobalScope) scope;
    }
    currentScope = scope;
  }

  public void exitScope() {
    currentScope = currentScope.getParent();
  }
}
