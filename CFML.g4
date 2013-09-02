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

ATR_ARRAY : 'array' '="' .*? '"' ;
ATR_FROM : 'from' '="' .*? '"' ;
ATR_INDEX : 'index' '="' .*? '"' ;
ATR_LIST : 'list' '="' .*? '"' ;
ATR_STEP : 'step' '="' .*? '"' ;
ATR_TO : 'to' '="' .*? '"' ;

WS : [ \t\r\n\f]+ -> skip ; // skip spaces, tabs, newlines, and formfeeds

TS : '<cf' ; // Tag Start
TE : '>' ; // Tag End

// Lexer Fragments
// ===============

fragment TC : '</cf' ; // Tag Close
