// Generated from Mx.g4 by ANTLR 4.13.1
package dev.Czar.frontend.Mx.grammar;
package dev.Czar.frontend.Mx.grammar;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MxParser}.
 */
public interface MxListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MxParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MxParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MxParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(MxParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(MxParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#classDef}.
	 * @param ctx the parse tree
	 */
	void enterClassDef(MxParser.ClassDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#classDef}.
	 * @param ctx the parse tree
	 */
	void exitClassDef(MxParser.ClassDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#typeVarDef}.
	 * @param ctx the parse tree
	 */
	void enterTypeVarDef(MxParser.TypeVarDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#typeVarDef}.
	 * @param ctx the parse tree
	 */
	void exitTypeVarDef(MxParser.TypeVarDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#varDef}.
	 * @param ctx the parse tree
	 */
	void enterVarDef(MxParser.VarDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#varDef}.
	 * @param ctx the parse tree
	 */
	void exitVarDef(MxParser.VarDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#atomVarDef}.
	 * @param ctx the parse tree
	 */
	void enterAtomVarDef(MxParser.AtomVarDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#atomVarDef}.
	 * @param ctx the parse tree
	 */
	void exitAtomVarDef(MxParser.AtomVarDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#classBuild}.
	 * @param ctx the parse tree
	 */
	void enterClassBuild(MxParser.ClassBuildContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#classBuild}.
	 * @param ctx the parse tree
	 */
	void exitClassBuild(MxParser.ClassBuildContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(MxParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(MxParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#funDef}.
	 * @param ctx the parse tree
	 */
	void enterFunDef(MxParser.FunDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#funDef}.
	 * @param ctx the parse tree
	 */
	void exitFunDef(MxParser.FunDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#funParaList}.
	 * @param ctx the parse tree
	 */
	void enterFunParaList(MxParser.FunParaListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#funParaList}.
	 * @param ctx the parse tree
	 */
	void exitFunParaList(MxParser.FunParaListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(MxParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(MxParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#ifstatement}.
	 * @param ctx the parse tree
	 */
	void enterIfstatement(MxParser.IfstatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#ifstatement}.
	 * @param ctx the parse tree
	 */
	void exitIfstatement(MxParser.IfstatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#whilestatement}.
	 * @param ctx the parse tree
	 */
	void enterWhilestatement(MxParser.WhilestatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#whilestatement}.
	 * @param ctx the parse tree
	 */
	void exitWhilestatement(MxParser.WhilestatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#forstatement}.
	 * @param ctx the parse tree
	 */
	void enterForstatement(MxParser.ForstatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#forstatement}.
	 * @param ctx the parse tree
	 */
	void exitForstatement(MxParser.ForstatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(MxParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(MxParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(MxParser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(MxParser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStatement(MxParser.ContinueStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStatement(MxParser.ContinueStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#expressionstatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionstatement(MxParser.ExpressionstatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#expressionstatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionstatement(MxParser.ExpressionstatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newArrayExpr}
	 * labeled alternative in {@link MxParser#initexpr}.
	 * @param ctx the parse tree
	 */
	void enterNewArrayExpr(MxParser.NewArrayExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newArrayExpr}
	 * labeled alternative in {@link MxParser#initexpr}.
	 * @param ctx the parse tree
	 */
	void exitNewArrayExpr(MxParser.NewArrayExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newVarExpr}
	 * labeled alternative in {@link MxParser#initexpr}.
	 * @param ctx the parse tree
	 */
	void enterNewVarExpr(MxParser.NewVarExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newVarExpr}
	 * labeled alternative in {@link MxParser#initexpr}.
	 * @param ctx the parse tree
	 */
	void exitNewVarExpr(MxParser.NewVarExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNewExpr(MxParser.NewExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNewExpr(MxParser.NewExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code unaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterUnaryExpr(MxParser.UnaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code unaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitUnaryExpr(MxParser.UnaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArrayExpr(MxParser.ArrayExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArrayExpr(MxParser.ArrayExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code memberExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMemberExpr(MxParser.MemberExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code memberExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMemberExpr(MxParser.MemberExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAtomExpr(MxParser.AtomExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code atomExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAtomExpr(MxParser.AtomExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpr(MxParser.BinaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExpr(MxParser.BinaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code callExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCallExpr(MxParser.CallExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code callExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCallExpr(MxParser.CallExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAssignExpr(MxParser.AssignExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAssignExpr(MxParser.AssignExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParenExpr(MxParser.ParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParenExpr(MxParser.ParenExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code conditionalExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterConditionalExpr(MxParser.ConditionalExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code conditionalExpr}
	 * labeled alternative in {@link MxParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitConditionalExpr(MxParser.ConditionalExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(MxParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(MxParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxParser#fstring}.
	 * @param ctx the parse tree
	 */
	void enterFstring(MxParser.FstringContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxParser#fstring}.
	 * @param ctx the parse tree
	 */
	void exitFstring(MxParser.FstringContext ctx);
}