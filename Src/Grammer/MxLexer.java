// Generated from Mx.g4 by ANTLR 4.13.1
package Compiler.Src.Grammer;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class MxLexer extends Lexer {
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
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"Add", "Sub", "Mul", "Div", "Mod", "Greater", "Less", "GreaterEqual", 
			"LessEqual", "UnEqual", "Equal", "LogicAnd", "LogicOr", "LogicNot", "RightShift", 
			"LeftShift", "And", "Or", "Xor", "Not", "Assign", "Selfadd", "Selfsub", 
			"Member", "Lbracket", "Rbracket", "LParen", "RParen", "Question", "Colon", 
			"Semi", "Comma", "Lbrace", "Rbrace", "Void", "Bool", "Int", "String", 
			"New", "Class", "Null", "True", "False", "This", "If", "Else", "For", 
			"While", "Break", "Continue", "Return", "WhiteSpace", "LineComment", 
			"BlockComment", "Identifier", "Integer", "StringLiteral", "Stringchar", 
			"FomatStringL", "FomatStringR", "FomatStringM", "FStringLiteral", "Fomatstring"
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


	public MxLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Mx.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000=\u01a9\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002"+
		"\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002"+
		"\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002"+
		"\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002"+
		"\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007"+
		"!\u0002\"\u0007\"\u0002#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007"+
		"&\u0002\'\u0007\'\u0002(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007"+
		"+\u0002,\u0007,\u0002-\u0007-\u0002.\u0007.\u0002/\u0007/\u00020\u0007"+
		"0\u00021\u00071\u00022\u00072\u00023\u00073\u00024\u00074\u00025\u0007"+
		"5\u00026\u00076\u00027\u00077\u00028\u00078\u00029\u00079\u0002:\u0007"+
		":\u0002;\u0007;\u0002<\u0007<\u0002=\u0007=\u0002>\u0007>\u0001\u0000"+
		"\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003"+
		"\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006"+
		"\u0001\u0006\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001"+
		"\b\u0001\t\u0001\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\u000e\u0001"+
		"\u000e\u0001\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u0010\u0001"+
		"\u0010\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001\u0013\u0001"+
		"\u0013\u0001\u0014\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0016\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0018\u0001"+
		"\u0018\u0001\u0019\u0001\u0019\u0001\u001a\u0001\u001a\u0001\u001b\u0001"+
		"\u001b\u0001\u001c\u0001\u001c\u0001\u001d\u0001\u001d\u0001\u001e\u0001"+
		"\u001e\u0001\u001f\u0001\u001f\u0001 \u0001 \u0001!\u0001!\u0001\"\u0001"+
		"\"\u0001\"\u0001\"\u0001\"\u0001#\u0001#\u0001#\u0001#\u0001#\u0001$\u0001"+
		"$\u0001$\u0001$\u0001%\u0001%\u0001%\u0001%\u0001%\u0001%\u0001%\u0001"+
		"&\u0001&\u0001&\u0001&\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'"+
		"\u0001(\u0001(\u0001(\u0001(\u0001(\u0001)\u0001)\u0001)\u0001)\u0001"+
		")\u0001*\u0001*\u0001*\u0001*\u0001*\u0001*\u0001+\u0001+\u0001+\u0001"+
		"+\u0001+\u0001,\u0001,\u0001,\u0001-\u0001-\u0001-\u0001-\u0001-\u0001"+
		".\u0001.\u0001.\u0001.\u0001/\u0001/\u0001/\u0001/\u0001/\u0001/\u0001"+
		"0\u00010\u00010\u00010\u00010\u00010\u00011\u00011\u00011\u00011\u0001"+
		"1\u00011\u00011\u00011\u00011\u00012\u00012\u00012\u00012\u00012\u0001"+
		"2\u00012\u00013\u00043\u012b\b3\u000b3\f3\u012c\u00013\u00013\u00014\u0001"+
		"4\u00014\u00014\u00054\u0135\b4\n4\f4\u0138\t4\u00014\u00014\u00015\u0001"+
		"5\u00015\u00015\u00055\u0140\b5\n5\f5\u0143\t5\u00015\u00015\u00015\u0001"+
		"5\u00015\u00016\u00016\u00056\u014c\b6\n6\f6\u014f\t6\u00017\u00017\u0001"+
		"7\u00057\u0154\b7\n7\f7\u0157\t7\u00037\u0159\b7\u00018\u00018\u00058"+
		"\u015d\b8\n8\f8\u0160\t8\u00018\u00018\u00019\u00019\u00019\u00019\u0001"+
		"9\u00019\u00019\u00039\u016b\b9\u0001:\u0001:\u0001:\u0001:\u0005:\u0171"+
		"\b:\n:\f:\u0174\t:\u0001:\u0001:\u0001;\u0001;\u0005;\u017a\b;\n;\f;\u017d"+
		"\t;\u0001;\u0001;\u0001<\u0001<\u0005<\u0183\b<\n<\f<\u0186\t<\u0001<"+
		"\u0001<\u0001=\u0001=\u0001=\u0001=\u0005=\u018e\b=\n=\f=\u0191\t=\u0001"+
		"=\u0001=\u0001>\u0001>\u0001>\u0001>\u0001>\u0001>\u0001>\u0003>\u019c"+
		"\b>\u0001>\u0001>\u0001>\u0001>\u0001>\u0001>\u0001>\u0005>\u01a5\b>\n"+
		">\f>\u01a8\t>\u0004\u0141\u015e\u018f\u01a6\u0000?\u0001\u0001\u0003\u0002"+
		"\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013"+
		"\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011"+
		"#\u0012%\u0013\'\u0014)\u0015+\u0016-\u0017/\u00181\u00193\u001a5\u001b"+
		"7\u001c9\u001d;\u001e=\u001f? A!C\"E#G$I%K&M\'O(Q)S*U+W,Y-[.]/_0a1c2e"+
		"3g4i5k6m7o8q9s\u0000u:w;y<{=}\u0000\u0001\u0000\t\u0003\u0000\t\n\r\r"+
		"  \u0002\u0000\n\n\r\r\u0003\u0000AZ__az\u0004\u000009AZ__az\u0001\u0000"+
		"19\u0001\u000009\u0001\u0000 ~\u0002\u0000\"\"$$\u0003\u0000\"\"{{}}\u01bb"+
		"\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000"+
		"\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000"+
		"\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000"+
		"\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011"+
		"\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015"+
		"\u0001\u0000\u0000\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019"+
		"\u0001\u0000\u0000\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d"+
		"\u0001\u0000\u0000\u0000\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001"+
		"\u0000\u0000\u0000\u0000#\u0001\u0000\u0000\u0000\u0000%\u0001\u0000\u0000"+
		"\u0000\u0000\'\u0001\u0000\u0000\u0000\u0000)\u0001\u0000\u0000\u0000"+
		"\u0000+\u0001\u0000\u0000\u0000\u0000-\u0001\u0000\u0000\u0000\u0000/"+
		"\u0001\u0000\u0000\u0000\u00001\u0001\u0000\u0000\u0000\u00003\u0001\u0000"+
		"\u0000\u0000\u00005\u0001\u0000\u0000\u0000\u00007\u0001\u0000\u0000\u0000"+
		"\u00009\u0001\u0000\u0000\u0000\u0000;\u0001\u0000\u0000\u0000\u0000="+
		"\u0001\u0000\u0000\u0000\u0000?\u0001\u0000\u0000\u0000\u0000A\u0001\u0000"+
		"\u0000\u0000\u0000C\u0001\u0000\u0000\u0000\u0000E\u0001\u0000\u0000\u0000"+
		"\u0000G\u0001\u0000\u0000\u0000\u0000I\u0001\u0000\u0000\u0000\u0000K"+
		"\u0001\u0000\u0000\u0000\u0000M\u0001\u0000\u0000\u0000\u0000O\u0001\u0000"+
		"\u0000\u0000\u0000Q\u0001\u0000\u0000\u0000\u0000S\u0001\u0000\u0000\u0000"+
		"\u0000U\u0001\u0000\u0000\u0000\u0000W\u0001\u0000\u0000\u0000\u0000Y"+
		"\u0001\u0000\u0000\u0000\u0000[\u0001\u0000\u0000\u0000\u0000]\u0001\u0000"+
		"\u0000\u0000\u0000_\u0001\u0000\u0000\u0000\u0000a\u0001\u0000\u0000\u0000"+
		"\u0000c\u0001\u0000\u0000\u0000\u0000e\u0001\u0000\u0000\u0000\u0000g"+
		"\u0001\u0000\u0000\u0000\u0000i\u0001\u0000\u0000\u0000\u0000k\u0001\u0000"+
		"\u0000\u0000\u0000m\u0001\u0000\u0000\u0000\u0000o\u0001\u0000\u0000\u0000"+
		"\u0000q\u0001\u0000\u0000\u0000\u0000u\u0001\u0000\u0000\u0000\u0000w"+
		"\u0001\u0000\u0000\u0000\u0000y\u0001\u0000\u0000\u0000\u0000{\u0001\u0000"+
		"\u0000\u0000\u0001\u007f\u0001\u0000\u0000\u0000\u0003\u0081\u0001\u0000"+
		"\u0000\u0000\u0005\u0083\u0001\u0000\u0000\u0000\u0007\u0085\u0001\u0000"+
		"\u0000\u0000\t\u0087\u0001\u0000\u0000\u0000\u000b\u0089\u0001\u0000\u0000"+
		"\u0000\r\u008b\u0001\u0000\u0000\u0000\u000f\u008d\u0001\u0000\u0000\u0000"+
		"\u0011\u0090\u0001\u0000\u0000\u0000\u0013\u0093\u0001\u0000\u0000\u0000"+
		"\u0015\u0096\u0001\u0000\u0000\u0000\u0017\u0099\u0001\u0000\u0000\u0000"+
		"\u0019\u009c\u0001\u0000\u0000\u0000\u001b\u009f\u0001\u0000\u0000\u0000"+
		"\u001d\u00a1\u0001\u0000\u0000\u0000\u001f\u00a4\u0001\u0000\u0000\u0000"+
		"!\u00a7\u0001\u0000\u0000\u0000#\u00a9\u0001\u0000\u0000\u0000%\u00ab"+
		"\u0001\u0000\u0000\u0000\'\u00ad\u0001\u0000\u0000\u0000)\u00af\u0001"+
		"\u0000\u0000\u0000+\u00b1\u0001\u0000\u0000\u0000-\u00b4\u0001\u0000\u0000"+
		"\u0000/\u00b7\u0001\u0000\u0000\u00001\u00b9\u0001\u0000\u0000\u00003"+
		"\u00bb\u0001\u0000\u0000\u00005\u00bd\u0001\u0000\u0000\u00007\u00bf\u0001"+
		"\u0000\u0000\u00009\u00c1\u0001\u0000\u0000\u0000;\u00c3\u0001\u0000\u0000"+
		"\u0000=\u00c5\u0001\u0000\u0000\u0000?\u00c7\u0001\u0000\u0000\u0000A"+
		"\u00c9\u0001\u0000\u0000\u0000C\u00cb\u0001\u0000\u0000\u0000E\u00cd\u0001"+
		"\u0000\u0000\u0000G\u00d2\u0001\u0000\u0000\u0000I\u00d7\u0001\u0000\u0000"+
		"\u0000K\u00db\u0001\u0000\u0000\u0000M\u00e2\u0001\u0000\u0000\u0000O"+
		"\u00e6\u0001\u0000\u0000\u0000Q\u00ec\u0001\u0000\u0000\u0000S\u00f1\u0001"+
		"\u0000\u0000\u0000U\u00f6\u0001\u0000\u0000\u0000W\u00fc\u0001\u0000\u0000"+
		"\u0000Y\u0101\u0001\u0000\u0000\u0000[\u0104\u0001\u0000\u0000\u0000]"+
		"\u0109\u0001\u0000\u0000\u0000_\u010d\u0001\u0000\u0000\u0000a\u0113\u0001"+
		"\u0000\u0000\u0000c\u0119\u0001\u0000\u0000\u0000e\u0122\u0001\u0000\u0000"+
		"\u0000g\u012a\u0001\u0000\u0000\u0000i\u0130\u0001\u0000\u0000\u0000k"+
		"\u013b\u0001\u0000\u0000\u0000m\u0149\u0001\u0000\u0000\u0000o\u0158\u0001"+
		"\u0000\u0000\u0000q\u015a\u0001\u0000\u0000\u0000s\u016a\u0001\u0000\u0000"+
		"\u0000u\u016c\u0001\u0000\u0000\u0000w\u0177\u0001\u0000\u0000\u0000y"+
		"\u0180\u0001\u0000\u0000\u0000{\u0189\u0001\u0000\u0000\u0000}\u019b\u0001"+
		"\u0000\u0000\u0000\u007f\u0080\u0005+\u0000\u0000\u0080\u0002\u0001\u0000"+
		"\u0000\u0000\u0081\u0082\u0005-\u0000\u0000\u0082\u0004\u0001\u0000\u0000"+
		"\u0000\u0083\u0084\u0005*\u0000\u0000\u0084\u0006\u0001\u0000\u0000\u0000"+
		"\u0085\u0086\u0005/\u0000\u0000\u0086\b\u0001\u0000\u0000\u0000\u0087"+
		"\u0088\u0005%\u0000\u0000\u0088\n\u0001\u0000\u0000\u0000\u0089\u008a"+
		"\u0005>\u0000\u0000\u008a\f\u0001\u0000\u0000\u0000\u008b\u008c\u0005"+
		"<\u0000\u0000\u008c\u000e\u0001\u0000\u0000\u0000\u008d\u008e\u0005>\u0000"+
		"\u0000\u008e\u008f\u0005=\u0000\u0000\u008f\u0010\u0001\u0000\u0000\u0000"+
		"\u0090\u0091\u0005<\u0000\u0000\u0091\u0092\u0005=\u0000\u0000\u0092\u0012"+
		"\u0001\u0000\u0000\u0000\u0093\u0094\u0005!\u0000\u0000\u0094\u0095\u0005"+
		"=\u0000\u0000\u0095\u0014\u0001\u0000\u0000\u0000\u0096\u0097\u0005=\u0000"+
		"\u0000\u0097\u0098\u0005=\u0000\u0000\u0098\u0016\u0001\u0000\u0000\u0000"+
		"\u0099\u009a\u0005&\u0000\u0000\u009a\u009b\u0005&\u0000\u0000\u009b\u0018"+
		"\u0001\u0000\u0000\u0000\u009c\u009d\u0005|\u0000\u0000\u009d\u009e\u0005"+
		"|\u0000\u0000\u009e\u001a\u0001\u0000\u0000\u0000\u009f\u00a0\u0005!\u0000"+
		"\u0000\u00a0\u001c\u0001\u0000\u0000\u0000\u00a1\u00a2\u0005>\u0000\u0000"+
		"\u00a2\u00a3\u0005>\u0000\u0000\u00a3\u001e\u0001\u0000\u0000\u0000\u00a4"+
		"\u00a5\u0005<\u0000\u0000\u00a5\u00a6\u0005<\u0000\u0000\u00a6 \u0001"+
		"\u0000\u0000\u0000\u00a7\u00a8\u0005&\u0000\u0000\u00a8\"\u0001\u0000"+
		"\u0000\u0000\u00a9\u00aa\u0005|\u0000\u0000\u00aa$\u0001\u0000\u0000\u0000"+
		"\u00ab\u00ac\u0005^\u0000\u0000\u00ac&\u0001\u0000\u0000\u0000\u00ad\u00ae"+
		"\u0005~\u0000\u0000\u00ae(\u0001\u0000\u0000\u0000\u00af\u00b0\u0005="+
		"\u0000\u0000\u00b0*\u0001\u0000\u0000\u0000\u00b1\u00b2\u0005+\u0000\u0000"+
		"\u00b2\u00b3\u0005+\u0000\u0000\u00b3,\u0001\u0000\u0000\u0000\u00b4\u00b5"+
		"\u0005-\u0000\u0000\u00b5\u00b6\u0005-\u0000\u0000\u00b6.\u0001\u0000"+
		"\u0000\u0000\u00b7\u00b8\u0005.\u0000\u0000\u00b80\u0001\u0000\u0000\u0000"+
		"\u00b9\u00ba\u0005[\u0000\u0000\u00ba2\u0001\u0000\u0000\u0000\u00bb\u00bc"+
		"\u0005]\u0000\u0000\u00bc4\u0001\u0000\u0000\u0000\u00bd\u00be\u0005("+
		"\u0000\u0000\u00be6\u0001\u0000\u0000\u0000\u00bf\u00c0\u0005)\u0000\u0000"+
		"\u00c08\u0001\u0000\u0000\u0000\u00c1\u00c2\u0005?\u0000\u0000\u00c2:"+
		"\u0001\u0000\u0000\u0000\u00c3\u00c4\u0005:\u0000\u0000\u00c4<\u0001\u0000"+
		"\u0000\u0000\u00c5\u00c6\u0005;\u0000\u0000\u00c6>\u0001\u0000\u0000\u0000"+
		"\u00c7\u00c8\u0005,\u0000\u0000\u00c8@\u0001\u0000\u0000\u0000\u00c9\u00ca"+
		"\u0005{\u0000\u0000\u00caB\u0001\u0000\u0000\u0000\u00cb\u00cc\u0005}"+
		"\u0000\u0000\u00ccD\u0001\u0000\u0000\u0000\u00cd\u00ce\u0005v\u0000\u0000"+
		"\u00ce\u00cf\u0005o\u0000\u0000\u00cf\u00d0\u0005i\u0000\u0000\u00d0\u00d1"+
		"\u0005d\u0000\u0000\u00d1F\u0001\u0000\u0000\u0000\u00d2\u00d3\u0005b"+
		"\u0000\u0000\u00d3\u00d4\u0005o\u0000\u0000\u00d4\u00d5\u0005o\u0000\u0000"+
		"\u00d5\u00d6\u0005l\u0000\u0000\u00d6H\u0001\u0000\u0000\u0000\u00d7\u00d8"+
		"\u0005i\u0000\u0000\u00d8\u00d9\u0005n\u0000\u0000\u00d9\u00da\u0005t"+
		"\u0000\u0000\u00daJ\u0001\u0000\u0000\u0000\u00db\u00dc\u0005s\u0000\u0000"+
		"\u00dc\u00dd\u0005t\u0000\u0000\u00dd\u00de\u0005r\u0000\u0000\u00de\u00df"+
		"\u0005i\u0000\u0000\u00df\u00e0\u0005n\u0000\u0000\u00e0\u00e1\u0005g"+
		"\u0000\u0000\u00e1L\u0001\u0000\u0000\u0000\u00e2\u00e3\u0005n\u0000\u0000"+
		"\u00e3\u00e4\u0005e\u0000\u0000\u00e4\u00e5\u0005w\u0000\u0000\u00e5N"+
		"\u0001\u0000\u0000\u0000\u00e6\u00e7\u0005c\u0000\u0000\u00e7\u00e8\u0005"+
		"l\u0000\u0000\u00e8\u00e9\u0005a\u0000\u0000\u00e9\u00ea\u0005s\u0000"+
		"\u0000\u00ea\u00eb\u0005s\u0000\u0000\u00ebP\u0001\u0000\u0000\u0000\u00ec"+
		"\u00ed\u0005n\u0000\u0000\u00ed\u00ee\u0005u\u0000\u0000\u00ee\u00ef\u0005"+
		"l\u0000\u0000\u00ef\u00f0\u0005l\u0000\u0000\u00f0R\u0001\u0000\u0000"+
		"\u0000\u00f1\u00f2\u0005t\u0000\u0000\u00f2\u00f3\u0005r\u0000\u0000\u00f3"+
		"\u00f4\u0005u\u0000\u0000\u00f4\u00f5\u0005e\u0000\u0000\u00f5T\u0001"+
		"\u0000\u0000\u0000\u00f6\u00f7\u0005f\u0000\u0000\u00f7\u00f8\u0005a\u0000"+
		"\u0000\u00f8\u00f9\u0005l\u0000\u0000\u00f9\u00fa\u0005s\u0000\u0000\u00fa"+
		"\u00fb\u0005e\u0000\u0000\u00fbV\u0001\u0000\u0000\u0000\u00fc\u00fd\u0005"+
		"t\u0000\u0000\u00fd\u00fe\u0005h\u0000\u0000\u00fe\u00ff\u0005i\u0000"+
		"\u0000\u00ff\u0100\u0005s\u0000\u0000\u0100X\u0001\u0000\u0000\u0000\u0101"+
		"\u0102\u0005i\u0000\u0000\u0102\u0103\u0005f\u0000\u0000\u0103Z\u0001"+
		"\u0000\u0000\u0000\u0104\u0105\u0005e\u0000\u0000\u0105\u0106\u0005l\u0000"+
		"\u0000\u0106\u0107\u0005s\u0000\u0000\u0107\u0108\u0005e\u0000\u0000\u0108"+
		"\\\u0001\u0000\u0000\u0000\u0109\u010a\u0005f\u0000\u0000\u010a\u010b"+
		"\u0005o\u0000\u0000\u010b\u010c\u0005r\u0000\u0000\u010c^\u0001\u0000"+
		"\u0000\u0000\u010d\u010e\u0005w\u0000\u0000\u010e\u010f\u0005h\u0000\u0000"+
		"\u010f\u0110\u0005i\u0000\u0000\u0110\u0111\u0005l\u0000\u0000\u0111\u0112"+
		"\u0005e\u0000\u0000\u0112`\u0001\u0000\u0000\u0000\u0113\u0114\u0005b"+
		"\u0000\u0000\u0114\u0115\u0005r\u0000\u0000\u0115\u0116\u0005e\u0000\u0000"+
		"\u0116\u0117\u0005a\u0000\u0000\u0117\u0118\u0005k\u0000\u0000\u0118b"+
		"\u0001\u0000\u0000\u0000\u0119\u011a\u0005c\u0000\u0000\u011a\u011b\u0005"+
		"o\u0000\u0000\u011b\u011c\u0005n\u0000\u0000\u011c\u011d\u0005t\u0000"+
		"\u0000\u011d\u011e\u0005i\u0000\u0000\u011e\u011f\u0005n\u0000\u0000\u011f"+
		"\u0120\u0005u\u0000\u0000\u0120\u0121\u0005e\u0000\u0000\u0121d\u0001"+
		"\u0000\u0000\u0000\u0122\u0123\u0005r\u0000\u0000\u0123\u0124\u0005e\u0000"+
		"\u0000\u0124\u0125\u0005t\u0000\u0000\u0125\u0126\u0005u\u0000\u0000\u0126"+
		"\u0127\u0005r\u0000\u0000\u0127\u0128\u0005n\u0000\u0000\u0128f\u0001"+
		"\u0000\u0000\u0000\u0129\u012b\u0007\u0000\u0000\u0000\u012a\u0129\u0001"+
		"\u0000\u0000\u0000\u012b\u012c\u0001\u0000\u0000\u0000\u012c\u012a\u0001"+
		"\u0000\u0000\u0000\u012c\u012d\u0001\u0000\u0000\u0000\u012d\u012e\u0001"+
		"\u0000\u0000\u0000\u012e\u012f\u00063\u0000\u0000\u012fh\u0001\u0000\u0000"+
		"\u0000\u0130\u0131\u0005/\u0000\u0000\u0131\u0132\u0005/\u0000\u0000\u0132"+
		"\u0136\u0001\u0000\u0000\u0000\u0133\u0135\b\u0001\u0000\u0000\u0134\u0133"+
		"\u0001\u0000\u0000\u0000\u0135\u0138\u0001\u0000\u0000\u0000\u0136\u0134"+
		"\u0001\u0000\u0000\u0000\u0136\u0137\u0001\u0000\u0000\u0000\u0137\u0139"+
		"\u0001\u0000\u0000\u0000\u0138\u0136\u0001\u0000\u0000\u0000\u0139\u013a"+
		"\u00064\u0000\u0000\u013aj\u0001\u0000\u0000\u0000\u013b\u013c\u0005/"+
		"\u0000\u0000\u013c\u013d\u0005*\u0000\u0000\u013d\u0141\u0001\u0000\u0000"+
		"\u0000\u013e\u0140\t\u0000\u0000\u0000\u013f\u013e\u0001\u0000\u0000\u0000"+
		"\u0140\u0143\u0001\u0000\u0000\u0000\u0141\u0142\u0001\u0000\u0000\u0000"+
		"\u0141\u013f\u0001\u0000\u0000\u0000\u0142\u0144\u0001\u0000\u0000\u0000"+
		"\u0143\u0141\u0001\u0000\u0000\u0000\u0144\u0145\u0005*\u0000\u0000\u0145"+
		"\u0146\u0005/\u0000\u0000\u0146\u0147\u0001\u0000\u0000\u0000\u0147\u0148"+
		"\u00065\u0000\u0000\u0148l\u0001\u0000\u0000\u0000\u0149\u014d\u0007\u0002"+
		"\u0000\u0000\u014a\u014c\u0007\u0003\u0000\u0000\u014b\u014a\u0001\u0000"+
		"\u0000\u0000\u014c\u014f\u0001\u0000\u0000\u0000\u014d\u014b\u0001\u0000"+
		"\u0000\u0000\u014d\u014e\u0001\u0000\u0000\u0000\u014en\u0001\u0000\u0000"+
		"\u0000\u014f\u014d\u0001\u0000\u0000\u0000\u0150\u0159\u00050\u0000\u0000"+
		"\u0151\u0155\u0007\u0004\u0000\u0000\u0152\u0154\u0007\u0005\u0000\u0000"+
		"\u0153\u0152\u0001\u0000\u0000\u0000\u0154\u0157\u0001\u0000\u0000\u0000"+
		"\u0155\u0153\u0001\u0000\u0000\u0000\u0155\u0156\u0001\u0000\u0000\u0000"+
		"\u0156\u0159\u0001\u0000\u0000\u0000\u0157\u0155\u0001\u0000\u0000\u0000"+
		"\u0158\u0150\u0001\u0000\u0000\u0000\u0158\u0151\u0001\u0000\u0000\u0000"+
		"\u0159p\u0001\u0000\u0000\u0000\u015a\u015e\u0005\"\u0000\u0000\u015b"+
		"\u015d\u0003s9\u0000\u015c\u015b\u0001\u0000\u0000\u0000\u015d\u0160\u0001"+
		"\u0000\u0000\u0000\u015e\u015f\u0001\u0000\u0000\u0000\u015e\u015c\u0001"+
		"\u0000\u0000\u0000\u015f\u0161\u0001\u0000\u0000\u0000\u0160\u015e\u0001"+
		"\u0000\u0000\u0000\u0161\u0162\u0005\"\u0000\u0000\u0162r\u0001\u0000"+
		"\u0000\u0000\u0163\u016b\u0007\u0006\u0000\u0000\u0164\u0165\u0005\\\u0000"+
		"\u0000\u0165\u016b\u0005n\u0000\u0000\u0166\u0167\u0005\\\u0000\u0000"+
		"\u0167\u016b\u0005\\\u0000\u0000\u0168\u0169\u0005\\\u0000\u0000\u0169"+
		"\u016b\u0005\"\u0000\u0000\u016a\u0163\u0001\u0000\u0000\u0000\u016a\u0164"+
		"\u0001\u0000\u0000\u0000\u016a\u0166\u0001\u0000\u0000\u0000\u016a\u0168"+
		"\u0001\u0000\u0000\u0000\u016bt\u0001\u0000\u0000\u0000\u016c\u016d\u0005"+
		"f\u0000\u0000\u016d\u016e\u0005\"\u0000\u0000\u016e\u0172\u0001\u0000"+
		"\u0000\u0000\u016f\u0171\u0003}>\u0000\u0170\u016f\u0001\u0000\u0000\u0000"+
		"\u0171\u0174\u0001\u0000\u0000\u0000\u0172\u0170\u0001\u0000\u0000\u0000"+
		"\u0172\u0173\u0001\u0000\u0000\u0000\u0173\u0175\u0001\u0000\u0000\u0000"+
		"\u0174\u0172\u0001\u0000\u0000\u0000\u0175\u0176\u0005$\u0000\u0000\u0176"+
		"v\u0001\u0000\u0000\u0000\u0177\u017b\u0005$\u0000\u0000\u0178\u017a\u0003"+
		"}>\u0000\u0179\u0178\u0001\u0000\u0000\u0000\u017a\u017d\u0001\u0000\u0000"+
		"\u0000\u017b\u0179\u0001\u0000\u0000\u0000\u017b\u017c\u0001\u0000\u0000"+
		"\u0000\u017c\u017e\u0001\u0000\u0000\u0000\u017d\u017b\u0001\u0000\u0000"+
		"\u0000\u017e\u017f\u0005\"\u0000\u0000\u017fx\u0001\u0000\u0000\u0000"+
		"\u0180\u0184\u0005$\u0000\u0000\u0181\u0183\u0003}>\u0000\u0182\u0181"+
		"\u0001\u0000\u0000\u0000\u0183\u0186\u0001\u0000\u0000\u0000\u0184\u0182"+
		"\u0001\u0000\u0000\u0000\u0184\u0185\u0001\u0000\u0000\u0000\u0185\u0187"+
		"\u0001\u0000\u0000\u0000\u0186\u0184\u0001\u0000\u0000\u0000\u0187\u0188"+
		"\u0005$\u0000\u0000\u0188z\u0001\u0000\u0000\u0000\u0189\u018a\u0005f"+
		"\u0000\u0000\u018a\u018b\u0005\"\u0000\u0000\u018b\u018f\u0001\u0000\u0000"+
		"\u0000\u018c\u018e\u0003}>\u0000\u018d\u018c\u0001\u0000\u0000\u0000\u018e"+
		"\u0191\u0001\u0000\u0000\u0000\u018f\u0190\u0001\u0000\u0000\u0000\u018f"+
		"\u018d\u0001\u0000\u0000\u0000\u0190\u0192\u0001\u0000\u0000\u0000\u0191"+
		"\u018f\u0001\u0000\u0000\u0000\u0192\u0193\u0005\"\u0000\u0000\u0193|"+
		"\u0001\u0000\u0000\u0000\u0194\u019c\b\u0007\u0000\u0000\u0195\u0196\u0005"+
		"\\\u0000\u0000\u0196\u019c\u0005n\u0000\u0000\u0197\u0198\u0005\\\u0000"+
		"\u0000\u0198\u019c\u0005\\\u0000\u0000\u0199\u019a\u0005\\\u0000\u0000"+
		"\u019a\u019c\u0005\"\u0000\u0000\u019b\u0194\u0001\u0000\u0000\u0000\u019b"+
		"\u0195\u0001\u0000\u0000\u0000\u019b\u0197\u0001\u0000\u0000\u0000\u019b"+
		"\u0199\u0001\u0000\u0000\u0000\u019c\u01a6\u0001\u0000\u0000\u0000\u019d"+
		"\u01a5\b\b\u0000\u0000\u019e\u019f\u0005\\\u0000\u0000\u019f\u01a5\u0005"+
		"n\u0000\u0000\u01a0\u01a1\u0005\\\u0000\u0000\u01a1\u01a5\u0005\\\u0000"+
		"\u0000\u01a2\u01a3\u0005\\\u0000\u0000\u01a3\u01a5\u0005\"\u0000\u0000"+
		"\u01a4\u019d\u0001\u0000\u0000\u0000\u01a4\u019e\u0001\u0000\u0000\u0000"+
		"\u01a4\u01a0\u0001\u0000\u0000\u0000\u01a4\u01a2\u0001\u0000\u0000\u0000"+
		"\u01a5\u01a8\u0001\u0000\u0000\u0000\u01a6\u01a7\u0001\u0000\u0000\u0000"+
		"\u01a6\u01a4\u0001\u0000\u0000\u0000\u01a7~\u0001\u0000\u0000\u0000\u01a8"+
		"\u01a6\u0001\u0000\u0000\u0000\u0010\u0000\u012c\u0136\u0141\u014d\u0155"+
		"\u0158\u015e\u016a\u0172\u017b\u0184\u018f\u019b\u01a4\u01a6\u0001\u0006"+
		"\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}