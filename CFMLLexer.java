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
		T__1=1, T__0=2, TS=3, TE=4, EQUALS=5, BOOLEAN_LITERAL=6, STRING_LITERAL=7, 
		IDENTIFIER=8, WS=9;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'set'", "'abort'", "'<cf'", "'>'", "'='", "BOOLEAN_LITERAL", "STRING_LITERAL", 
		"IDENTIFIER", "WS"
	};
	public static final String[] ruleNames = {
		"T__1", "T__0", "TS", "TE", "EQUALS", "BOOLEAN_LITERAL", "STRING_LITERAL", 
		"DoubleStringCharacter", "SingleStringCharacter", "LETTER", "DIGIT", "IDENTIFIER", 
		"WS"
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
		case 12: WS_action((RuleContext)_localctx, actionIndex); break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0: skip();  break;
		}
	}

	public static final String _serializedATN =
		"\2\4\13r\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4"+
		"\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\3\2\3\2\3\2\3\2\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\5\7B\n\7\3\b\3"+
		"\b\7\bF\n\b\f\b\16\bI\13\b\3\b\3\b\3\b\7\bN\n\b\f\b\16\bQ\13\b\3\b\5\b"+
		"T\n\b\3\t\3\t\3\t\5\tY\n\t\3\n\3\n\3\n\5\n^\n\n\3\13\3\13\3\f\3\f\3\r"+
		"\3\r\3\r\7\rg\n\r\f\r\16\rj\13\r\3\16\6\16m\n\16\r\16\16\16n\3\16\3\16"+
		"\2\17\3\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t\1\21\2\1\23\2\1\25\2\1"+
		"\27\2\1\31\n\1\33\13\2\3\2\7\3$$\3))\16&&C\\aac|\u00c2\u00d8\u00da\u00f8"+
		"\u00fa\u2001\u3042\u3191\u3302\u3381\u3402\u3d2f\u4e02\ua001\uf902\ufb01"+
		"\21\62;\u0662\u066b\u06f2\u06fb\u0968\u0971\u09e8\u09f1\u0a68\u0a71\u0ae8"+
		"\u0af1\u0b68\u0b71\u0be9\u0bf1\u0c68\u0c71\u0ce8\u0cf1\u0d68\u0d71\u0e52"+
		"\u0e5b\u0ed2\u0edb\u1042\u104b\5\13\f\16\17\"\"x\2\3\3\2\2\2\2\5\3\2\2"+
		"\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\31"+
		"\3\2\2\2\2\33\3\2\2\2\3\35\3\2\2\2\5!\3\2\2\2\7\'\3\2\2\2\t+\3\2\2\2\13"+
		"-\3\2\2\2\rA\3\2\2\2\17S\3\2\2\2\21X\3\2\2\2\23]\3\2\2\2\25_\3\2\2\2\27"+
		"a\3\2\2\2\31c\3\2\2\2\33l\3\2\2\2\35\36\7u\2\2\36\37\7g\2\2\37 \7v\2\2"+
		" \4\3\2\2\2!\"\7c\2\2\"#\7d\2\2#$\7q\2\2$%\7t\2\2%&\7v\2\2&\6\3\2\2\2"+
		"\'(\7>\2\2()\7e\2\2)*\7h\2\2*\b\3\2\2\2+,\7@\2\2,\n\3\2\2\2-.\7?\2\2."+
		"\f\3\2\2\2/\60\7V\2\2\60\61\7T\2\2\61\62\7W\2\2\62B\7G\2\2\63\64\7H\2"+
		"\2\64\65\7C\2\2\65\66\7N\2\2\66\67\7U\2\2\67B\7G\2\289\7v\2\29:\7t\2\2"+
		":;\7w\2\2;B\7g\2\2<=\7h\2\2=>\7c\2\2>?\7n\2\2?@\7u\2\2@B\7g\2\2A/\3\2"+
		"\2\2A\63\3\2\2\2A8\3\2\2\2A<\3\2\2\2B\16\3\2\2\2CG\7$\2\2DF\5\21\t\2E"+
		"D\3\2\2\2FI\3\2\2\2GE\3\2\2\2GH\3\2\2\2HJ\3\2\2\2IG\3\2\2\2JT\7$\2\2K"+
		"O\7)\2\2LN\5\23\n\2ML\3\2\2\2NQ\3\2\2\2OM\3\2\2\2OP\3\2\2\2PR\3\2\2\2"+
		"QO\3\2\2\2RT\7)\2\2SC\3\2\2\2SK\3\2\2\2T\20\3\2\2\2UY\n\2\2\2VW\7$\2\2"+
		"WY\7$\2\2XU\3\2\2\2XV\3\2\2\2Y\22\3\2\2\2Z^\n\3\2\2[\\\7)\2\2\\^\7)\2"+
		"\2]Z\3\2\2\2][\3\2\2\2^\24\3\2\2\2_`\t\4\2\2`\26\3\2\2\2ab\t\5\2\2b\30"+
		"\3\2\2\2ch\5\25\13\2dg\5\25\13\2eg\5\27\f\2fd\3\2\2\2fe\3\2\2\2gj\3\2"+
		"\2\2hf\3\2\2\2hi\3\2\2\2i\32\3\2\2\2jh\3\2\2\2km\t\6\2\2lk\3\2\2\2mn\3"+
		"\2\2\2nl\3\2\2\2no\3\2\2\2op\3\2\2\2pq\b\16\2\2q\34\3\2\2\2\f\2AGOSX]"+
		"fhn";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}