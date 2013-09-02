grammar CFML;

// Parser Rules
// ============

block : (cfcomment | tagIf | line)* ;

line :
  tagSet
  | tagAbort ;

/* `cfcomment` must be a parser rule, so that the listener will hear
about it, but it must be implemented as a lexer rule, so that it can
have higher precedence than the `WS` rule. */
cfcomment : CFCOMMENT ;

tagIf : CFIF block tagElseIf* tagElse? ENDCFIF;
tagElseIf : CFELSEIF block ;
tagElse : CFELSE block ;
tagSet : CFSET ;
tagAbort : CFABORT ;

// Lexer Rules
// ===========

CFABORT : TS 'abort' TE ;
CFCOMMENT : '<!---' .*? '--->' ;
CFIF : TS 'if' .*? TE ;
CFELSE : TS 'else' TE ;
CFELSEIF : TS 'elseif' .*? TE;
CFSET : TS 'set' .*? TE ;
ENDCFIF : TC 'if' TE ;
WS : [ \t\r\n\f]+ -> skip ; // skip spaces, tabs, newlines, and formfeeds

// Lexer Fragments
// ===============

fragment TC : '</cf' ; // Tag Close
fragment TS : '<cf' ; // Tag Start
fragment TE : '>' ; // Tag End
