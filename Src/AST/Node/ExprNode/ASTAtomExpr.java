package Compiler.Src.AST.Node.ExprNode;

import java.util.ArrayList;

import AST.ASTNode;
import AST.ASTVisitor;
import Compiler.Src.Util.Info.ExprInfo;
import Compiler.Src.AST.Node.Expr.ASTExpr;
import Compiler.Src.AST.Node.ExprNode.ExprUnitNode.*;
import Compiler.Src.Util.Info.*;

@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter

public class ASTAtomExpr extends ASTExpr {
    public static enum Type {
        INT, BOOL, STRING, FSTRING, CONSTARRAY, INDENTIFIER, NULL, THIS;
      }
    private Type atomType;
    private String value;
    private ASTConstarray constarray;
    private ASTFstring fstring;
    @Override
    public <T> T accept(ASTVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }
}
