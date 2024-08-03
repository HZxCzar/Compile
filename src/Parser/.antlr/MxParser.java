// Generated from /home/czar/Compile/Compile/src/Parser/Mx.g4 by ANTLR 4.13.1
package dev.Czar.frontend.Mx.grammar;
package dev.Czar.frontend.Mx.grammar;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class MxParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		Add=1, Sub=2, Mul=3, Div=4, Mod=5, Greater=6, Less=7, GreaterEqual=8, 
		LessEqual=9, UnEqual=10, Equal=11, LogicAnd=12, LogicOr=13, LogicNot=14, 
		RightShift=15, LeftShift=16, And=17, Or=18, Xor=19, Not=20, Assign=21, 
		Selfadd=22, Selfsub=23, Member=24, Lbracket=25, Rbracket=26, LParen=27, 
		RParen=28, Question=29, Colon=30, Semi=31, Comma=32, Lbrace=33, Rbrace=34, 
		Void=35, Bool=36, Int=37, String=38, New=39, Class=40, Null=41, True=42, 
		False=43, This=44, If=45, Else=46, For=47, While=48, Break=49, Continue=50, 
		Return=51, WhiteSpace=52, LineComment=53, BlockComment=54, Identifier=55, 
		Integer=56, StringLiteral=57, FomatStringL=58, FomatStringR=59, FomatStringM=60, 
		FStringLiteral=61;
	public static final int
		RULE_program = 0, RULE_type = 1, RULE_classDef = 2, RULE_typeVarDef = 3, 
		RULE_varDef = 4, RULE_atomVarDef = 5, RULE_classBuild = 6, RULE_block = 7, 
		RULE_funDef = 8, RULE_funParaList = 9, RULE_statement = 10, RULE_ifstatement = 11, 
		RULE_whilestatement = 12, RULE_forstatement = 13, RULE_returnStatement = 14, 
		RULE_breakStatement = 15, RULE_continueStatement = 16, RULE_expressionstatement = 17, 
		RULE_initexpr = 18, RULE_expression = 19, RULE_atom = 20, RULE_fstring = 21;
	private static String[] makeRuleNames() {
		return new String[] {
			"program", "type", "classDef", "typeVarDef", "varDef", "atomVarDef", 
			"classBuild", "block", "funDef", "funParaList", "statement", "ifstatement", 
			"whilestatement", "forstatement", "returnStatement", "breakStatement", 
			"continueStatement", "expressionstatement", "initexpr", "expression", 
			"atom", "fstring"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'+'", "'-'", "'*'", "'/'", "'%'", "'>'", "'<'", "'>='", "'<='", 
			"'!='", "'=='", "'&&'", "'||'", "'!'", "'>>'", "'<<'", "'&'", "'|'", 
			"'^'", "'~'", "'='", "'++'", "'--'", "'.'", "'['", "']'", "'('", "')'", 
			"'?'", "':'", "';'", "','", "'{'", "'}'", "'void'", "'bool'", "'int'", 
			"'string'", "'new'", "'class'", "'null'", "'true'", "'false'", "'this'", 
			"'if'", "'else'", "'for'", "'while'", "'break'", "'continue'", "'return'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "Add", "Sub", "Mul", "Div", "Mod", "Greater", "Less", "GreaterEqual", 
			"LessEqual", "UnEqual", "Equal", "LogicAnd", "LogicOr", "LogicNot", "RightShift", 
			"LeftShift", "And", "Or", "Xor", "Not", "Assign", "Selfadd", "Selfsub", 
			"Member", "Lbracket", "Rbracket", "LParen", "RParen", "Question", "Colon", 
			"Semi", "Comma", "Lbrace", "Rbrace", "Void", "Bool", "Int", "String", 
			"New", "Class", "Null", "True", "False", "This", "If", "Else", "For", 
			"While", "Break", "Continue", "Return", "WhiteSpace", "LineComment", 
			"BlockComment", "Identifier", "Integer", "StringLiteral", "FomatStringL", 
			"FomatStringR", "FomatStringM", "FStringLiteral"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "Mx.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public MxParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ProgramContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(MxParser.EOF, 0); }
		public List<FunDefContext> funDef() {
			return getRuleContexts(FunDefContext.class);
		}
		public FunDefContext funDef(int i) {
			return getRuleContext(FunDefContext.class,i);
		}
		public List<ClassDefContext> classDef() {
			return getRuleContexts(ClassDefContext.class);
		}
		public ClassDefContext classDef(int i) {
			return getRuleContext(ClassDefContext.class,i);
		}
		public List<VarDefContext> varDef() {
			return getRuleContexts(VarDefContext.class);
		}
		public VarDefContext varDef(int i) {
			return getRuleContext(VarDefContext.class,i);
		}
		public ProgramContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_program; }
	}

	public final ProgramContext program() throws RecognitionException {
		ProgramContext _localctx = new ProgramContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_program);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(49);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 36030411926667264L) != 0)) {
				{
				setState(47);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
				case 1:
					{
					setState(44);
					funDef();
					}
					break;
				case 2:
					{
					setState(45);
					classDef();
					}
					break;
				case 3:
					{
					setState(46);
					varDef();
					}
					break;
				}
				}
				setState(51);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(52);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeContext extends ParserRuleContext {
		public TerminalNode Int() { return getToken(MxParser.Int, 0); }
		public TerminalNode Bool() { return getToken(MxParser.Bool, 0); }
		public TerminalNode String() { return getToken(MxParser.String, 0); }
		public TerminalNode Void() { return getToken(MxParser.Void, 0); }
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public TypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_type; }
	}

	public final TypeContext type() throws RecognitionException {
		TypeContext _localctx = new TypeContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_type);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(54);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 36029312415039488L) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassDefContext extends ParserRuleContext {
		public TerminalNode Class() { return getToken(MxParser.Class, 0); }
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public TerminalNode Lbrace() { return getToken(MxParser.Lbrace, 0); }
		public TerminalNode Rbrace() { return getToken(MxParser.Rbrace, 0); }
		public TerminalNode Semi() { return getToken(MxParser.Semi, 0); }
		public List<VarDefContext> varDef() {
			return getRuleContexts(VarDefContext.class);
		}
		public VarDefContext varDef(int i) {
			return getRuleContext(VarDefContext.class,i);
		}
		public List<ClassBuildContext> classBuild() {
			return getRuleContexts(ClassBuildContext.class);
		}
		public ClassBuildContext classBuild(int i) {
			return getRuleContext(ClassBuildContext.class,i);
		}
		public List<FunDefContext> funDef() {
			return getRuleContexts(FunDefContext.class);
		}
		public FunDefContext funDef(int i) {
			return getRuleContext(FunDefContext.class,i);
		}
		public ClassDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classDef; }
	}

	public final ClassDefContext classDef() throws RecognitionException {
		ClassDefContext _localctx = new ClassDefContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_classDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			match(Class);
			setState(57);
			match(Identifier);
			setState(58);
			match(Lbrace);
			setState(64);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 36029312415039488L) != 0)) {
				{
				setState(62);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
				case 1:
					{
					setState(59);
					varDef();
					}
					break;
				case 2:
					{
					setState(60);
					classBuild();
					}
					break;
				case 3:
					{
					setState(61);
					funDef();
					}
					break;
				}
				}
				setState(66);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(67);
			match(Rbrace);
			setState(68);
			match(Semi);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TypeVarDefContext extends ParserRuleContext {
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<TerminalNode> Lbracket() { return getTokens(MxParser.Lbracket); }
		public TerminalNode Lbracket(int i) {
			return getToken(MxParser.Lbracket, i);
		}
		public List<TerminalNode> Rbracket() { return getTokens(MxParser.Rbracket); }
		public TerminalNode Rbracket(int i) {
			return getToken(MxParser.Rbracket, i);
		}
		public TypeVarDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_typeVarDef; }
	}

	public final TypeVarDefContext typeVarDef() throws RecognitionException {
		TypeVarDefContext _localctx = new TypeVarDefContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_typeVarDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			type();
			setState(75);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Lbracket) {
				{
				{
				setState(71);
				match(Lbracket);
				setState(72);
				match(Rbracket);
				}
				}
				setState(77);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class VarDefContext extends ParserRuleContext {
		public TypeVarDefContext typeVarDef() {
			return getRuleContext(TypeVarDefContext.class,0);
		}
		public List<AtomVarDefContext> atomVarDef() {
			return getRuleContexts(AtomVarDefContext.class);
		}
		public AtomVarDefContext atomVarDef(int i) {
			return getRuleContext(AtomVarDefContext.class,i);
		}
		public TerminalNode Semi() { return getToken(MxParser.Semi, 0); }
		public List<TerminalNode> Comma() { return getTokens(MxParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(MxParser.Comma, i);
		}
		public VarDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_varDef; }
	}

	public final VarDefContext varDef() throws RecognitionException {
		VarDefContext _localctx = new VarDefContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_varDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			typeVarDef();
			setState(79);
			atomVarDef();
			setState(84);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(80);
				match(Comma);
				setState(81);
				atomVarDef();
				}
				}
				setState(86);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(87);
			match(Semi);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AtomVarDefContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public TerminalNode Assign() { return getToken(MxParser.Assign, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public AtomVarDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atomVarDef; }
	}

	public final AtomVarDefContext atomVarDef() throws RecognitionException {
		AtomVarDefContext _localctx = new AtomVarDefContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_atomVarDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			match(Identifier);
			setState(92);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==Assign) {
				{
				setState(90);
				match(Assign);
				setState(91);
				expression(0);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ClassBuildContext extends ParserRuleContext {
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public TerminalNode LParen() { return getToken(MxParser.LParen, 0); }
		public TerminalNode RParen() { return getToken(MxParser.RParen, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public ClassBuildContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_classBuild; }
	}

	public final ClassBuildContext classBuild() throws RecognitionException {
		ClassBuildContext _localctx = new ClassBuildContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_classBuild);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			match(Identifier);
			setState(95);
			match(LParen);
			setState(96);
			match(RParen);
			setState(97);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BlockContext extends ParserRuleContext {
		public TerminalNode Lbrace() { return getToken(MxParser.Lbrace, 0); }
		public TerminalNode Rbrace() { return getToken(MxParser.Rbrace, 0); }
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<TerminalNode> Semi() { return getTokens(MxParser.Semi); }
		public TerminalNode Semi(int i) {
			return getToken(MxParser.Semi, i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_block);
		int _la;
		try {
			int _alt;
			setState(115);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Lbrace:
				enterOuterAlt(_localctx, 1);
				{
				setState(99);
				match(Lbrace);
				setState(104);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2850707072395264004L) != 0)) {
					{
					setState(102);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
					case 1:
						{
						setState(100);
						block();
						}
						break;
					case 2:
						{
						setState(101);
						statement();
						}
						break;
					}
					}
					setState(106);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(107);
				match(Rbrace);
				}
				break;
			case Semi:
				enterOuterAlt(_localctx, 2);
				{
				setState(108);
				match(Semi);
				setState(112);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(109);
						match(Semi);
						}
						} 
					}
					setState(114);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FunDefContext extends ParserRuleContext {
		public TypeVarDefContext typeVarDef() {
			return getRuleContext(TypeVarDefContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public TerminalNode LParen() { return getToken(MxParser.LParen, 0); }
		public TerminalNode RParen() { return getToken(MxParser.RParen, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public FunParaListContext funParaList() {
			return getRuleContext(FunParaListContext.class,0);
		}
		public FunDefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funDef; }
	}

	public final FunDefContext funDef() throws RecognitionException {
		FunDefContext _localctx = new FunDefContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_funDef);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
			typeVarDef();
			setState(118);
			match(Identifier);
			setState(119);
			match(LParen);
			setState(121);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 36029312415039488L) != 0)) {
				{
				setState(120);
				funParaList();
				}
			}

			setState(123);
			match(RParen);
			setState(124);
			block();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FunParaListContext extends ParserRuleContext {
		public List<TypeVarDefContext> typeVarDef() {
			return getRuleContexts(TypeVarDefContext.class);
		}
		public TypeVarDefContext typeVarDef(int i) {
			return getRuleContext(TypeVarDefContext.class,i);
		}
		public List<TerminalNode> Identifier() { return getTokens(MxParser.Identifier); }
		public TerminalNode Identifier(int i) {
			return getToken(MxParser.Identifier, i);
		}
		public List<TerminalNode> Comma() { return getTokens(MxParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(MxParser.Comma, i);
		}
		public FunParaListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_funParaList; }
	}

	public final FunParaListContext funParaList() throws RecognitionException {
		FunParaListContext _localctx = new FunParaListContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_funParaList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(126);
			typeVarDef();
			setState(127);
			match(Identifier);
			setState(134);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==Comma) {
				{
				{
				setState(128);
				match(Comma);
				setState(129);
				typeVarDef();
				setState(130);
				match(Identifier);
				}
				}
				setState(136);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StatementContext extends ParserRuleContext {
		public VarDefContext varDef() {
			return getRuleContext(VarDefContext.class,0);
		}
		public IfstatementContext ifstatement() {
			return getRuleContext(IfstatementContext.class,0);
		}
		public WhilestatementContext whilestatement() {
			return getRuleContext(WhilestatementContext.class,0);
		}
		public ForstatementContext forstatement() {
			return getRuleContext(ForstatementContext.class,0);
		}
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public BreakStatementContext breakStatement() {
			return getRuleContext(BreakStatementContext.class,0);
		}
		public ContinueStatementContext continueStatement() {
			return getRuleContext(ContinueStatementContext.class,0);
		}
		public ExpressionstatementContext expressionstatement() {
			return getRuleContext(ExpressionstatementContext.class,0);
		}
		public TerminalNode Semi() { return getToken(MxParser.Semi, 0); }
		public StatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_statement; }
	}

	public final StatementContext statement() throws RecognitionException {
		StatementContext _localctx = new StatementContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_statement);
		try {
			setState(146);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(137);
				varDef();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(138);
				ifstatement();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(139);
				whilestatement();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(140);
				forstatement();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(141);
				returnStatement();
				}
				break;
			case 6:
				enterOuterAlt(_localctx, 6);
				{
				setState(142);
				breakStatement();
				}
				break;
			case 7:
				enterOuterAlt(_localctx, 7);
				{
				setState(143);
				continueStatement();
				}
				break;
			case 8:
				enterOuterAlt(_localctx, 8);
				{
				setState(144);
				expressionstatement();
				}
				break;
			case 9:
				enterOuterAlt(_localctx, 9);
				{
				setState(145);
				match(Semi);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class IfstatementContext extends ParserRuleContext {
		public TerminalNode If() { return getToken(MxParser.If, 0); }
		public TerminalNode LParen() { return getToken(MxParser.LParen, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RParen() { return getToken(MxParser.RParen, 0); }
		public List<BlockContext> block() {
			return getRuleContexts(BlockContext.class);
		}
		public BlockContext block(int i) {
			return getRuleContext(BlockContext.class,i);
		}
		public List<StatementContext> statement() {
			return getRuleContexts(StatementContext.class);
		}
		public StatementContext statement(int i) {
			return getRuleContext(StatementContext.class,i);
		}
		public List<TerminalNode> Semi() { return getTokens(MxParser.Semi); }
		public TerminalNode Semi(int i) {
			return getToken(MxParser.Semi, i);
		}
		public TerminalNode Else() { return getToken(MxParser.Else, 0); }
		public IfstatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ifstatement; }
	}

	public final IfstatementContext ifstatement() throws RecognitionException {
		IfstatementContext _localctx = new IfstatementContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_ifstatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(148);
			match(If);
			setState(149);
			match(LParen);
			setState(150);
			expression(0);
			setState(151);
			match(RParen);
			setState(155);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				{
				setState(152);
				block();
				}
				break;
			case 2:
				{
				setState(153);
				statement();
				}
				break;
			case 3:
				{
				setState(154);
				match(Semi);
				}
				break;
			}
			setState(163);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,16,_ctx) ) {
			case 1:
				{
				setState(157);
				match(Else);
				setState(161);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,15,_ctx) ) {
				case 1:
					{
					setState(158);
					block();
					}
					break;
				case 2:
					{
					setState(159);
					statement();
					}
					break;
				case 3:
					{
					setState(160);
					match(Semi);
					}
					break;
				}
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class WhilestatementContext extends ParserRuleContext {
		public TerminalNode While() { return getToken(MxParser.While, 0); }
		public TerminalNode LParen() { return getToken(MxParser.LParen, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RParen() { return getToken(MxParser.RParen, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public TerminalNode Semi() { return getToken(MxParser.Semi, 0); }
		public WhilestatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whilestatement; }
	}

	public final WhilestatementContext whilestatement() throws RecognitionException {
		WhilestatementContext _localctx = new WhilestatementContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_whilestatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(165);
			match(While);
			setState(166);
			match(LParen);
			setState(167);
			expression(0);
			setState(168);
			match(RParen);
			setState(172);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				{
				setState(169);
				block();
				}
				break;
			case 2:
				{
				setState(170);
				statement();
				}
				break;
			case 3:
				{
				setState(171);
				match(Semi);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ForstatementContext extends ParserRuleContext {
		public TerminalNode For() { return getToken(MxParser.For, 0); }
		public TerminalNode LParen() { return getToken(MxParser.LParen, 0); }
		public List<TerminalNode> Semi() { return getTokens(MxParser.Semi); }
		public TerminalNode Semi(int i) {
			return getToken(MxParser.Semi, i);
		}
		public TerminalNode RParen() { return getToken(MxParser.RParen, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public StatementContext statement() {
			return getRuleContext(StatementContext.class,0);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public VarDefContext varDef() {
			return getRuleContext(VarDefContext.class,0);
		}
		public ForstatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_forstatement; }
	}

	public final ForstatementContext forstatement() throws RecognitionException {
		ForstatementContext _localctx = new ForstatementContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_forstatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(174);
			match(For);
			setState(175);
			match(LParen);
			setState(179);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
			case 1:
				{
				setState(176);
				expression(0);
				}
				break;
			case 2:
				{
				setState(177);
				varDef();
				}
				break;
			case 3:
				{
				setState(178);
				match(Semi);
				}
				break;
			}
			setState(181);
			match(Semi);
			setState(183);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2846308499750666244L) != 0)) {
				{
				setState(182);
				expression(0);
				}
			}

			setState(185);
			match(Semi);
			setState(187);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2846308499750666244L) != 0)) {
				{
				setState(186);
				expression(0);
				}
			}

			setState(189);
			match(RParen);
			setState(193);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,21,_ctx) ) {
			case 1:
				{
				setState(190);
				block();
				}
				break;
			case 2:
				{
				setState(191);
				statement();
				}
				break;
			case 3:
				{
				setState(192);
				match(Semi);
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ReturnStatementContext extends ParserRuleContext {
		public TerminalNode Return() { return getToken(MxParser.Return, 0); }
		public TerminalNode Semi() { return getToken(MxParser.Semi, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(195);
			match(Return);
			setState(197);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2846308499750666244L) != 0)) {
				{
				setState(196);
				expression(0);
				}
			}

			setState(199);
			match(Semi);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class BreakStatementContext extends ParserRuleContext {
		public TerminalNode Break() { return getToken(MxParser.Break, 0); }
		public TerminalNode Semi() { return getToken(MxParser.Semi, 0); }
		public BreakStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_breakStatement; }
	}

	public final BreakStatementContext breakStatement() throws RecognitionException {
		BreakStatementContext _localctx = new BreakStatementContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_breakStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(201);
			match(Break);
			setState(202);
			match(Semi);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ContinueStatementContext extends ParserRuleContext {
		public TerminalNode Continue() { return getToken(MxParser.Continue, 0); }
		public TerminalNode Semi() { return getToken(MxParser.Semi, 0); }
		public ContinueStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_continueStatement; }
	}

	public final ContinueStatementContext continueStatement() throws RecognitionException {
		ContinueStatementContext _localctx = new ContinueStatementContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_continueStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(204);
			match(Continue);
			setState(205);
			match(Semi);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionstatementContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode Semi() { return getToken(MxParser.Semi, 0); }
		public ExpressionstatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expressionstatement; }
	}

	public final ExpressionstatementContext expressionstatement() throws RecognitionException {
		ExpressionstatementContext _localctx = new ExpressionstatementContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_expressionstatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(207);
			expression(0);
			setState(208);
			match(Semi);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class InitexprContext extends ParserRuleContext {
		public InitexprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_initexpr; }
	 
		public InitexprContext() { }
		public void copyFrom(InitexprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NewVarExprContext extends InitexprContext {
		public TerminalNode New() { return getToken(MxParser.New, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public TerminalNode LParen() { return getToken(MxParser.LParen, 0); }
		public TerminalNode RParen() { return getToken(MxParser.RParen, 0); }
		public NewVarExprContext(InitexprContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NewArrayExprContext extends InitexprContext {
		public TerminalNode New() { return getToken(MxParser.New, 0); }
		public TypeContext type() {
			return getRuleContext(TypeContext.class,0);
		}
		public List<TerminalNode> Lbracket() { return getTokens(MxParser.Lbracket); }
		public TerminalNode Lbracket(int i) {
			return getToken(MxParser.Lbracket, i);
		}
		public List<TerminalNode> Rbracket() { return getTokens(MxParser.Rbracket); }
		public TerminalNode Rbracket(int i) {
			return getToken(MxParser.Rbracket, i);
		}
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public NewArrayExprContext(InitexprContext ctx) { copyFrom(ctx); }
	}

	public final InitexprContext initexpr() throws RecognitionException {
		InitexprContext _localctx = new InitexprContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_initexpr);
		int _la;
		try {
			int _alt;
			setState(228);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				_localctx = new NewArrayExprContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(210);
				match(New);
				setState(211);
				type();
				setState(219);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(212);
						match(Lbracket);
						setState(214);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2846308499750666244L) != 0)) {
							{
							setState(213);
							expression(0);
							}
						}

						setState(216);
						match(Rbracket);
						}
						} 
					}
					setState(221);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,24,_ctx);
				}
				}
				break;
			case 2:
				_localctx = new NewVarExprContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(222);
				match(New);
				setState(223);
				type();
				setState(226);
				_errHandler.sync(this);
				switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
				case 1:
					{
					setState(224);
					match(LParen);
					setState(225);
					match(RParen);
					}
					break;
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NewExprContext extends ExpressionContext {
		public InitexprContext initexpr() {
			return getRuleContext(InitexprContext.class,0);
		}
		public NewExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class UnaryExprContext extends ExpressionContext {
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode Selfadd() { return getToken(MxParser.Selfadd, 0); }
		public TerminalNode Selfsub() { return getToken(MxParser.Selfsub, 0); }
		public TerminalNode Not() { return getToken(MxParser.Not, 0); }
		public TerminalNode LogicNot() { return getToken(MxParser.LogicNot, 0); }
		public TerminalNode Sub() { return getToken(MxParser.Sub, 0); }
		public UnaryExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ArrayExprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode Lbracket() { return getToken(MxParser.Lbracket, 0); }
		public TerminalNode Rbracket() { return getToken(MxParser.Rbracket, 0); }
		public ArrayExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MemberExprContext extends ExpressionContext {
		public Token op;
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public TerminalNode Member() { return getToken(MxParser.Member, 0); }
		public MemberExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AtomExprContext extends ExpressionContext {
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public AtomExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class BinaryExprContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode Mul() { return getToken(MxParser.Mul, 0); }
		public TerminalNode Div() { return getToken(MxParser.Div, 0); }
		public TerminalNode Mod() { return getToken(MxParser.Mod, 0); }
		public TerminalNode Add() { return getToken(MxParser.Add, 0); }
		public TerminalNode Sub() { return getToken(MxParser.Sub, 0); }
		public TerminalNode LeftShift() { return getToken(MxParser.LeftShift, 0); }
		public TerminalNode RightShift() { return getToken(MxParser.RightShift, 0); }
		public TerminalNode Less() { return getToken(MxParser.Less, 0); }
		public TerminalNode Greater() { return getToken(MxParser.Greater, 0); }
		public TerminalNode LessEqual() { return getToken(MxParser.LessEqual, 0); }
		public TerminalNode GreaterEqual() { return getToken(MxParser.GreaterEqual, 0); }
		public TerminalNode Equal() { return getToken(MxParser.Equal, 0); }
		public TerminalNode UnEqual() { return getToken(MxParser.UnEqual, 0); }
		public TerminalNode And() { return getToken(MxParser.And, 0); }
		public TerminalNode Xor() { return getToken(MxParser.Xor, 0); }
		public TerminalNode Or() { return getToken(MxParser.Or, 0); }
		public TerminalNode LogicAnd() { return getToken(MxParser.LogicAnd, 0); }
		public TerminalNode LogicOr() { return getToken(MxParser.LogicOr, 0); }
		public BinaryExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class CallExprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode LParen() { return getToken(MxParser.LParen, 0); }
		public TerminalNode RParen() { return getToken(MxParser.RParen, 0); }
		public List<TerminalNode> Comma() { return getTokens(MxParser.Comma); }
		public TerminalNode Comma(int i) {
			return getToken(MxParser.Comma, i);
		}
		public CallExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AssignExprContext extends ExpressionContext {
		public Token op;
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode Assign() { return getToken(MxParser.Assign, 0); }
		public AssignExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenExprContext extends ExpressionContext {
		public TerminalNode LParen() { return getToken(MxParser.LParen, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RParen() { return getToken(MxParser.RParen, 0); }
		public ParenExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ConditionalExprContext extends ExpressionContext {
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode Question() { return getToken(MxParser.Question, 0); }
		public TerminalNode Colon() { return getToken(MxParser.Colon, 0); }
		public ConditionalExprContext(ExpressionContext ctx) { copyFrom(ctx); }
	}

	public final ExpressionContext expression() throws RecognitionException {
		return expression(0);
	}

	private ExpressionContext expression(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExpressionContext _localctx = new ExpressionContext(_ctx, _parentState);
		ExpressionContext _prevctx = _localctx;
		int _startState = 38;
		enterRecursionRule(_localctx, 38, RULE_expression, _p);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(239);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case New:
				{
				_localctx = new NewExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(231);
				initexpr();
				}
				break;
			case LParen:
				{
				_localctx = new ParenExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(232);
				match(LParen);
				setState(233);
				expression(0);
				setState(234);
				match(RParen);
				}
				break;
			case Sub:
			case LogicNot:
			case Not:
			case Selfadd:
			case Selfsub:
				{
				_localctx = new UnaryExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(236);
				((UnaryExprContext)_localctx).op = _input.LT(1);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 13647876L) != 0)) ) {
					((UnaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(237);
				expression(11);
				}
				break;
			case Null:
			case True:
			case False:
			case This:
			case Identifier:
			case Integer:
			case StringLiteral:
			case FomatStringL:
			case FStringLiteral:
				{
				_localctx = new AtomExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(238);
				atom();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(297);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(295);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,31,_ctx) ) {
					case 1:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(241);
						if (!(precpred(_ctx, 10))) throw new FailedPredicateException(this, "precpred(_ctx, 10)");
						setState(242);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 56L) != 0)) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(243);
						expression(11);
						}
						break;
					case 2:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(244);
						if (!(precpred(_ctx, 9))) throw new FailedPredicateException(this, "precpred(_ctx, 9)");
						setState(245);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==Add || _la==Sub) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(246);
						expression(10);
						}
						break;
					case 3:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(247);
						if (!(precpred(_ctx, 8))) throw new FailedPredicateException(this, "precpred(_ctx, 8)");
						setState(248);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==RightShift || _la==LeftShift) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(249);
						expression(9);
						}
						break;
					case 4:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(250);
						if (!(precpred(_ctx, 7))) throw new FailedPredicateException(this, "precpred(_ctx, 7)");
						setState(251);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 960L) != 0)) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(252);
						expression(8);
						}
						break;
					case 5:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(253);
						if (!(precpred(_ctx, 6))) throw new FailedPredicateException(this, "precpred(_ctx, 6)");
						setState(254);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==UnEqual || _la==Equal) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(255);
						expression(7);
						}
						break;
					case 6:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(256);
						if (!(precpred(_ctx, 5))) throw new FailedPredicateException(this, "precpred(_ctx, 5)");
						setState(257);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 917504L) != 0)) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(258);
						expression(6);
						}
						break;
					case 7:
						{
						_localctx = new BinaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(259);
						if (!(precpred(_ctx, 4))) throw new FailedPredicateException(this, "precpred(_ctx, 4)");
						setState(260);
						((BinaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==LogicAnd || _la==LogicOr) ) {
							((BinaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						setState(261);
						expression(5);
						}
						break;
					case 8:
						{
						_localctx = new ConditionalExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(262);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(263);
						match(Question);
						setState(264);
						expression(0);
						setState(265);
						match(Colon);
						setState(266);
						expression(3);
						}
						break;
					case 9:
						{
						_localctx = new AssignExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(268);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(269);
						((AssignExprContext)_localctx).op = match(Assign);
						setState(270);
						expression(2);
						}
						break;
					case 10:
						{
						_localctx = new CallExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(271);
						if (!(precpred(_ctx, 15))) throw new FailedPredicateException(this, "precpred(_ctx, 15)");
						setState(272);
						match(LParen);
						setState(281);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2846308499750666244L) != 0)) {
							{
							setState(273);
							expression(0);
							setState(278);
							_errHandler.sync(this);
							_la = _input.LA(1);
							while (_la==Comma) {
								{
								{
								setState(274);
								match(Comma);
								setState(275);
								expression(0);
								}
								}
								setState(280);
								_errHandler.sync(this);
								_la = _input.LA(1);
							}
							}
						}

						setState(283);
						match(RParen);
						}
						break;
					case 11:
						{
						_localctx = new MemberExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(284);
						if (!(precpred(_ctx, 14))) throw new FailedPredicateException(this, "precpred(_ctx, 14)");
						setState(285);
						((MemberExprContext)_localctx).op = match(Member);
						setState(286);
						match(Identifier);
						}
						break;
					case 12:
						{
						_localctx = new ArrayExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(287);
						if (!(precpred(_ctx, 13))) throw new FailedPredicateException(this, "precpred(_ctx, 13)");
						setState(288);
						match(Lbracket);
						setState(290);
						_errHandler.sync(this);
						_la = _input.LA(1);
						if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 2846308499750666244L) != 0)) {
							{
							setState(289);
							expression(0);
							}
						}

						setState(292);
						match(Rbracket);
						}
						break;
					case 13:
						{
						_localctx = new UnaryExprContext(new ExpressionContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expression);
						setState(293);
						if (!(precpred(_ctx, 12))) throw new FailedPredicateException(this, "precpred(_ctx, 12)");
						setState(294);
						((UnaryExprContext)_localctx).op = _input.LT(1);
						_la = _input.LA(1);
						if ( !(_la==Selfadd || _la==Selfsub) ) {
							((UnaryExprContext)_localctx).op = (Token)_errHandler.recoverInline(this);
						}
						else {
							if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
							_errHandler.reportMatch(this);
							consume();
						}
						}
						break;
					}
					} 
				}
				setState(299);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,32,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AtomContext extends ParserRuleContext {
		public TerminalNode Integer() { return getToken(MxParser.Integer, 0); }
		public FstringContext fstring() {
			return getRuleContext(FstringContext.class,0);
		}
		public TerminalNode True() { return getToken(MxParser.True, 0); }
		public TerminalNode False() { return getToken(MxParser.False, 0); }
		public TerminalNode This() { return getToken(MxParser.This, 0); }
		public TerminalNode Null() { return getToken(MxParser.Null, 0); }
		public TerminalNode Identifier() { return getToken(MxParser.Identifier, 0); }
		public TerminalNode StringLiteral() { return getToken(MxParser.StringLiteral, 0); }
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_atom);
		try {
			setState(308);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Integer:
				enterOuterAlt(_localctx, 1);
				{
				setState(300);
				match(Integer);
				}
				break;
			case FomatStringL:
			case FStringLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(301);
				fstring();
				}
				break;
			case True:
				enterOuterAlt(_localctx, 3);
				{
				setState(302);
				match(True);
				}
				break;
			case False:
				enterOuterAlt(_localctx, 4);
				{
				setState(303);
				match(False);
				}
				break;
			case This:
				enterOuterAlt(_localctx, 5);
				{
				setState(304);
				match(This);
				}
				break;
			case Null:
				enterOuterAlt(_localctx, 6);
				{
				setState(305);
				match(Null);
				}
				break;
			case Identifier:
				enterOuterAlt(_localctx, 7);
				{
				setState(306);
				match(Identifier);
				}
				break;
			case StringLiteral:
				enterOuterAlt(_localctx, 8);
				{
				setState(307);
				match(StringLiteral);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FstringContext extends ParserRuleContext {
		public TerminalNode FomatStringL() { return getToken(MxParser.FomatStringL, 0); }
		public List<ExpressionContext> expression() {
			return getRuleContexts(ExpressionContext.class);
		}
		public ExpressionContext expression(int i) {
			return getRuleContext(ExpressionContext.class,i);
		}
		public TerminalNode FomatStringR() { return getToken(MxParser.FomatStringR, 0); }
		public List<TerminalNode> FomatStringM() { return getTokens(MxParser.FomatStringM); }
		public TerminalNode FomatStringM(int i) {
			return getToken(MxParser.FomatStringM, i);
		}
		public TerminalNode FStringLiteral() { return getToken(MxParser.FStringLiteral, 0); }
		public FstringContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fstring; }
	}

	public final FstringContext fstring() throws RecognitionException {
		FstringContext _localctx = new FstringContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_fstring);
		int _la;
		try {
			setState(322);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FomatStringL:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(310);
				match(FomatStringL);
				setState(311);
				expression(0);
				setState(316);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==FomatStringM) {
					{
					{
					setState(312);
					match(FomatStringM);
					setState(313);
					expression(0);
					}
					}
					setState(318);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(319);
				match(FomatStringR);
				}
				}
				break;
			case FStringLiteral:
				enterOuterAlt(_localctx, 2);
				{
				setState(321);
				match(FStringLiteral);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 19:
			return expression_sempred((ExpressionContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expression_sempred(ExpressionContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 10);
		case 1:
			return precpred(_ctx, 9);
		case 2:
			return precpred(_ctx, 8);
		case 3:
			return precpred(_ctx, 7);
		case 4:
			return precpred(_ctx, 6);
		case 5:
			return precpred(_ctx, 5);
		case 6:
			return precpred(_ctx, 4);
		case 7:
			return precpred(_ctx, 3);
		case 8:
			return precpred(_ctx, 2);
		case 9:
			return precpred(_ctx, 15);
		case 10:
			return precpred(_ctx, 14);
		case 11:
			return precpred(_ctx, 13);
		case 12:
			return precpred(_ctx, 12);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001=\u0145\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007\u0015"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u00000\b\u0000\n\u0000\f\u0000"+
		"3\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0005\u0002"+
		"?\b\u0002\n\u0002\f\u0002B\t\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0005\u0003J\b\u0003\n\u0003\f\u0003"+
		"M\t\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0005\u0004"+
		"S\b\u0004\n\u0004\f\u0004V\t\u0004\u0001\u0004\u0001\u0004\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0003\u0005]\b\u0005\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0005\u0007g\b\u0007\n\u0007\f\u0007j\t\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0005\u0007o\b\u0007\n\u0007\f\u0007r\t\u0007\u0003\u0007"+
		"t\b\u0007\u0001\b\u0001\b\u0001\b\u0001\b\u0003\bz\b\b\u0001\b\u0001\b"+
		"\u0001\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0005\t\u0085"+
		"\b\t\n\t\f\t\u0088\t\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n"+
		"\u0001\n\u0001\n\u0001\n\u0003\n\u0093\b\n\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u009c"+
		"\b\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u00a2"+
		"\b\u000b\u0003\u000b\u00a4\b\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0003\f\u00ad\b\f\u0001\r\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0003\r\u00b4\b\r\u0001\r\u0001\r\u0003\r\u00b8\b\r\u0001\r\u0001\r"+
		"\u0003\r\u00bc\b\r\u0001\r\u0001\r\u0001\r\u0001\r\u0003\r\u00c2\b\r\u0001"+
		"\u000e\u0001\u000e\u0003\u000e\u00c6\b\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0003\u0012\u00d7\b\u0012\u0001\u0012\u0005\u0012\u00da\b\u0012"+
		"\n\u0012\f\u0012\u00dd\t\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0003\u0012\u00e3\b\u0012\u0003\u0012\u00e5\b\u0012\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0003\u0013\u00f0\b\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0005\u0013\u0115\b\u0013\n\u0013"+
		"\f\u0013\u0118\t\u0013\u0003\u0013\u011a\b\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0003\u0013"+
		"\u0123\b\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0005\u0013\u0128\b"+
		"\u0013\n\u0013\f\u0013\u012b\t\u0013\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0003\u0014"+
		"\u0135\b\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0005\u0015"+
		"\u013b\b\u0015\n\u0015\f\u0015\u013e\t\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0003\u0015\u0143\b\u0015\u0001\u0015\u0000\u0001&\u0016\u0000"+
		"\u0002\u0004\u0006\b\n\f\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c"+
		"\u001e \"$&(*\u0000\n\u0002\u0000#&77\u0004\u0000\u0002\u0002\u000e\u000e"+
		"\u0014\u0014\u0016\u0017\u0001\u0000\u0003\u0005\u0001\u0000\u0001\u0002"+
		"\u0001\u0000\u000f\u0010\u0001\u0000\u0006\t\u0001\u0000\n\u000b\u0001"+
		"\u0000\u0011\u0013\u0001\u0000\f\r\u0001\u0000\u0016\u0017\u0174\u0000"+
		"1\u0001\u0000\u0000\u0000\u00026\u0001\u0000\u0000\u0000\u00048\u0001"+
		"\u0000\u0000\u0000\u0006F\u0001\u0000\u0000\u0000\bN\u0001\u0000\u0000"+
		"\u0000\nY\u0001\u0000\u0000\u0000\f^\u0001\u0000\u0000\u0000\u000es\u0001"+
		"\u0000\u0000\u0000\u0010u\u0001\u0000\u0000\u0000\u0012~\u0001\u0000\u0000"+
		"\u0000\u0014\u0092\u0001\u0000\u0000\u0000\u0016\u0094\u0001\u0000\u0000"+
		"\u0000\u0018\u00a5\u0001\u0000\u0000\u0000\u001a\u00ae\u0001\u0000\u0000"+
		"\u0000\u001c\u00c3\u0001\u0000\u0000\u0000\u001e\u00c9\u0001\u0000\u0000"+
		"\u0000 \u00cc\u0001\u0000\u0000\u0000\"\u00cf\u0001\u0000\u0000\u0000"+
		"$\u00e4\u0001\u0000\u0000\u0000&\u00ef\u0001\u0000\u0000\u0000(\u0134"+
		"\u0001\u0000\u0000\u0000*\u0142\u0001\u0000\u0000\u0000,0\u0003\u0010"+
		"\b\u0000-0\u0003\u0004\u0002\u0000.0\u0003\b\u0004\u0000/,\u0001\u0000"+
		"\u0000\u0000/-\u0001\u0000\u0000\u0000/.\u0001\u0000\u0000\u000003\u0001"+
		"\u0000\u0000\u00001/\u0001\u0000\u0000\u000012\u0001\u0000\u0000\u0000"+
		"24\u0001\u0000\u0000\u000031\u0001\u0000\u0000\u000045\u0005\u0000\u0000"+
		"\u00015\u0001\u0001\u0000\u0000\u000067\u0007\u0000\u0000\u00007\u0003"+
		"\u0001\u0000\u0000\u000089\u0005(\u0000\u00009:\u00057\u0000\u0000:@\u0005"+
		"!\u0000\u0000;?\u0003\b\u0004\u0000<?\u0003\f\u0006\u0000=?\u0003\u0010"+
		"\b\u0000>;\u0001\u0000\u0000\u0000><\u0001\u0000\u0000\u0000>=\u0001\u0000"+
		"\u0000\u0000?B\u0001\u0000\u0000\u0000@>\u0001\u0000\u0000\u0000@A\u0001"+
		"\u0000\u0000\u0000AC\u0001\u0000\u0000\u0000B@\u0001\u0000\u0000\u0000"+
		"CD\u0005\"\u0000\u0000DE\u0005\u001f\u0000\u0000E\u0005\u0001\u0000\u0000"+
		"\u0000FK\u0003\u0002\u0001\u0000GH\u0005\u0019\u0000\u0000HJ\u0005\u001a"+
		"\u0000\u0000IG\u0001\u0000\u0000\u0000JM\u0001\u0000\u0000\u0000KI\u0001"+
		"\u0000\u0000\u0000KL\u0001\u0000\u0000\u0000L\u0007\u0001\u0000\u0000"+
		"\u0000MK\u0001\u0000\u0000\u0000NO\u0003\u0006\u0003\u0000OT\u0003\n\u0005"+
		"\u0000PQ\u0005 \u0000\u0000QS\u0003\n\u0005\u0000RP\u0001\u0000\u0000"+
		"\u0000SV\u0001\u0000\u0000\u0000TR\u0001\u0000\u0000\u0000TU\u0001\u0000"+
		"\u0000\u0000UW\u0001\u0000\u0000\u0000VT\u0001\u0000\u0000\u0000WX\u0005"+
		"\u001f\u0000\u0000X\t\u0001\u0000\u0000\u0000Y\\\u00057\u0000\u0000Z["+
		"\u0005\u0015\u0000\u0000[]\u0003&\u0013\u0000\\Z\u0001\u0000\u0000\u0000"+
		"\\]\u0001\u0000\u0000\u0000]\u000b\u0001\u0000\u0000\u0000^_\u00057\u0000"+
		"\u0000_`\u0005\u001b\u0000\u0000`a\u0005\u001c\u0000\u0000ab\u0003\u000e"+
		"\u0007\u0000b\r\u0001\u0000\u0000\u0000ch\u0005!\u0000\u0000dg\u0003\u000e"+
		"\u0007\u0000eg\u0003\u0014\n\u0000fd\u0001\u0000\u0000\u0000fe\u0001\u0000"+
		"\u0000\u0000gj\u0001\u0000\u0000\u0000hf\u0001\u0000\u0000\u0000hi\u0001"+
		"\u0000\u0000\u0000ik\u0001\u0000\u0000\u0000jh\u0001\u0000\u0000\u0000"+
		"kt\u0005\"\u0000\u0000lp\u0005\u001f\u0000\u0000mo\u0005\u001f\u0000\u0000"+
		"nm\u0001\u0000\u0000\u0000or\u0001\u0000\u0000\u0000pn\u0001\u0000\u0000"+
		"\u0000pq\u0001\u0000\u0000\u0000qt\u0001\u0000\u0000\u0000rp\u0001\u0000"+
		"\u0000\u0000sc\u0001\u0000\u0000\u0000sl\u0001\u0000\u0000\u0000t\u000f"+
		"\u0001\u0000\u0000\u0000uv\u0003\u0006\u0003\u0000vw\u00057\u0000\u0000"+
		"wy\u0005\u001b\u0000\u0000xz\u0003\u0012\t\u0000yx\u0001\u0000\u0000\u0000"+
		"yz\u0001\u0000\u0000\u0000z{\u0001\u0000\u0000\u0000{|\u0005\u001c\u0000"+
		"\u0000|}\u0003\u000e\u0007\u0000}\u0011\u0001\u0000\u0000\u0000~\u007f"+
		"\u0003\u0006\u0003\u0000\u007f\u0086\u00057\u0000\u0000\u0080\u0081\u0005"+
		" \u0000\u0000\u0081\u0082\u0003\u0006\u0003\u0000\u0082\u0083\u00057\u0000"+
		"\u0000\u0083\u0085\u0001\u0000\u0000\u0000\u0084\u0080\u0001\u0000\u0000"+
		"\u0000\u0085\u0088\u0001\u0000\u0000\u0000\u0086\u0084\u0001\u0000\u0000"+
		"\u0000\u0086\u0087\u0001\u0000\u0000\u0000\u0087\u0013\u0001\u0000\u0000"+
		"\u0000\u0088\u0086\u0001\u0000\u0000\u0000\u0089\u0093\u0003\b\u0004\u0000"+
		"\u008a\u0093\u0003\u0016\u000b\u0000\u008b\u0093\u0003\u0018\f\u0000\u008c"+
		"\u0093\u0003\u001a\r\u0000\u008d\u0093\u0003\u001c\u000e\u0000\u008e\u0093"+
		"\u0003\u001e\u000f\u0000\u008f\u0093\u0003 \u0010\u0000\u0090\u0093\u0003"+
		"\"\u0011\u0000\u0091\u0093\u0005\u001f\u0000\u0000\u0092\u0089\u0001\u0000"+
		"\u0000\u0000\u0092\u008a\u0001\u0000\u0000\u0000\u0092\u008b\u0001\u0000"+
		"\u0000\u0000\u0092\u008c\u0001\u0000\u0000\u0000\u0092\u008d\u0001\u0000"+
		"\u0000\u0000\u0092\u008e\u0001\u0000\u0000\u0000\u0092\u008f\u0001\u0000"+
		"\u0000\u0000\u0092\u0090\u0001\u0000\u0000\u0000\u0092\u0091\u0001\u0000"+
		"\u0000\u0000\u0093\u0015\u0001\u0000\u0000\u0000\u0094\u0095\u0005-\u0000"+
		"\u0000\u0095\u0096\u0005\u001b\u0000\u0000\u0096\u0097\u0003&\u0013\u0000"+
		"\u0097\u009b\u0005\u001c\u0000\u0000\u0098\u009c\u0003\u000e\u0007\u0000"+
		"\u0099\u009c\u0003\u0014\n\u0000\u009a\u009c\u0005\u001f\u0000\u0000\u009b"+
		"\u0098\u0001\u0000\u0000\u0000\u009b\u0099\u0001\u0000\u0000\u0000\u009b"+
		"\u009a\u0001\u0000\u0000\u0000\u009c\u00a3\u0001\u0000\u0000\u0000\u009d"+
		"\u00a1\u0005.\u0000\u0000\u009e\u00a2\u0003\u000e\u0007\u0000\u009f\u00a2"+
		"\u0003\u0014\n\u0000\u00a0\u00a2\u0005\u001f\u0000\u0000\u00a1\u009e\u0001"+
		"\u0000\u0000\u0000\u00a1\u009f\u0001\u0000\u0000\u0000\u00a1\u00a0\u0001"+
		"\u0000\u0000\u0000\u00a2\u00a4\u0001\u0000\u0000\u0000\u00a3\u009d\u0001"+
		"\u0000\u0000\u0000\u00a3\u00a4\u0001\u0000\u0000\u0000\u00a4\u0017\u0001"+
		"\u0000\u0000\u0000\u00a5\u00a6\u00050\u0000\u0000\u00a6\u00a7\u0005\u001b"+
		"\u0000\u0000\u00a7\u00a8\u0003&\u0013\u0000\u00a8\u00ac\u0005\u001c\u0000"+
		"\u0000\u00a9\u00ad\u0003\u000e\u0007\u0000\u00aa\u00ad\u0003\u0014\n\u0000"+
		"\u00ab\u00ad\u0005\u001f\u0000\u0000\u00ac\u00a9\u0001\u0000\u0000\u0000"+
		"\u00ac\u00aa\u0001\u0000\u0000\u0000\u00ac\u00ab\u0001\u0000\u0000\u0000"+
		"\u00ad\u0019\u0001\u0000\u0000\u0000\u00ae\u00af\u0005/\u0000\u0000\u00af"+
		"\u00b3\u0005\u001b\u0000\u0000\u00b0\u00b4\u0003&\u0013\u0000\u00b1\u00b4"+
		"\u0003\b\u0004\u0000\u00b2\u00b4\u0005\u001f\u0000\u0000\u00b3\u00b0\u0001"+
		"\u0000\u0000\u0000\u00b3\u00b1\u0001\u0000\u0000\u0000\u00b3\u00b2\u0001"+
		"\u0000\u0000\u0000\u00b3\u00b4\u0001\u0000\u0000\u0000\u00b4\u00b5\u0001"+
		"\u0000\u0000\u0000\u00b5\u00b7\u0005\u001f\u0000\u0000\u00b6\u00b8\u0003"+
		"&\u0013\u0000\u00b7\u00b6\u0001\u0000\u0000\u0000\u00b7\u00b8\u0001\u0000"+
		"\u0000\u0000\u00b8\u00b9\u0001\u0000\u0000\u0000\u00b9\u00bb\u0005\u001f"+
		"\u0000\u0000\u00ba\u00bc\u0003&\u0013\u0000\u00bb\u00ba\u0001\u0000\u0000"+
		"\u0000\u00bb\u00bc\u0001\u0000\u0000\u0000\u00bc\u00bd\u0001\u0000\u0000"+
		"\u0000\u00bd\u00c1\u0005\u001c\u0000\u0000\u00be\u00c2\u0003\u000e\u0007"+
		"\u0000\u00bf\u00c2\u0003\u0014\n\u0000\u00c0\u00c2\u0005\u001f\u0000\u0000"+
		"\u00c1\u00be\u0001\u0000\u0000\u0000\u00c1\u00bf\u0001\u0000\u0000\u0000"+
		"\u00c1\u00c0\u0001\u0000\u0000\u0000\u00c2\u001b\u0001\u0000\u0000\u0000"+
		"\u00c3\u00c5\u00053\u0000\u0000\u00c4\u00c6\u0003&\u0013\u0000\u00c5\u00c4"+
		"\u0001\u0000\u0000\u0000\u00c5\u00c6\u0001\u0000\u0000\u0000\u00c6\u00c7"+
		"\u0001\u0000\u0000\u0000\u00c7\u00c8\u0005\u001f\u0000\u0000\u00c8\u001d"+
		"\u0001\u0000\u0000\u0000\u00c9\u00ca\u00051\u0000\u0000\u00ca\u00cb\u0005"+
		"\u001f\u0000\u0000\u00cb\u001f\u0001\u0000\u0000\u0000\u00cc\u00cd\u0005"+
		"2\u0000\u0000\u00cd\u00ce\u0005\u001f\u0000\u0000\u00ce!\u0001\u0000\u0000"+
		"\u0000\u00cf\u00d0\u0003&\u0013\u0000\u00d0\u00d1\u0005\u001f\u0000\u0000"+
		"\u00d1#\u0001\u0000\u0000\u0000\u00d2\u00d3\u0005\'\u0000\u0000\u00d3"+
		"\u00db\u0003\u0002\u0001\u0000\u00d4\u00d6\u0005\u0019\u0000\u0000\u00d5"+
		"\u00d7\u0003&\u0013\u0000\u00d6\u00d5\u0001\u0000\u0000\u0000\u00d6\u00d7"+
		"\u0001\u0000\u0000\u0000\u00d7\u00d8\u0001\u0000\u0000\u0000\u00d8\u00da"+
		"\u0005\u001a\u0000\u0000\u00d9\u00d4\u0001\u0000\u0000\u0000\u00da\u00dd"+
		"\u0001\u0000\u0000\u0000\u00db\u00d9\u0001\u0000\u0000\u0000\u00db\u00dc"+
		"\u0001\u0000\u0000\u0000\u00dc\u00e5\u0001\u0000\u0000\u0000\u00dd\u00db"+
		"\u0001\u0000\u0000\u0000\u00de\u00df\u0005\'\u0000\u0000\u00df\u00e2\u0003"+
		"\u0002\u0001\u0000\u00e0\u00e1\u0005\u001b\u0000\u0000\u00e1\u00e3\u0005"+
		"\u001c\u0000\u0000\u00e2\u00e0\u0001\u0000\u0000\u0000\u00e2\u00e3\u0001"+
		"\u0000\u0000\u0000\u00e3\u00e5\u0001\u0000\u0000\u0000\u00e4\u00d2\u0001"+
		"\u0000\u0000\u0000\u00e4\u00de\u0001\u0000\u0000\u0000\u00e5%\u0001\u0000"+
		"\u0000\u0000\u00e6\u00e7\u0006\u0013\uffff\uffff\u0000\u00e7\u00f0\u0003"+
		"$\u0012\u0000\u00e8\u00e9\u0005\u001b\u0000\u0000\u00e9\u00ea\u0003&\u0013"+
		"\u0000\u00ea\u00eb\u0005\u001c\u0000\u0000\u00eb\u00f0\u0001\u0000\u0000"+
		"\u0000\u00ec\u00ed\u0007\u0001\u0000\u0000\u00ed\u00f0\u0003&\u0013\u000b"+
		"\u00ee\u00f0\u0003(\u0014\u0000\u00ef\u00e6\u0001\u0000\u0000\u0000\u00ef"+
		"\u00e8\u0001\u0000\u0000\u0000\u00ef\u00ec\u0001\u0000\u0000\u0000\u00ef"+
		"\u00ee\u0001\u0000\u0000\u0000\u00f0\u0129\u0001\u0000\u0000\u0000\u00f1"+
		"\u00f2\n\n\u0000\u0000\u00f2\u00f3\u0007\u0002\u0000\u0000\u00f3\u0128"+
		"\u0003&\u0013\u000b\u00f4\u00f5\n\t\u0000\u0000\u00f5\u00f6\u0007\u0003"+
		"\u0000\u0000\u00f6\u0128\u0003&\u0013\n\u00f7\u00f8\n\b\u0000\u0000\u00f8"+
		"\u00f9\u0007\u0004\u0000\u0000\u00f9\u0128\u0003&\u0013\t\u00fa\u00fb"+
		"\n\u0007\u0000\u0000\u00fb\u00fc\u0007\u0005\u0000\u0000\u00fc\u0128\u0003"+
		"&\u0013\b\u00fd\u00fe\n\u0006\u0000\u0000\u00fe\u00ff\u0007\u0006\u0000"+
		"\u0000\u00ff\u0128\u0003&\u0013\u0007\u0100\u0101\n\u0005\u0000\u0000"+
		"\u0101\u0102\u0007\u0007\u0000\u0000\u0102\u0128\u0003&\u0013\u0006\u0103"+
		"\u0104\n\u0004\u0000\u0000\u0104\u0105\u0007\b\u0000\u0000\u0105\u0128"+
		"\u0003&\u0013\u0005\u0106\u0107\n\u0003\u0000\u0000\u0107\u0108\u0005"+
		"\u001d\u0000\u0000\u0108\u0109\u0003&\u0013\u0000\u0109\u010a\u0005\u001e"+
		"\u0000\u0000\u010a\u010b\u0003&\u0013\u0003\u010b\u0128\u0001\u0000\u0000"+
		"\u0000\u010c\u010d\n\u0002\u0000\u0000\u010d\u010e\u0005\u0015\u0000\u0000"+
		"\u010e\u0128\u0003&\u0013\u0002\u010f\u0110\n\u000f\u0000\u0000\u0110"+
		"\u0119\u0005\u001b\u0000\u0000\u0111\u0116\u0003&\u0013\u0000\u0112\u0113"+
		"\u0005 \u0000\u0000\u0113\u0115\u0003&\u0013\u0000\u0114\u0112\u0001\u0000"+
		"\u0000\u0000\u0115\u0118\u0001\u0000\u0000\u0000\u0116\u0114\u0001\u0000"+
		"\u0000\u0000\u0116\u0117\u0001\u0000\u0000\u0000\u0117\u011a\u0001\u0000"+
		"\u0000\u0000\u0118\u0116\u0001\u0000\u0000\u0000\u0119\u0111\u0001\u0000"+
		"\u0000\u0000\u0119\u011a\u0001\u0000\u0000\u0000\u011a\u011b\u0001\u0000"+
		"\u0000\u0000\u011b\u0128\u0005\u001c\u0000\u0000\u011c\u011d\n\u000e\u0000"+
		"\u0000\u011d\u011e\u0005\u0018\u0000\u0000\u011e\u0128\u00057\u0000\u0000"+
		"\u011f\u0120\n\r\u0000\u0000\u0120\u0122\u0005\u0019\u0000\u0000\u0121"+
		"\u0123\u0003&\u0013\u0000\u0122\u0121\u0001\u0000\u0000\u0000\u0122\u0123"+
		"\u0001\u0000\u0000\u0000\u0123\u0124\u0001\u0000\u0000\u0000\u0124\u0128"+
		"\u0005\u001a\u0000\u0000\u0125\u0126\n\f\u0000\u0000\u0126\u0128\u0007"+
		"\t\u0000\u0000\u0127\u00f1\u0001\u0000\u0000\u0000\u0127\u00f4\u0001\u0000"+
		"\u0000\u0000\u0127\u00f7\u0001\u0000\u0000\u0000\u0127\u00fa\u0001\u0000"+
		"\u0000\u0000\u0127\u00fd\u0001\u0000\u0000\u0000\u0127\u0100\u0001\u0000"+
		"\u0000\u0000\u0127\u0103\u0001\u0000\u0000\u0000\u0127\u0106\u0001\u0000"+
		"\u0000\u0000\u0127\u010c\u0001\u0000\u0000\u0000\u0127\u010f\u0001\u0000"+
		"\u0000\u0000\u0127\u011c\u0001\u0000\u0000\u0000\u0127\u011f\u0001\u0000"+
		"\u0000\u0000\u0127\u0125\u0001\u0000\u0000\u0000\u0128\u012b\u0001\u0000"+
		"\u0000\u0000\u0129\u0127\u0001\u0000\u0000\u0000\u0129\u012a\u0001\u0000"+
		"\u0000\u0000\u012a\'\u0001\u0000\u0000\u0000\u012b\u0129\u0001\u0000\u0000"+
		"\u0000\u012c\u0135\u00058\u0000\u0000\u012d\u0135\u0003*\u0015\u0000\u012e"+
		"\u0135\u0005*\u0000\u0000\u012f\u0135\u0005+\u0000\u0000\u0130\u0135\u0005"+
		",\u0000\u0000\u0131\u0135\u0005)\u0000\u0000\u0132\u0135\u00057\u0000"+
		"\u0000\u0133\u0135\u00059\u0000\u0000\u0134\u012c\u0001\u0000\u0000\u0000"+
		"\u0134\u012d\u0001\u0000\u0000\u0000\u0134\u012e\u0001\u0000\u0000\u0000"+
		"\u0134\u012f\u0001\u0000\u0000\u0000\u0134\u0130\u0001\u0000\u0000\u0000"+
		"\u0134\u0131\u0001\u0000\u0000\u0000\u0134\u0132\u0001\u0000\u0000\u0000"+
		"\u0134\u0133\u0001\u0000\u0000\u0000\u0135)\u0001\u0000\u0000\u0000\u0136"+
		"\u0137\u0005:\u0000\u0000\u0137\u013c\u0003&\u0013\u0000\u0138\u0139\u0005"+
		"<\u0000\u0000\u0139\u013b\u0003&\u0013\u0000\u013a\u0138\u0001\u0000\u0000"+
		"\u0000\u013b\u013e\u0001\u0000\u0000\u0000\u013c\u013a\u0001\u0000\u0000"+
		"\u0000\u013c\u013d\u0001\u0000\u0000\u0000\u013d\u013f\u0001\u0000\u0000"+
		"\u0000\u013e\u013c\u0001\u0000\u0000\u0000\u013f\u0140\u0005;\u0000\u0000"+
		"\u0140\u0143\u0001\u0000\u0000\u0000\u0141\u0143\u0005=\u0000\u0000\u0142"+
		"\u0136\u0001\u0000\u0000\u0000\u0142\u0141\u0001\u0000\u0000\u0000\u0143"+
		"+\u0001\u0000\u0000\u0000$/1>@KT\\fhpsy\u0086\u0092\u009b\u00a1\u00a3"+
		"\u00ac\u00b3\u00b7\u00bb\u00c1\u00c5\u00d6\u00db\u00e2\u00e4\u00ef\u0116"+
		"\u0119\u0122\u0127\u0129\u0134\u013c\u0142";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}