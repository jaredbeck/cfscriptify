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
		T__1=1, T__0=2, ASSIGNMENT=3, BOOLEAN_LITERAL=4, CFCOMMENT=5, TS=6, TE=7, 
		STRING_LITERAL=8, IDENTIFIER=9, WS=10;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'set'", "'abort'", "ASSIGNMENT", "BOOLEAN_LITERAL", "CFCOMMENT", "'<cf'", 
		"'>'", "STRING_LITERAL", "IDENTIFIER", "WS"
	};
	public static final String[] ruleNames = {
		"T__1", "T__0", "ASSIGNMENT", "BOOLEAN_LITERAL", "CFCOMMENT", "TS", "TE", 
		"STRING_LITERAL", "IDENTIFIER", "WS", "EQUALS", "DoubleStringCharacter", 
		"SingleStringCharacter", "LETTER", "DIGIT"
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
		"\2\4\f\u0090\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t"+
		"\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20"+
		"\t\20\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\4\3\4\7\4.\n\4\f\4\16"+
		"\4\61\13\4\3\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\5\5\5G\n\5\3\6\3\6\3\6\3\6\3\6\3\6\3\6\7\6P\n\6\f\6"+
		"\16\6S\13\6\3\6\3\6\3\6\3\6\3\6\3\7\3\7\3\7\3\7\3\b\3\b\3\t\3\t\7\tb\n"+
		"\t\f\t\16\te\13\t\3\t\3\t\3\t\7\tj\n\t\f\t\16\tm\13\t\3\t\5\tp\n\t\3\n"+
		"\3\n\3\n\7\nu\n\n\f\n\16\nx\13\n\3\13\6\13{\n\13\r\13\16\13|\3\13\3\13"+
		"\3\f\3\f\3\r\3\r\3\r\5\r\u0086\n\r\3\16\3\16\3\16\5\16\u008b\n\16\3\17"+
		"\3\17\3\20\3\20\4/Q\21\3\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t\1\21"+
		"\n\1\23\13\1\25\f\2\27\2\1\31\2\1\33\2\1\35\2\1\37\2\1\3\2\7\5\13\f\16"+
		"\17\"\"\3$$\3))\16&&C\\aac|\u00c2\u00d8\u00da\u00f8\u00fa\u2001\u3042"+
		"\u3191\u3302\u3381\u3402\u3d2f\u4e02\ua001\uf902\ufb01\21\62;\u0662\u066b"+
		"\u06f2\u06fb\u0968\u0971\u09e8\u09f1\u0a68\u0a71\u0ae8\u0af1\u0b68\u0b71"+
		"\u0be9\u0bf1\u0c68\u0c71\u0ce8\u0cf1\u0d68\u0d71\u0e52\u0e5b\u0ed2\u0edb"+
		"\u1042\u104b\u0097\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13"+
		"\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2"+
		"\2\3!\3\2\2\2\5%\3\2\2\2\7+\3\2\2\2\tF\3\2\2\2\13H\3\2\2\2\rY\3\2\2\2"+
		"\17]\3\2\2\2\21o\3\2\2\2\23q\3\2\2\2\25z\3\2\2\2\27\u0080\3\2\2\2\31\u0085"+
		"\3\2\2\2\33\u008a\3\2\2\2\35\u008c\3\2\2\2\37\u008e\3\2\2\2!\"\7u\2\2"+
		"\"#\7g\2\2#$\7v\2\2$\4\3\2\2\2%&\7c\2\2&\'\7d\2\2\'(\7q\2\2()\7t\2\2)"+
		"*\7v\2\2*\6\3\2\2\2+/\5\27\f\2,.\13\2\2\2-,\3\2\2\2.\61\3\2\2\2/\60\3"+
		"\2\2\2/-\3\2\2\2\60\62\3\2\2\2\61/\3\2\2\2\62\63\5\17\b\2\63\b\3\2\2\2"+
		"\64\65\7V\2\2\65\66\7T\2\2\66\67\7W\2\2\67G\7G\2\289\7H\2\29:\7C\2\2:"+
		";\7N\2\2;<\7U\2\2<G\7G\2\2=>\7v\2\2>?\7t\2\2?@\7w\2\2@G\7g\2\2AB\7h\2"+
		"\2BC\7c\2\2CD\7n\2\2DE\7u\2\2EG\7g\2\2F\64\3\2\2\2F8\3\2\2\2F=\3\2\2\2"+
		"FA\3\2\2\2G\n\3\2\2\2HI\7>\2\2IJ\7#\2\2JK\7/\2\2KL\7/\2\2LM\7/\2\2MQ\3"+
		"\2\2\2NP\13\2\2\2ON\3\2\2\2PS\3\2\2\2QR\3\2\2\2QO\3\2\2\2RT\3\2\2\2SQ"+
		"\3\2\2\2TU\7/\2\2UV\7/\2\2VW\7/\2\2WX\7@\2\2X\f\3\2\2\2YZ\7>\2\2Z[\7e"+
		"\2\2[\\\7h\2\2\\\16\3\2\2\2]^\7@\2\2^\20\3\2\2\2_c\7$\2\2`b\5\31\r\2a"+
		"`\3\2\2\2be\3\2\2\2ca\3\2\2\2cd\3\2\2\2df\3\2\2\2ec\3\2\2\2fp\7$\2\2g"+
		"k\7)\2\2hj\5\33\16\2ih\3\2\2\2jm\3\2\2\2ki\3\2\2\2kl\3\2\2\2ln\3\2\2\2"+
		"mk\3\2\2\2np\7)\2\2o_\3\2\2\2og\3\2\2\2p\22\3\2\2\2qv\5\35\17\2ru\5\35"+
		"\17\2su\5\37\20\2tr\3\2\2\2ts\3\2\2\2ux\3\2\2\2vt\3\2\2\2vw\3\2\2\2w\24"+
		"\3\2\2\2xv\3\2\2\2y{\t\2\2\2zy\3\2\2\2{|\3\2\2\2|z\3\2\2\2|}\3\2\2\2}"+
		"~\3\2\2\2~\177\b\13\2\2\177\26\3\2\2\2\u0080\u0081\7?\2\2\u0081\30\3\2"+
		"\2\2\u0082\u0086\n\3\2\2\u0083\u0084\7$\2\2\u0084\u0086\7$\2\2\u0085\u0082"+
		"\3\2\2\2\u0085\u0083\3\2\2\2\u0086\32\3\2\2\2\u0087\u008b\n\4\2\2\u0088"+
		"\u0089\7)\2\2\u0089\u008b\7)\2\2\u008a\u0087\3\2\2\2\u008a\u0088\3\2\2"+
		"\2\u008b\34\3\2\2\2\u008c\u008d\t\5\2\2\u008d\36\3\2\2\2\u008e\u008f\t"+
		"\6\2\2\u008f \3\2\2\2\16\2/FQckotv|\u0085\u008a";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}