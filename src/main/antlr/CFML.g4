grammar CFML;

// Parser Rules
// ============

block : (blockTag | lineTag)* ;

blockTag
  : cfcomment
  | tagIf
  | tagLoop
  | tagScript
  | tagTry
  ;

lineTag
  : tagAbort
  | tagBreak
  | tagInclude
  | tagParam
  | tagRethrow
  | tagSet
  | tagThrow
  ;

/* `cfcomment` must be a parser rule, so that the listener will hear
about it, but it must be implemented as a lexer rule, so that it can
have higher precedence than the `WS` rule. */
cfcomment : CFCOMMENT ;

tagBreak : CFBREAK ;
tagIf : CFIF block tagElseIf* tagElse? ENDCFIF ;
tagElseIf : CFELSEIF block ;
tagElse : CFELSE block ;
tagInclude : CFINCLUDE 'template' '=' STRING_LITERAL TE ;
tagLoop : (tagLoopList | tagLoopArray | tagLoopFrom) ;
tagLoopArray : CFLOOP (ATR_ARRAY | ATR_INDEX)* TE block ENDCFLOOP ;
tagLoopFrom : CFLOOP (ATR_FROM | ATR_TO | ATR_INDEX | ATR_STEP)* TE block ENDCFLOOP ;
tagLoopList : CFLOOP (ATR_LIST | ATR_INDEX)* TE block ENDCFLOOP ;
tagParam : CFPARAM ;
tagScript : CFSCRIPT ;
tagSet : CFSET ;
tagThrow : CFTHROW ;
tagAbort : CFABORT ;
tagTry : CFTRY block tagCatch* ENDCFTRY ;
tagCatch : CFCATCH ('type' '=' STRING_LITERAL)? TE block ENDCFCATCH ;
tagRethrow : CFRETHROW ;

// Lexer Rules
// ===========

CFCOMMENT : '<!---' .*? '--->' ;

// Tags with no attributes or expressions
CFABORT     : TS 'abort' TE ;
CFBREAK     : TS 'break' TE ;
CFELSE      : TS 'else' TE ;
CFRETHROW   : TS 'rethrow' TE ;
CFTRY       : TS 'try' TE ;

// Tags with expressions
CFIF        : TS 'if' .*? TE ;
CFELSEIF    : TS 'elseif' .*? TE ;
CFSET       : TS 'set' .*? TE ;

// Tags with attributes
CFCATCH     : TS 'catch' ;
CFINCLUDE   : TS 'include' ;
CFLOOP      : TS 'loop' ;
CFPARAM     : TS 'param' .*? TE ;
CFTHROW     : TS 'throw' .*? TE ;

// Tags with unparsed blocks
CFSCRIPT    : TS 'script' TE .*? ENDCFSCRIPT ;

// Closing tags
ENDCFCATCH  : TC 'catch' TE ;
ENDCFIF     : TC 'if' TE ;
ENDCFLOOP   : TC 'loop' TE ;
ENDCFSCRIPT : TC 'script' TE ;
ENDCFTRY    : TC 'try' TE ;

// Attributes
ATR_ARRAY   : 'array' '=' STRING_LITERAL ;
ATR_FROM    : 'from'  '=' STRING_LITERAL ;
ATR_INDEX   : 'index' '=' STRING_LITERAL ;
ATR_LIST    : 'list'  '=' STRING_LITERAL ;
ATR_STEP    : 'step'  '=' STRING_LITERAL ;
ATR_TO      : 'to'    '=' STRING_LITERAL ;

TE : '/'? '>' ; // Tag End

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
