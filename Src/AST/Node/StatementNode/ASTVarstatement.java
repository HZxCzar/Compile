package Compiler.Src.AST.Node.StatementNode;

import java.util.ArrayList;

import AST.ASTNode;
import AST.ASTVisitor;
import Compiler.Src.Util.Info.StmtInfo;
import Compiler.Src.Util.Info.TypeInfo;
import Compiler.Src.AST.Node.Def.ASTVarDef;
import Compiler.Src.AST.Node.Statement.ASTStatement;
@lombok.experimental.SuperBuilder
@lombok.Getter
@lombok.Setter
public class ASTVarstatement extends ASTStatement {
    private final ArrayList<ASTVarDef> VarDefs;
    @Override
    public <T> T accept(ASTVisitor<T> visitor) throws BaseError {
        return visitor.visit(this);
    }
}
