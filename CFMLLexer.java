// Generated from CFML.g4 by ANTLR 4.0
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CFMLLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__1=1, T__0=2, BOOLEAN_LITERAL=3, CFCOMMENT=4, EQUALS=5, TS=6, TE=7, 
		STRING_LITERAL=8, IDENTIFIER=9, WS=10;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'set'", "'abort'", "BOOLEAN_LITERAL", "CFCOMMENT", "'='", "'<cf'", "'>'", 
		"STRING_LITERAL", "IDENTIFIER", "WS"
	};
	public static final String[] ruleNames = {
		"T__1", "T__0", "BOOLEAN_LITERAL", "CFCOMMENT", "EQUALS", "TS", "TE", 
		"STRING_LITERAL", "IDENTIFIER", "WS", "DoubleStringCharacter", "SingleStringCharacter", 
		"LETTER", "DIGIT"
	};


	public CFMLLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "CFML.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 9: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\2\4\f\u0085\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t"+
		"\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\3\2\3"+
		"\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4"+
		"\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\3\4\5\4<\n\4\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\7\5E\n\5\f\5\16\5H\13\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3"+
		"\7\3\b\3\b\3\t\3\t\7\tY\n\t\f\t\16\t\\\13\t\3\t\3\t\3\t\7\ta\n\t\f\t\16"+
		"\td\13\t\3\t\5\tg\n\t\3\n\3\n\3\n\7\nl\n\n\f\n\16\no\13\n\3\13\6\13r\n"+
		"\13\r\13\16\13s\3\13\3\13\3\f\3\f\3\f\5\f{\n\f\3\r\3\r\3\r\5\r\u0080\n"+
		"\r\3\16\3\16\3\17\3\17\3F\20\3\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t"+
		"\1\21\n\1\23\13\1\25\f\2\27\2\1\31\2\1\33\2\1\35\2\1\3\2\7\5\13\f\16\17"+
		"\"\"\3$$\3))\16&&C\\aac|\u00c2\u00d8\u00da\u00f8\u00fa\u2001\u3042\u3191"+
		"\u3302\u3381\u3402\u3d2f\u4e02\ua001\uf902\ufb01\21\62;\u0662\u066b\u06f2"+
		"\u06fb\u0968\u0971\u09e8\u09f1\u0a68\u0a71\u0ae8\u0af1\u0b68\u0b71\u0be9"+
		"\u0bf1\u0c68\u0c71\u0ce8\u0cf1\u0d68\u0d71\u0e52\u0e5b\u0ed2\u0edb\u1042"+
		"\u104b\u008c\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2"+
		"\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\3"+
		"\37\3\2\2\2\5#\3\2\2\2\7;\3\2\2\2\t=\3\2\2\2\13N\3\2\2\2\rP\3\2\2\2\17"+
		"T\3\2\2\2\21f\3\2\2\2\23h\3\2\2\2\25q\3\2\2\2\27z\3\2\2\2\31\177\3\2\2"+
		"\2\33\u0081\3\2\2\2\35\u0083\3\2\2\2\37 \7u\2\2 !\7g\2\2!\"\7v\2\2\"\4"+
		"\3\2\2\2#$\7c\2\2$%\7d\2\2%&\7q\2\2&\'\7t\2\2\'(\7v\2\2(\6\3\2\2\2)*\7"+
		"V\2\2*+\7T\2\2+,\7W\2\2,<\7G\2\2-.\7H\2\2./\7C\2\2/\60\7N\2\2\60\61\7"+
		"U\2\2\61<\7G\2\2\62\63\7v\2\2\63\64\7t\2\2\64\65\7w\2\2\65<\7g\2\2\66"+
		"\67\7h\2\2\678\7c\2\289\7n\2\29:\7u\2\2:<\7g\2\2;)\3\2\2\2;-\3\2\2\2;"+
		"\62\3\2\2\2;\66\3\2\2\2<\b\3\2\2\2=>\7>\2\2>?\7#\2\2?@\7/\2\2@A\7/\2\2"+
		"AB\7/\2\2BF\3\2\2\2CE\13\2\2\2DC\3\2\2\2EH\3\2\2\2FG\3\2\2\2FD\3\2\2\2"+
		"GI\3\2\2\2HF\3\2\2\2IJ\7/\2\2JK\7/\2\2KL\7/\2\2LM\7@\2\2M\n\3\2\2\2NO"+
		"\7?\2\2O\f\3\2\2\2PQ\7>\2\2QR\7e\2\2RS\7h\2\2S\16\3\2\2\2TU\7@\2\2U\20"+
		"\3\2\2\2VZ\7$\2\2WY\5\27\f\2XW\3\2\2\2Y\\\3\2\2\2ZX\3\2\2\2Z[\3\2\2\2"+
		"[]\3\2\2\2\\Z\3\2\2\2]g\7$\2\2^b\7)\2\2_a\5\31\r\2`_\3\2\2\2ad\3\2\2\2"+
		"b`\3\2\2\2bc\3\2\2\2ce\3\2\2\2db\3\2\2\2eg\7)\2\2fV\3\2\2\2f^\3\2\2\2"+
		"g\22\3\2\2\2hm\5\33\16\2il\5\33\16\2jl\5\35\17\2ki\3\2\2\2kj\3\2\2\2l"+
		"o\3\2\2\2mk\3\2\2\2mn\3\2\2\2n\24\3\2\2\2om\3\2\2\2pr\t\2\2\2qp\3\2\2"+
		"\2rs\3\2\2\2sq\3\2\2\2st\3\2\2\2tu\3\2\2\2uv\b\13\2\2v\26\3\2\2\2w{\n"+
		"\3\2\2xy\7$\2\2y{\7$\2\2zw\3\2\2\2zx\3\2\2\2{\30\3\2\2\2|\u0080\n\4\2"+
		"\2}~\7)\2\2~\u0080\7)\2\2\177|\3\2\2\2\177}\3\2\2\2\u0080\32\3\2\2\2\u0081"+
		"\u0082\t\5\2\2\u0082\34\3\2\2\2\u0083\u0084\t\6\2\2\u0084\36\3\2\2\2\r"+
		"\2;FZbfkmsz\177";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}