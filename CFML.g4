grammar CFML;

// Parser Rules
// ============

block : (cfcomment | tagIf | tagLoop | line)* ;

line :
  tagSet
  | tagAbort ;

/* `cfcomment` must be a parser rule, so that the listener will hear
about it, but it must be implemented as a lexer rule, so that it can
have higher precedence than the `WS` rule. */
cfcomment : CFCOMMENT ;

tagIf : CFIF block tagElseIf* tagElse? ENDCFIF ;
tagElseIf : CFELSEIF block ;
tagElse : CFELSE block ;
tagLoop : (tagLoopList | tagLoopArray | tagLoopFrom) ;
tagLoopArray : CFLOOP (ATR_ARRAY | ATR_INDEX)* TE block ENDCFLOOP ;
tagLoopFrom : CFLOOP (ATR_FROM | ATR_TO | ATR_INDEX | ATR_STEP)* TE block ENDCFLOOP ;
tagLoopList : CFLOOP (ATR_LIST | ATR_INDEX)* TE block ENDCFLOOP ;
tagSet : CFSET ;
tagAbort : CFABORT ;

// Lexer Rules
// ===========

CFABORT : TS 'abort' TE ;
CFCOMMENT : '<!---' .*? '--->' ;
CFIF : TS 'if' .*? TE ;
CFELSE : TS 'else' TE ;
CFELSEIF : TS 'elseif' .*? TE ;
CFLOOP : TS 'loop' ;
CFSET : TS 'set' .*? TE ;

ENDCFIF : TC 'if' TE ;
ENDCFLOOP : TC 'loop' TE ;

ATR_ARRAY : 'array' '=' STRING_LITERAL ;
ATR_FROM : 'from' '=' STRING_LITERAL ;
ATR_INDEX : 'index' '=' STRING_LITERAL ;
ATR_LIST : 'list' '=' STRING_LITERAL ;
ATR_STEP : 'step' '=' STRING_LITERAL ;
ATR_TO : 'to' '=' STRING_LITERAL ;

TE : '>' ; // Tag End

STRING_LITERAL
  : '"' DoubleStringCharacter* '"'
  | '\'' SingleStringCharacter* '\''
  ;

/*
Lexer Rules: Skips
------------------

Skip whitespace (spaces, tabs, newlines, and formfeeds) unless
a rule above consumes it first.  Skip <cfsilent> because cfscript
is always silent.
*/

WS : [ \t\r\n\f]+ -> skip ;
CFSILENT : (TS | TC) 'silent' TE -> skip ;

// Lexer Fragments
// ===============

fragment DoubleStringCharacter
  : ~('"')
  | '""'
  ;

fragment SingleStringCharacter
  : ~('\'')
  | '\'\''
  ;

fragment TC : '</cf' ; // Tag Close
fragment TS : '<cf' ; // Tag Start
