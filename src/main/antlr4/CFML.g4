grammar CFML;

// Parser Rules
// ============

block : (blockTag | lineTag)* ;

blockTag
  : cfcomment
  | tagFunction
  | tagIf
  | tagLoop
  | tagScript
  | tagSwitch
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
tagFinally  : CFFINALLY block ENDCFFINALLY ;
tagInclude : CFINCLUDE 'template' '=' STRING_LITERAL TE ;
tagLoop : (tagLoopList | tagLoopArray | tagLoopFrom) ;
tagLoopArray : CFLOOP (ATR_ARRAY | ATR_INDEX)* TE block ENDCFLOOP ;
tagLoopFrom : CFLOOP (ATR_FROM | ATR_TO | ATR_INDEX | ATR_STEP)* TE block ENDCFLOOP ;
tagLoopList : CFLOOP (ATR_LIST | ATR_INDEX)* TE block ENDCFLOOP ;
tagParam : CFPARAM ;
tagScript : CFSCRIPT ;
tagThrow : CFTHROW ;
tagAbort : CFABORT ;
tagTry : CFTRY block tagCatch* tagFinally? ENDCFTRY ;
tagCatch : CFCATCH ('type' '=' STRING_LITERAL)? TE block ENDCFCATCH ;
tagRethrow : CFRETHROW ;

tagSwitch : CFSWITCH 'expression' '=' STRING_LITERAL TE tagCase* tagDefaultCase? ENDCFSWITCH ;
tagCase   : CFCASE 'value' '=' STRING_LITERAL TE block ENDCFCASE ;
tagDefaultCase : CFDEFAULTCASE block ENDCFDEFAULTCASE ;

tagFunction
  : CFFUNCTION
  (
      ATR_NAME
      | ATR_RETURNTYPE
      | ATR_OUTPUT
      | ATR_ACCESS
  )*
  TE
  block
  tagReturn?
  ENDCFFUNCTION
  ;

tagReturn : CFRETURN ;

// Experimental
// ------------

tagSet : CFSET ( assignment | expression ) TE ;
assignment : reference '=' expression ;
expression : binaryOp | unaryOp | operand ;
binaryOp : operand BINARY_OPERATOR expression ;
unaryOp : operand UNARY_POSTFIX_OPERATOR | UNARY_PREFIX_OPERATOR operand ;
operand : literal | reference | funcInvoc ;
literal : STRING_LITERAL | INT_LITERAL | DECIMAL_LITERAL ;

funcInvoc : dottedRef '(' positionalArguments? ')' ;
positionalArguments : expression ( ',' expression )* ;

reference : dottedRef | arrayIndex /* or a struct index */ ;
dottedRef : VARIABLE_NAME ( '.' VARIABLE_NAME )* ;
arrayIndex : dottedRef '[' expression ']' ;

/*
nonrecursive rules:
  dottedRef
  literal

recursive rules to listen for:
  assignment
  binaryOp
  unaryOp
  funcInvoc
  positionalArguments
*/

// Lexer Rules
// ===========

CFCOMMENT : '<!---' .*? '--->' ;

// Experimental
CFSET       : TS 'set' ;

// Tags with no attributes or expressions
CFABORT     : TS 'abort' TE ;
CFBREAK     : TS 'break' TE ;
CFDEFAULTCASE : TS 'defaultcase' TE ;
CFELSE      : TS 'else' TE ;
CFFINALLY   : TS 'finally' TE ;
CFRETHROW   : TS 'rethrow' TE ;
CFTRY       : TS 'try' TE ;

// Tags with expressions
CFIF        : TS 'if' .*? TE ;
CFELSEIF    : TS 'elseif' .*? TE ;
CFRETURN    : TS 'return' .*? TE ;

// Tags with attributes
CFCASE      : TS 'case' ;
CFCATCH     : TS 'catch' ;
CFFUNCTION  : TS 'function' ;
CFINCLUDE   : TS 'include' ;
CFLOOP      : TS 'loop' ;
CFPARAM     : TS 'param' .*? TE ;
CFSWITCH    : TS 'switch' ;
CFTHROW     : TS 'throw' .*? TE ;

// Tags with unparsed blocks
CFSCRIPT    : TS 'script' TE .*? ENDCFSCRIPT ;

// Closing tags
ENDCFCASE   : TC 'case' TE ;
ENDCFCATCH  : TC 'catch' TE ;
ENDCFDEFAULTCASE : TC 'defaultcase' TE ;
ENDCFFINALLY : TC 'finally' TE ;
ENDCFFUNCTION : TC 'function' TE ;
ENDCFIF     : TC 'if' TE ;
ENDCFLOOP   : TC 'loop' TE ;
ENDCFSCRIPT : TC 'script' TE ;
ENDCFSWITCH : TC 'switch' TE ;
ENDCFTRY    : TC 'try' TE ;

// Attributes
ATR_ACCESS      : 'access'      '=' STRING_LITERAL ;
ATR_ARRAY       : 'array'       '=' STRING_LITERAL ;
ATR_FROM        : 'from'        '=' STRING_LITERAL ;
ATR_INDEX       : 'index'       '=' STRING_LITERAL ;
ATR_LIST        : 'list'        '=' STRING_LITERAL ;
ATR_NAME        : 'name'        '=' STRING_LITERAL ;
ATR_OUTPUT      : 'output'      '=' STRING_LITERAL ;
ATR_RETURNTYPE  : 'returntype'  '=' STRING_LITERAL ;
ATR_STEP        : 'step'        '=' STRING_LITERAL ;
ATR_TO          : 'to'          '=' STRING_LITERAL ;

TE : '/'? '>' ; // Tag End

INT_LITERAL : '-'? DIGIT+ ;
DECIMAL_LITERAL : '-'? DIGIT+ '.' DIGIT+ ;
STRING_LITERAL
  : '"' DoubleStringCharacter* '"'
  | '\'' SingleStringCharacter* '\''
  ;

/*
A variable name must begin with a letter, underscore, or Unicode
currency symbol. The initial character can by followed by any number
of letters, numbers, underscore characters, and Unicode currency
symbols. A variable name cannot contain spaces.
http://adobe.ly/1btjPoA
*/
VARIABLE_NAME : [a-zA-Z_$] [a-zA-Z_$0-9]* ;

/* omg so many operators: http://adobe.ly/cRnrRL */
UNARY_POSTFIX_OPERATOR : '++' | '--' ;
UNARY_PREFIX_OPERATOR : 'NOT' ;
BINARY_OPERATOR
  : '+'
  | '-'
  | '*'
  | '/'
  | '%'
  | '^'
  | '\\'
  | 'MOD'
  | '&&'
  | 'AND'
  | '||'
  | 'OR'
  | 'XOR'
  | 'EQV'
  | 'IMP'
  | 'IS'
  | 'EQUAL'
  | 'EQ'
  | 'IS NOT'
  | 'NOT EQUAL'
  | 'NEQ'
  | 'CONTAINS'
  | 'DOES NOT CONTAIN'
  | 'GREATER THAN'
  | 'GT'
  | 'LESS THAN'
  | 'LT'
  | 'GREATER THAN OR EQUAL TO'
  | 'GTE'
  | 'GE'
  | 'LESS THAN OR EQUAL TO'
  | 'LTE'
  | 'LE'
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

fragment DIGIT : [0-9] ;

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
