// Generated from CFML.g4 by ANTLR 4.0
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class CFMLParser extends Parser {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__1=1, T__0=2, BOOLEAN_LITERAL=3, CFCOMMENT=4, EQUALS=5, TS=6, TE=7, 
		STRING_LITERAL=8, IDENTIFIER=9, WS=10;
	public static final String[] tokenNames = {
		"<INVALID>", "'set'", "'abort'", "BOOLEAN_LITERAL", "CFCOMMENT", "'='", 
		"'<cf'", "'>'", "STRING_LITERAL", "IDENTIFIER", "WS"
	};
	public static final int
		RULE_cfm = 0, RULE_line = 1, RULE_cfcomment = 2, RULE_tagSet = 3, RULE_tagAbort = 4, 
		RULE_expression = 5, RULE_literal = 6;
	public static final String[] ruleNames = {
		"cfm", "line", "cfcomment", "tagSet", "tagAbort", "expression", "literal"
	};

	@Override
	public String getGrammarFileName() { return "CFML.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public CFMLParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class CfmContext extends ParserRuleContext {
		public CfcommentContext cfcomment(int i) {
			return getRuleContext(CfcommentContext.class,i);
		}
		public LineContext line(int i) {
			return getRuleContext(LineContext.class,i);
		}
		public List<CfcommentContext> cfcomment() {
			return getRuleContexts(CfcommentContext.class);
		}
		public List<LineContext> line() {
			return getRuleContexts(LineContext.class);
		}
		public CfmContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cfm; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFMLListener ) ((CFMLListener)listener).enterCfm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFMLListener ) ((CFMLListener)listener).exitCfm(this);
		}
	}

	public final CfmContext cfm() throws RecognitionException {
		CfmContext _localctx = new CfmContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_cfm);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CFCOMMENT || _la==TS) {
				{
				setState(16);
				switch (_input.LA(1)) {
				case CFCOMMENT:
					{
					setState(14); cfcomment();
					}
					break;
				case TS:
					{
					setState(15); line();
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				setState(20);
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

	public static class LineContext extends ParserRuleContext {
		public TagSetContext tagSet() {
			return getRuleContext(TagSetContext.class,0);
		}
		public TagAbortContext tagAbort() {
			return getRuleContext(TagAbortContext.class,0);
		}
		public LineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_line; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFMLListener ) ((CFMLListener)listener).enterLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFMLListener ) ((CFMLListener)listener).exitLine(this);
		}
	}

	public final LineContext line() throws RecognitionException {
		LineContext _localctx = new LineContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_line);
		try {
			setState(23);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(21); tagSet();
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(22); tagAbort();
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

	public static class CfcommentContext extends ParserRuleContext {
		public TerminalNode CFCOMMENT() { return getToken(CFMLParser.CFCOMMENT, 0); }
		public CfcommentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_cfcomment; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFMLListener ) ((CFMLListener)listener).enterCfcomment(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFMLListener ) ((CFMLListener)listener).exitCfcomment(this);
		}
	}

	public final CfcommentContext cfcomment() throws RecognitionException {
		CfcommentContext _localctx = new CfcommentContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_cfcomment);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(25); match(CFCOMMENT);
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

	public static class TagSetContext extends ParserRuleContext {
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode TE() { return getToken(CFMLParser.TE, 0); }
		public TerminalNode EQUALS() { return getToken(CFMLParser.EQUALS, 0); }
		public TerminalNode IDENTIFIER() { return getToken(CFMLParser.IDENTIFIER, 0); }
		public TerminalNode TS() { return getToken(CFMLParser.TS, 0); }
		public TagSetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tagSet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFMLListener ) ((CFMLListener)listener).enterTagSet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFMLListener ) ((CFMLListener)listener).exitTagSet(this);
		}
	}

	public final TagSetContext tagSet() throws RecognitionException {
		TagSetContext _localctx = new TagSetContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_tagSet);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(27); match(TS);
			setState(28); match(1);
			setState(29); match(IDENTIFIER);
			setState(30); match(EQUALS);
			setState(31); expression();
			setState(32); match(TE);
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

	public static class TagAbortContext extends ParserRuleContext {
		public TerminalNode TE() { return getToken(CFMLParser.TE, 0); }
		public TerminalNode TS() { return getToken(CFMLParser.TS, 0); }
		public TagAbortContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tagAbort; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFMLListener ) ((CFMLListener)listener).enterTagAbort(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFMLListener ) ((CFMLListener)listener).exitTagAbort(this);
		}
	}

	public final TagAbortContext tagAbort() throws RecognitionException {
		TagAbortContext _localctx = new TagAbortContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_tagAbort);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34); match(TS);
			setState(35); match(2);
			setState(36); match(TE);
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

	public static class ExpressionContext extends ParserRuleContext {
		public LiteralContext literal() {
			return getRuleContext(LiteralContext.class,0);
		}
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFMLListener ) ((CFMLListener)listener).enterExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFMLListener ) ((CFMLListener)listener).exitExpression(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_expression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(38); literal();
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

	public static class LiteralContext extends ParserRuleContext {
		public TerminalNode BOOLEAN_LITERAL() { return getToken(CFMLParser.BOOLEAN_LITERAL, 0); }
		public TerminalNode STRING_LITERAL() { return getToken(CFMLParser.STRING_LITERAL, 0); }
		public LiteralContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof CFMLListener ) ((CFMLListener)listener).enterLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof CFMLListener ) ((CFMLListener)listener).exitLiteral(this);
		}
	}

	public final LiteralContext literal() throws RecognitionException {
		LiteralContext _localctx = new LiteralContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_literal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			_la = _input.LA(1);
			if ( !(_la==BOOLEAN_LITERAL || _la==STRING_LITERAL) ) {
			_errHandler.recoverInline(this);
			}
			consume();
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

	public static final String _serializedATN =
		"\2\3\f-\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\3\2\3"+
		"\2\7\2\23\n\2\f\2\16\2\26\13\2\3\3\3\3\5\3\32\n\3\3\4\3\4\3\5\3\5\3\5"+
		"\3\5\3\5\3\5\3\5\3\6\3\6\3\6\3\6\3\7\3\7\3\b\3\b\3\b\2\t\2\4\6\b\n\f\16"+
		"\2\3\4\5\5\n\n(\2\24\3\2\2\2\4\31\3\2\2\2\6\33\3\2\2\2\b\35\3\2\2\2\n"+
		"$\3\2\2\2\f(\3\2\2\2\16*\3\2\2\2\20\23\5\6\4\2\21\23\5\4\3\2\22\20\3\2"+
		"\2\2\22\21\3\2\2\2\23\26\3\2\2\2\24\22\3\2\2\2\24\25\3\2\2\2\25\3\3\2"+
		"\2\2\26\24\3\2\2\2\27\32\5\b\5\2\30\32\5\n\6\2\31\27\3\2\2\2\31\30\3\2"+
		"\2\2\32\5\3\2\2\2\33\34\7\6\2\2\34\7\3\2\2\2\35\36\7\b\2\2\36\37\7\3\2"+
		"\2\37 \7\13\2\2 !\7\7\2\2!\"\5\f\7\2\"#\7\t\2\2#\t\3\2\2\2$%\7\b\2\2%"+
		"&\7\4\2\2&\'\7\t\2\2\'\13\3\2\2\2()\5\16\b\2)\r\3\2\2\2*+\t\2\2\2+\17"+
		"\3\2\2\2\5\22\24\31";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}