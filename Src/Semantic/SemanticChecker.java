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

@lombok.Getter
@lombok.Setter

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

    public SMCError visit(ASTClassDef node) {
        node.addScope(currentScope);
        enterScope((ClassScope) node.getScope());
        var msg = new SMCError();
        msg.append(node.getConstructor().accept(this));
        for (var def : node.getFuncs()) {
            msg.append(def.accept(this));
        }
        exitScope();
        return msg;
    }

    public SMCError visit(ASTFuncDef node)// 处理传参
    {
        node.addScope(currentScope);
        enterScope((FuncScope) node.getScope());
        var msg = new SMCError();
        for (var param : node.getParams()) {
            msg.append(param.accept(this));
        }
        msg.append(node.getBlockedBody().accept(this));
        exitScope();
        return msg;
    }

    public SMCError visit(ASTVarDef node) {
        var msg = new SMCError();
        VarInfo info = node.getInfo();
        if (!ValidVarType(info.getType())) {
            return new SMCError("Invalid type\n");
        } else if (currentScope.contains(node.getName())) {
            return new SMCError("redefination\n");
        } else {
            if (node.getInitexpr() != null) {
                msg.append(node.getInitexpr().accept(this));
                BaseInfo type = node.getInitexpr().getInfo().getType();
                if (!(type instanceof TypeInfo || type.equals(info.getType()))) {
                    return new SMCError("Incorrect assignment\n");
                }
            }
            currentScope.declare(new VarInfo(node.getName(), info.getType()));
        }
        return msg;
    }

    public SMCError visit(ASTArrayExpr node) {
        var msg = new SMCError();
        msg.append(node.getArrayName().accept(this));
        BaseInfo type = node.getArrayName().getInfo().getType();
        if (!(type instanceof TypeInfo || ((TypeInfo) type).getDepth() == 0)) {
            return new SMCError("Can not access such non-array type\n");
        }
        msg.append(node.getIndex().accept(this));
        BaseInfo index_type = node.getIndex().getInfo().getType();
        if (!(index_type instanceof TypeInfo || index_type.equals(GlobalScope.intType))) {
            return new SMCError("not a correct index for arrayExpr\n");
        }
        node.setInfo(new ExprInfo("ArrayExpr", new TypeInfo(type.getName(), ((TypeInfo) type).getDepth() - 1), true));
        return msg;
    }

    public SMCError visit(ASTAssignExpr node) {
        var msg = new SMCError();
        msg.append(node.getLeft().accept(this));
        BaseInfo LexprInfo = node.getLeft().getInfo();
        msg.append(node.getRight().accept(this));
        BaseInfo RexprInfo = node.getRight().getInfo();
        if (!(LexprInfo instanceof ExprInfo || RexprInfo instanceof ExprInfo)) {
            return new SMCError("Have none ExprInfo expression\n");
        }
        if (!((ExprInfo) LexprInfo).getType().equals(((ExprInfo) RexprInfo).getType())) {
            return new SMCError("lhs and rhs is not the same type\n");
        }
        if (!(LexprInfo.isLvalue())) {
            return new SMCError("left hand side is a left value\n");
        }
        node.setInfo(new ExprInfo("assignExpr", ((ExprInfo) LexprInfo).getType(), true));
        return msg;
    }

    public SMCError visit(ASTAtomExpr node) {
        var msg = new SMCError();
        if (node.getAtomType() == ASTAtomExpr.Type.INT) {
            node.setInfo(new ExprInfo("atomExpr", new TypeInfo(GlobalScope.intType, 0), false));
        } else if (node.getAtomType() == ASTAtomExpr.Type.BOOL) {
            node.setInfo(new ExprInfo("atomExpr", new TypeInfo(GlobalScope.boolType, 0), false));
        } else if (node.getAtomType() == ASTAtomExpr.Type.STRING) {
            node.setInfo(new ExprInfo("atomExpr", new TypeInfo(GlobalScope.stringType, 0), false));
        } else if (node.getAtomType() == ASTAtomExpr.Type.NULL) {
            node.setInfo(new ExprInfo("atomExpr", new TypeInfo(GlobalScope.nullType, 0), false));
        } else if (node.getAtomType() == ASTAtomExpr.Type.FSTRING) {
            msg.append(node.getFstring().accept(this));
            node.setInfo(new ExprInfo("atomExpr", new TypeInfo(GlobalScope.stringType, 0), false));
        } else if (node.getAtomType() == ASTAtomExpr.Type.CONSTARRAY) {
            msg.append(node.getConstarray().accept(this));
            // int depth = node.getConstarray().getExpr(0).getInfo().getType().getDepth();
            // for (var unit : node.getConstarray().getExpr()) {
            // if (!unit.getInfo().getType() instanceof TypeInfo) {
            // return new SMCError("constarray only contains TypeInfo\n");
            // }
            // int size = ((TypeInfo) unit.getInfo().getType()).getDepth();
            // if (!((TypeInfo) unit.getInfo().getType()).getName().equals("int")) {
            // return new SMCError("constarray only contains Integer\n");
            // }
            // if (depth != size) {
            // return new SMCError("each expr in costarray must have the same depth\n");
            // } else if (((ExprInfo) unit.getInfo()).isLvalue()) {
            // return new SMCError("constarray shouldn't contain Lvalue expression\n");
            // }
            // }
            // depth = depth + 1;
            node.setInfo(new ExprInfo("atomExpr", node.getConstarray().getInfo().getType(), false));
        } else if (node.getAtomType() == ASTAtomExpr.Type.INDENTIFIER) {
            BaseInfo info = currentScope.BackSearch(node.getValue());
            if (info == null) {
                return new msg("Undefined indentifier\n");
            } else if (info instanceof VarInfo) {
                node.setInfo(new ExprInfo("atomExpr", info, true));
            } else if (info instanceof FuncInfo) {
                node.setInfo(new ExprInfo("atomExpr", info, false));
            } else {
                return new msg("Identifier is possiblily a class name, shouldn't be here\n");
            }
        } else if (node.getAtomType() == ASTAtomExpr.Type.THIS) {
            BaseScope scope = whichClass(currentScope);
            if (scope == null) {
                return new SMCError("Invalid use of THIS out of a ClassScope\n");
            }
            node.setInfo(new ExprInfo("atomExpr", new TypeInfo(scope.getInfo().getName(), 0), true));
        } else {
            return new SMCError("Invalid AtomExprn\n");
        }
        return msg;
    }

    public SMCError visit(ASTBinaryExpr node) {
        var msg = new SMCError();
        msg.append(node.getLeft().accept(this));
        msg.append(node.getRight().accept(this));
        TypeInfo Ltype = null;
        if (node.getLeft().getInfo().getType() instanceof TypeInfo) {
            Ltype = node.getLeft().getInfo().getType();
        } else if (node.getLeft().getInfo().getType() instanceof VarInfo) {
            Ltype = ((VarInfo) node.getLeft().getInfo().getType()).getType();
        } else {
            return new SMCError("Invalid Ltype\n");
        }
        TypeInfo Rtype = null;
        if (node.getRight().getInfo().getType() instanceof TypeInfo) {
            Rtype = node.getRight().getInfo().getType();
        } else if (node.getRight().getInfo().getType() instanceof VarInfo) {
            Rtype = ((VarInfo) node.getRight().getInfo().getType()).getType();
        } else {
            return new SMCError("Invalid Rtype\n");
        }

        if (Ltype.equals(Rtype)) {
            return new SMCError("Invalid BinarryExpr between different Type\n");
        }

        if (Ltype.getDepth() > 0) {
            if (!(node.getOp().equals("==") || node.getOp().equals("!="))) {
                return new SMCError("Invalid op for depth-array\n");
            }
        } else if (Ltype.equals(GlobalScope.intType)) {
            if (node.getOp().equals("!")) {
                return new SMCError("Integer not support !\n");
            }
        } else if (Ltype.equals(GlobalScope.boolType)) {
            if (!(node.getOp().equals("==") || node.getOp().equals("!=") || node.getOp().equals("&&")
                    || node.getOp().equals("||"))) {
                return new SMCError("Boolean only support == !=\n");
            }
        } else if (Ltype.equals(GlobalScope.stringType) || Ltype.equals(GlobalScope.fstringType)) {
            if (!(node.getOp().equals("==") || node.getOp().equals("!=") || node.getOp().equals("+")
                    || node.getOp().equals("<") || node.getOp().equals(">") || node.getOp().equals("<=")
                    || node.getOp().equals(">="))) {
                return new SMCError("Invalid op for string\n");
            }
        } else if (Ltype.equals(GlobalScope.constarrayType)) {
            if (!(node.getOp().equals("==") || node.getOp().equals("!="))) {
                return new SMCError("Invalid op for constarray\n");
            }
        } else if (Ltype.equals(GlobalScope.nullType)) {
            if (!(node.getOp().equals("==") || node.getOp().equals("!="))) {
                return new SMCError("Invalid op for constarray\n");
            }
        } else {
            return new SMCError("Invalid Type for BinaryExpr\n");
        }
        if (node.getOp().equals("<") || node.getOp().equals(">") || node.getOp().equals("<=")
                || node.getOp().equals(">=")
                || node.getOp().equals("==") || node.getOp().equals("!=")) {
            node.setInfo(new ExprInfo("binaryExpr", GlobalScope.boolType, false));
        } else {
            node.setInfo(new ExprInfo("binaryExpr", Ltype, false));
        }
        return msg;
    }

    public SMCError visit(ASTCallExpr node) {
        var msg = new SMCError();
        msg.append(node.getFunc().accept(this));
        for (var def : node.getArgs()) {
            msg.append(def.accept(this));
        }
        BaseInfo functype = node.getFunc().getInfo().getType();
        if (!(functype instanceof FuncInfo)) {
            return new SMCError("Call to undefined Function\n");
        }
        if (node.getArgs().size() != ((FuncInfo) functype).getParams().size()) {
            return new SMCError("Incorrect args number\n");
        }
        for (int i = 0; i < ((FuncInfo) functype).getParams().size(); ++i) {
            TypeInfo type = ((FuncInfo) functype).getParams(i);
            ExprInfo exprInfo = node.getArgs(i).getInfo();
            TypeInfo recv = exprInfo.getDepTypeInfo();
            if (recv == null) {
                return new SMCError("invalid params in callExpr\n");
            }
            if (!recv.equals(type)) {
                return new SMCError("args not match with input\n");
            }
        }
        node.setInfo(new ExprInfo("callExpr", functype.getFunctype(), false));
        return msg;
    }

    public SMCError visit(ASTConditionalExpr node) {
        var msg = new SMCError();
        msg.append(node.getQues().accept(this));
        msg.append(node.getLeft().accept(this));
        msg.append(node.getRight().accept(this));
        TypeInfo quesType = node.getQues().getInfo().getDepTypeInfo();
        TypeInfo leftType = node.getLeft().getInfo().getDepTypeInfo();
        TypeInfo rightType = node.getRight().getInfo().getDepTypeInfo();

        if (quesType == null || leftType == null || rightType == null) {
            return new SMCError("Invalid Expr of conditionalExpr\n");
        }
        if (!quesType.equals(GlobalScope.boolType)) {
            return new SMCError("Ques is not a boolean type\n");
        }
        if (!leftType.equals(rightType)) {
            return new SMCError("Lhs and Rhs type not match in conditionalExpr\n");
        }

        node.setInfo(new ExprInfo("conditionalExpr", leftType, false));
        return msg;
    }

    public SMCError visit(ASTMemberExpr node) {
        var msg = new SMCError();
        msg.append(node.getMember());
        TypeInfo type = node.getMember().getInfo().getDepTypeInfo();
        if (type == null) {
            return new SMCError("Invalid type in MemberExpr\n");
        }
        if (type.getDepth() > 0) {
            if (!node.getMemberName().equals("size")) {
                return new SMCError("Unexpected member call for depth-array\n");
            } else {
                node.setInfo(new ExprInfo("memberExpr", GlobalScope.arraySize, false));
            }
        } else {
            ClassInfo classinfo = globalScope.containsClasses(node.getMember().getInfo().getType().getName());
            if (classinfo == null) {
                return new SMCError("no such class\n");
            }
            BaseInfo info = classinfo.get(node.getMemberName());
            if (info instanceof VarInfo) {
                node.setInfo(new ExprInfo("memberExpr", info, true));
            } else if (info instanceof FuncInfo) {
                node.setInfo(new ExprInfo("callExpr", info, false));
            } else {
                return new SMCError("such member in class is not defined\n");
            }
        }
        return msg;
    }

    public SMCError visit(ASTNewExpr node) {
        var msg = new SMCError();
        ClassInfo clsinfo = globalScope.containsClasses(node.getType().getName());
        if (clsinfo == null) {
            return new SMCError("no such class in newExpr\n");
        }
        if ((clsinfo.getName().equals("int") || clsinfo.getName().equals("string") || clsinfo.getName().equals("bool")
                || clsinfo.getName().equals("void") || clsinfo.getName().equals("null"))
                && node.getType().getDepth() == 0) {
            return new SMCError("default type not support newExpr\n");
        }
        for (var unit : node.getSize()) {
            msg.append(unit.accept(this));
            if (unit.getInfo().getDepTypeInfo() == null || unit.getInfo().getDepTypeInfo() != GlobalScope.intType) {
                return new SMCError("size must be Integer in newExpr\n");
            }
        }
        if (node.getConstarray() != null) {
            msg.append(node.getConstarray().accept(this));
            if (!((TypeInfo) node.getConstarray().getInfo().getDepTypeInfo()).getName().equals("int")) {
                return new SMCError("constarray need to contain Integer\n");
            }
            if (node.getType().getDepth() != node.getConstarray().getInfo().getDepTypeInfo().getDepth()) {
                return new SMCError("array size not match");
            }
        }
        node.setInfo(new ExprInfo("newExpr", node.getType(), true));
        return msg;
    }

    public SMCError visit(ASTParenExpr node) {
        return new SMCError(node.getExpr.accept(this));
    }

    public SMCError visit(ASTPreunaryExpr node) {
        var msg = new SMCError();
        msg.append(node.getExpr().accept(this));
        TypeInfo type = node.getExpr().getInfo().getDepTypeInfo();
        if (type == null) {
            return new SMCError("Invalid type in preunaryExpr\n");
        }
        if (node.getOp().equals("!")) {
            if (!type.equals(GlobalScope.boolType)) {
                return new SMCError("Not boolean after ! in preunaryExpr\n");
            }
            node.setInfo(new ExprInfo("preunaryExpr", type, false));
        } else {
            if (!type.equals(GlobalScope.intType)) {
                return new SMCError("only support Integer here in preunaryExpr\n");
            }
            if (node.getOp().equals("++") || node.getOp().equals("--")) {
                if (!node.getExpr().getInfo().isLvalue()) {
                    return new SMCError("preunaryExpr not support due to not Lvalue\n");
                }
            }
            node.setInfo(new ExprInfo("preunaryExpr", type, true));
        }
        return msg;
    }

    public SMCError visit(ASTUnaryExpr node) {
        var msg = new SMCError();
        msg.append(node.getExpr().accept(this));
        TypeInfo type = node.getExpr().getInfo().getDepTypeInfo();
        if (type == null) {
            return new SMCError("Invalid type in unaryExpr\n");
        }
        if (!type.equals(GlobalScope.intType)) {
            return new SMCError("only support Integer here in unaryExpr\n");
        }
        if (!node.getExpr().getInfo().isLvalue()) {
            return new SMCError("unaryExpr not support due to not Lvalue\n");
        }
        node.setInfo(new ExprInfo("preunaryExpr", type, false));
        return msg;
    }

    public SMCError visit(ASTConstarray node) {
        var msg = new SMCError();
        int depth = -1;
        for (ASTExpr unit : node.getExpr()) {
            msg.append(unit.accept(this));
            if (!(unit.getInfo().getType() instanceof TypeInfo)) {
                return new SMCError("constarray only contains TypeInfo\n");
            }
            int size = ((TypeInfo) unit.getInfo().getType()).getDepth();
            if (!((TypeInfo) unit.getInfo().getType()).getName().equals("int")) {
                return new SMCError("constarray only contains Integer\n");
            }
            if (depth != -1 && depth != size) {
                return new SMCError("each expr in costarray must have the same depth\n");
            } else if (((ExprInfo) unit.getInfo()).isLvalue()) {
                return new SMCError("constarray shouldn't contain Lvalue expression\n");
            }
            depth = size;
        }
        depth = depth + 1;
        node.setInfo(new ExprInfo("constarrayExpr", new TypeInfo(GlobalScope.intType, depth), false));
        return msg;
    }

    public SMCError visit(ASTFstring node) {
        var msg = new SMCError();
        for (ASTExpr unit : node.getExprpart()) {
            msg.append(unit.accept(this));
            TypeInfo type = unit.getInfo().getDepTypeInfo();
            if (type == null) {
                return new SMCError("Invalid type in Fsting\n");
            }
            if (!(type.equals(GlobalScope.intType) || type.equals(GlobalScope.boolType)
                    || type.equals(GlobalScope.stringType))) {
                return new SMCError("Only int bool string is supported in fstring\n");
            }
        }
        node.setInfo(new ExprInfo("fstringExpr", GlobalScope.stringType, false));
        return msg;
    }

    public SMCError visit(ASTBlockstatement node) {
        node.addScope(currentScope);
        enterScope(node.getScope());
        var msg = new SMCError();
        for (ASTStatement stmt : node.getStmts()) {
            msg.append(stmt.accept(this));
        }
        exitScope();
        return msg;
    }

    public SMCError visit(ASTBreakstatement node)
    {
        if(currentScope.whichLoop()==null)
        {
            return new SMCError("Break should be called inside Loop\n");
        }
        return new SMCError();
    }

    public SMCError visit(ASTContinuestatement node)
    {
        if(currentScope.whichLoop()==null)
        {
            return new SMCError("Continue should be called inside Loop\n");
        }
        return new SMCError();
    }

    public SMCError visit(ASTEmptystatement node)
    {
        return new SMCError();
    }

    public SMCError visit(ASTExpressionstatement node)
    {
        var msg=new SMCError();
        for(ASTExpr unit:node.getExpr())
        {
            msg.append(unit.accept(this));
            if(unit.getInfo().getName().equals("atomExpr"))
            {
                return new SMCError("incomplete expression which is atom\n");
            }
        }
        return msg;
    }

    public SMCError visit(ASTForstatement node)
    {
        node.addScope(currentScope);
        enterScope(node.getScope());
        var msg=new SMCError();
        if(node.getVarinit()!=null)
        {
            msg.append(node.getVarinit().accept(this));
        }
        else if(node.getExprinit()!=null)
        {
            msg.append(node.getExprinit().accept(this));
        }
        else{
            return new SMCError("Invalid init in forLoop\n");
        }

        if(node.getCond()!=null)
        {
            msg.append(node.getCond().accept(this));
            TypeInfo type=((ExprInfo)node.getCond().getInfo()).getDepTypeInfo();
            if(type==null || !type.equals(GlobalScope.boolType))
            {
                return new SMCError("Invalid type in forLoop\n");
            }
        }
        if(node.getStep()!=null)
        {
            msg.append(node.getStep().accept(this));
        }

        msg.append(node.getStmts());
        exitScope();
        return msg;
    }

    public SMCError visit(ASTIfstatement node)
    {
        node.addIfScope(currentScope);
        enterScope(node.getIfScope());
        var msg=new SMCError();
        msg.append(node.getJudge().accept(this));
        TypeInfo type=((ExprInfo)node.getJudge().getInfo()).getDepTypeInfo();
        if(type==null || !type.equals(GlobalScope.boolType))
        {
            return new SMCError("Invalid type in IfStatement\n");
        }
        msg.append(node.getIfstmt());
        exitScope();
        node.addElseScope(currentScope);
        enterScope(node.getElseScope());
        msg.append(node.getElsestmt());
        exitScope();
        return msg;
    }

    public SMCError visit(ASTReturnstatement node)
    {
        var msg=new SMCError();
        FuncScope funcScope=whichFunc(currentScope);
        if(funcScope==null)
        {
            return new SMCError("Return should be used in Function\n");
        }
        TypeInfo funcType=((FuncInfo)funcScope.getInfo()).getFunctype();
        if(node.getRet()==null)
        {
            if(!funcType.equals(GlobalScope.voidType))
            {
                return new SMCError("No return type not match\n");
            }
        }
        else{
            msg.append(node.getRet().accept(this));
            TypeInfo retType=((ExprInfo)node.getRet().getInfo()).getDepTypeInfo();
            if(retType==null || !retType.equals(funcType))
            {
                return new SMCError("Return type not match\n");
            }
        }
    }

    public SMCError visit(ASTVarstatement node)
    {
        var msg=new SMCError();
        for(ASTVarDef def:node.getVarDefs())
        {
            msg.append(def.accept(this));
        }
        return msg;
    }

    public SMCError visit(ASTWhilestatement node)
    {
        node.addScope(currentScope);
        enterScope(node.getScope());
        var msg=new SMCError();
        TypeInfo type=((ExprInfo)node.getJudge().getInfo()).getDepTypeInfo();
        if(type==null || !type.equals(GlobalScope.boolType))
        {
            return new SMCError("Invalid type in WhileLoop\n");
        }
        msg.append(node.getStmts().accept(this));
        exitScope();
        return msg;
    }
}
