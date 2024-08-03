grammar Mx;
import Lexer;
@header {package dev.Czar.frontend.Mx.grammar;}
program: (funDef | classDef | varDef)* EOF;
type: Int | Bool | String | Void | Identifier;

classDef:
	Class Identifier Lbrace (varDef | classBuild | funDef)* Rbrace Semi;
typeVarDef: type (Lbracket Rbracket)*;
varDef: typeVarDef atomVarDef (',' atomVarDef)* Semi;
atomVarDef: Identifier ('=' expression)?;
classBuild: Identifier LParen RParen block;
block: Lbrace ( block | statement)* Rbrace | Semi (Semi)*;
funDef: typeVarDef Identifier LParen funParaList? RParen block;
funParaList: typeVarDef Identifier (',' typeVarDef Identifier)*;

statement:
	varDef
	| ifstatement
	| whilestatement
	| forstatement
	| returnStatement
	| breakStatement
	| continueStatement
	| expressionstatement
	| Semi;

ifstatement:
	If LParen expression RParen (block | statement | Semi) (
		Else (block | statement | Semi)
	)?;
whilestatement:
	While LParen expression RParen (block | statement | Semi);
forstatement:
	For LParen (expression | varDef | Semi)? Semi expression? Semi expression? RParen (
		block
		| statement
		| Semi
	);

returnStatement: Return expression? Semi;
breakStatement: Break Semi;
continueStatement: Continue Semi;
expressionstatement: expression Semi;

initexpr:
	New type (Lbracket (expression)? Rbracket)*	# newArrayExpr
	| New type (LParen RParen)?					# newVarExpr;

expression:
	initexpr														# newExpr
	| LParen expression RParen										# parenExpr
	| expression LParen (expression (Comma expression)*)? RParen	# callExpr
	| expression op = Member Identifier								# memberExpr
	| expression Lbracket (expression)? Rbracket					# arrayExpr
	| expression op = (Selfadd | Selfsub)							# unaryExpr
	| <assoc = right>op = (
		Selfadd
		| Selfsub
		| Not
		| LogicNot
		| Sub
	) expression # unaryExpr

	//BinaryExpr
	| expression op = (Mul | Div | Mod) expression								# binaryExpr
	| expression op = (Add | Sub) expression									# binaryExpr
	| expression op = (LeftShift | RightShift) expression						# binaryExpr
	| expression op = (Less | Greater | LessEqual | GreaterEqual) expression	# binaryExpr
	| expression op = (Equal | UnEqual) expression								# binaryExpr
	| expression op = (And | Xor | Or) expression								# binaryExpr
	| expression op = (LogicAnd | LogicOr) expression							# binaryExpr
	| <assoc = right> expression Question expression Colon expression			# conditionalExpr

	//AssignExpr
	| <assoc = right> expression op = Assign expression # assignExpr

	//AtomExpr
	| atom # atomExpr;

atom:
	Integer
	| fstring
	| True
	| False
	| This
	| Null
	| Identifier
	| StringLiteral;

fstring:
	( FomatStringL expression (FomatStringM expression)* FomatStringR ) | FStringLiteral;
