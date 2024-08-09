package Compiler.Src.Semantic;

import Compiler.Src.AST.ASTVisitor;
import Compiler.Src.AST.Node.ASTRoot;
import Compiler.Src.AST.Node.ASTNode;
import Compiler.Src.AST.Node.DefNode.*;
import Compiler.Src.Util.Error.SBCError;
import Compiler.Src.Util.ScopeUtil.*;
import Compiler.Src.Util.Info.*;

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

  public boolean ValidFuncType(TypeInfo type)
  {
    if(type.equals(GlobalScope.intType) || type.equals(GlobalScope.boolType) || type.equals(GlobalScope.stringType) || type.equals(GlobalScope.voidType) || globalScope.containsClasses(type.getName())!=null)
    {
      return true;
    }
    return false;
  }
  public boolean ValidVarType(TypeInfo type)
  {
    if(type.equals(GlobalScope.intType) || type.equals(GlobalScope.boolType) || type.equals(GlobalScope.stringType) || globalScope.containsClasses(type.getName())!=null)
    {
      return true;
    }
    return false;
  }

  public BaseScope whichClass(BaseScope cur)
  {
    BaseScope tmp=cur;
    while(tmp!=null)
    {
      if(tmp instanceof ClassScope)
      {
        return tmp;
      }
      tmp=tmp.getParent();
    }
    return null;
  }

  public BaseScope whichFunc(BaseScope cur)
  {
    BaseScope tmp=cur;
    while(tmp!=null)
    {
      if(tmp instanceof FuncScope)
      {
        return tmp;
      }
      tmp=tmp.getParent();
    }
    return null;
  }

  public BaseScope whichLoop(BaseScope cur)
  {
    BaseScope tmp=cur;
    while(tmp!=null)
    {
      if(tmp instanceof LoopScope)
      {
        return tmp;
      }
      tmp=tmp.getParent();
    }
    return null;
  }
}
