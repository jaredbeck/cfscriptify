grammar CFML;

// Parser Rules
// ============

// Starting rule.  `block` describes an entire CFML file.
block : (blockTag | lineTag)* ;

blockTag
  : cfcomment
  | tagComponent
  | tagFunction
  | tagIf
  | tagLock
  | tagLoop
  | tagScript
  | tagSwitch
  | tagTry
  ;

lineTag
  : tagAbort
  | tagBreak
  | tagContinue
  | tagDump
  | tagInclude
  | tagLocation
  | tagLog
  | tagParam
  | tagRethrow
  | tagReturn
  | tagSet
  | tagThrow
  ;

/* `cfcomment` must be a parser rule, so that the listener will hear
about it, but it must be implemented as a lexer rule, so that it can
have higher precedence than the `WS` rule. */
cfcomment : CFCOMMENT ;

// Rules that do not yet support "deep" parsing
// --------------------------------------------

tagBreak : CFBREAK ;
tagContinue : CFCONTINUE ;
tagDump : CFDUMP attribute* TE ;
tagFinally  : CFFINALLY block ENDCFFINALLY ;
tagInclude : CFINCLUDE attribute TE ;
tagLocation : CFLOCATION attribute* TE ;
tagLock : CFLOCK attribute* TE block ENDCFLOCK ;
tagLog : CFLOG attribute* TE ;
tagLoop : CFLOOP attribute* TE block ENDCFLOOP ;
tagParam : CFPARAM ;
tagScript : CFSCRIPT ;
tagThrow : CFTHROW ;
tagAbort : CFABORT ;
tagTry : CFTRY block tagCatch* tagFinally? ENDCFTRY ;
tagCatch : CFCATCH attribute? TE block ENDCFCATCH ;
tagRethrow : CFRETHROW ;

tagSwitch : CFSWITCH attribute TE tagCase* tagDefaultCase? ENDCFSWITCH ;
tagCase   : CFCASE attribute TE block ENDCFCASE ;
tagDefaultCase : CFDEFAULTCASE block ENDCFDEFAULTCASE ;

tagComponent : CFCOMPONENT attribute* TE block ENDCFCOMPONENT ;
attribute : ATTRIBUTE_EQ STRING_LITERAL ;

tagFunction
  : CFFUNCTION
  attribute*
  TE
  tagArgument*
  block
  ENDCFFUNCTION
  ;

tagArgument
  : CFARGUMENT
  attribute*
  TE
  ;

// Rules that *do* support "deep" parsing
// --------------------------------------------

tagIf : CFIF expression TE block tagElseIf* tagElse? ENDCFIF ;
tagElseIf : CFELSEIF expression TE block ;
tagElse : CFELSE block ;
tagReturn : CFRETURN expression TE ;
tagSet : CFSET ( assignment | expression ) TE ;

/* TODO: In `assignment` the disjunction of `( operand '=' |
ATTRIBUTE_EQ )` is an ugly hack.  The first half (`operand '='`)
parses assignments with whitespace before the equals sign.  The second
half (`ATTRIBUTE_EQ`) lexes assignments without whitespace.  Is there
any way to clean  this up? */
assignment : ( operand '=' | ATTRIBUTE_EQ ) expression ;

expression : binaryOp | unaryOp | operand | parenthesis ;
parenthesis : '(' expression ')' ;
binaryOp : ( operand | parenthesis ) BINARY_OPERATOR expression ;
unaryOp : operand UNARY_POSTFIX_OPERATOR | UNARY_PREFIX_OPERATOR expression ;

operand : chainable ( '.' chainable )* ;
chainable : atom ( message )* ;
atom : VARIABLE_NAME | BUILTIN_FUNC | literal ;
message : arrayIndex | funcInvoc ;

literal : STRING_LITERAL
  | INT_LITERAL
  | DECIMAL_LITERAL
  | arrayLiteral
  | structLiteral
  ;
arrayLiteral : '[' positionalArguments ']' ;
structLiteral : '{' namedArguments '}' ;

arrayIndex : '[' expression ']' ; /* or a struct index.  TODO: rename */
funcInvoc :
  '('
  ( positionalArguments | namedArguments )?
  ')'
  ;
positionalArguments : expression ( ',' expression )* ;
namedArguments : assignment ( ',' assignment )* ;

// Lexer Rules
// ===========

CFCOMMENT : '<!---' .*? '--->' ;

// Tags with expressions
CFSET       : TS 'set' ;
CFIF        : TS 'if' ;
CFELSEIF    : TS 'elseif' ;
CFRETURN    : TS 'return' ;

// Tags with no attributes or expressions
CFABORT     : TS 'abort' TE ;
CFBREAK     : TS 'break' TE ;
CFCONTINUE  : TS 'continue' TE ;
CFDEFAULTCASE : TS 'defaultcase' TE ;
CFELSE      : TS 'else' TE ;
CFFINALLY   : TS 'finally' TE ;
CFRETHROW   : TS 'rethrow' TE ;
CFTRY       : TS 'try' TE ;

// Tags with attributes
CFARGUMENT  : TS 'argument' ;
CFCASE      : TS 'case' ;
CFCATCH     : TS 'catch' ;
CFCOMPONENT : TS 'component' ;
CFDUMP      : TS 'dump' ;
CFFUNCTION  : TS 'function' ;
CFINCLUDE   : TS 'include' ;
CFLOCATION  : TS 'location' ;
CFLOCK      : TS 'lock' ;
CFLOG       : TS 'log' ;
CFLOOP      : TS 'loop' ;
CFPARAM     : TS 'param' .*? TE ;
CFSWITCH    : TS 'switch' ;
CFTHROW     : TS 'throw' .*? TE ;

// Tags with blocks that should not be parsed
CFSCRIPT    : TS 'script' TE .*? ENDCFSCRIPT ;

// Closing tags
ENDCFCASE   : TC 'case' TE ;
ENDCFCATCH  : TC 'catch' TE ;
ENDCFCOMPONENT : TC 'component' TE ;
ENDCFDEFAULTCASE : TC 'defaultcase' TE ;
ENDCFFINALLY : TC 'finally' TE ;
ENDCFFUNCTION : TC 'function' TE ;
ENDCFIF     : TC 'if' TE ;
ENDCFLOCK   : TC 'lock' TE ;
ENDCFLOOP   : TC 'loop' TE ;
ENDCFSCRIPT : TC 'script' TE ;
ENDCFSWITCH : TC 'switch' TE ;
ENDCFTRY    : TC 'try' TE ;

/* To avoid defining (highly repetitive) lexer rules for every
attribute in CFML, handle attributes generically. */
ATTRIBUTE_EQ : [a-zA-Z]+ '=' ;

TE : '/'? '>' ; // Tag End

INT_LITERAL : '-'? DIGIT+ ;
DECIMAL_LITERAL : '-'? DIGIT+ '.' DIGIT+ ;
STRING_LITERAL
  : '"' DoubleStringCharacter* '"'
  | '\'' SingleStringCharacter* '\''
  ;

/* omg so many operators: http://adobe.ly/cRnrRL */
UNARY_POSTFIX_OPERATOR : '++' | '--' ;
UNARY_PREFIX_OPERATOR : 'NOT' | 'not' ;
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
  | 'mod'
  | 'and'
  | 'or'
  | 'xor'
  | 'eqv'
  | 'imp'
  | 'is'
  | 'equal'
  | 'eq'
  | 'is not'
  | 'not equal'
  | 'neq'
  | 'contains'
  | 'does not contain'
  | 'greater than'
  | 'gt'
  | 'less than'
  | 'lt'
  | 'greater than or equal to'
  | 'gte'
  | 'ge'
  | 'less than or equal to'
  | 'lte'
  | 'le'
  ;

/* Built-in function names are all reserved words, all 470 of them. */
BUILTIN_FUNC
  : 'Abs'
  | 'ACos'
  | 'AddSOAPRequestHeader'
  | 'AddSOAPResponseHeader'
  | 'AjaxLink'
  | 'AjaxOnLoad'
  | 'ApplicationStop'
  | 'ArrayAppend'
  | 'ArrayAvg'
  | 'ArrayClear'
  | 'ArrayContains'
  | 'ArrayDelete'
  | 'ArrayDeleteAt'
  | 'ArrayFind'
  | 'ArrayFindNoCase'
  | 'ArrayInsertAt'
  | 'ArrayIsDefined'
  | 'ArrayIsEmpty'
  | 'ArrayLen'
  | 'ArrayMax'
  | 'ArrayMin'
  | 'ArrayNew'
  | 'ArrayPrepend'
  | 'ArrayResize'
  | 'ArraySet'
  | 'ArraySort'
  | 'ArraySum'
  | 'ArraySwap'
  | 'ArrayToList'
  | 'Asc'
  | 'ASin'
  | 'Atn'
  | 'BinaryDecode'
  | 'BinaryEncode'
  | 'BitAnd'
  | 'BitMaskClear'
  | 'BitMaskRead'
  | 'BitMaskSet'
  | 'BitNot'
  | 'BitOr'
  | 'BitSHLN'
  | 'BitSHRN'
  | 'BitXor'
  | 'CacheGet'
  | 'CacheGetAllIds'
  | 'CacheGetMetadata'
  | 'CacheGetProperties'
  | 'CachePut'
  | 'CacheRemove'
  | 'CacheSetProperties'
  | 'Ceiling'
  | 'CharsetDecode'
  | 'CharsetEncode'
  | 'Chr'
  | 'CJustify'
  | 'Compare'
  | 'CompareNoCase'
  | 'Cos'
  | 'CreateDate'
  | 'CreateDateTime'
  | 'CreateObject'
  | 'CreateODBCDate'
  | 'CreateODBCDateTime'
  | 'CreateODBCTime'
  | 'CreateTime'
  | 'CreateTimeSpan'
  | 'CreateUUID'
  | 'DateAdd'
  | 'DateCompare'
  | 'DateConvert'
  | 'DateDiff'
  | 'DateFormat'
  | 'DatePart'
  | 'Day'
  | 'DayOfWeek'
  | 'DayOfWeekAsString'
  | 'DayOfYear'
  | 'DaysInMonth'
  | 'DaysInYear'
  | 'DE'
  | 'DecimalFormat'
  | 'DecrementValue'
  | 'Decrypt'
  | 'DecryptBinary'
  | 'DeleteClientVariable'
  | 'DeserializeJSON'
  | 'DirectoryCreate'
  | 'DirectoryDelete'
  | 'DirectoryExists'
  | 'DirectoryList'
  | 'DirectoryRename'
  | 'DollarFormat'
  | 'DotNetToCFType'
  | 'Duplicate'
  | 'Encrypt'
  | 'EncryptBinary'
  | 'EntityDelete'
  | 'EntityLoad'
  | 'EntityLoadByExample'
  | 'EntityLoadByPK'
  | 'EntityMerge'
  | 'EntityNew'
  | 'EntityReload'
  | 'EntitySave'
  | 'EntitytoQuery'
  | 'Evaluate'
  | 'Exp'
  | 'ExpandPath'
  | 'FileClose'
  | 'FileCopy'
  | 'FileDelete'
  | 'FileExists'
  | 'FileIsEOF'
  | 'FileMove'
  | 'FileOpen'
  | 'FileRead'
  | 'FileReadBinary'
  | 'FileReadLine'
  | 'FileSeek'
  | 'FileSetAccessMode'
  | 'FileSetAttribute'
  | 'FileSetLastModified'
  | 'FileSkipBytes'
  | 'FileWrite'
  | 'Find'
  | 'FindNoCase'
  | 'FindOneOf'
  | 'FirstDayOfMonth'
  | 'Fix'
  | 'FormatBaseN'
  | 'GenerateSecretKey'
  | 'GetAuthUser'
  | 'GetBaseTagData'
  | 'GetBaseTagList'
  | 'GetBaseTemplatePath'
  | 'GetClientVariablesList'
  | 'GetComponentMetaData'
  | 'GetContextRoot'
  | 'GetCurrentTemplatePath'
  | 'GetDirectoryFromPath'
  | 'GetEncoding'
  | 'GetException'
  | 'GetFileFromPath'
  | 'GetFileInfo'
  | 'GetFunctionCalledName'
  | 'GetFunctionList'
  | 'GetGatewayHelper'
  | 'GetHttpRequestData'
  | 'GetHttpTimeString'
  | 'GetLocale'
  | 'GetLocaleDisplayName'
  | 'GetLocalHostIP'
  | 'GetMetaData'
  | 'GetMetricData'
  | 'GetPageContext'
  | 'GetPrinterInfo'
  | 'GetProfileSections'
  | 'GetProfileString'
  | 'GetReadableImageFormats'
  | 'GetSOAPRequest'
  | 'GetSOAPRequestHeader'
  | 'GetSOAPResponse'
  | 'GetSOAPResponseHeader'
  | 'GetTempDirectory'
  | 'GetTempFile'
  | 'GetTemplatePath'
  | 'GetTickCount'
  | 'GetTimeZoneInfo'
  | 'GetToken'
  | 'GetUserRoles'
  | 'GetWriteableImageFormats'
  | 'Hash'
  | 'Hour'
  | 'HTMLCodeFormat'
  | 'HTMLEditFormat'
  | 'IIf'
  | 'ImageAddBorder'
  | 'ImageBlur'
  | 'ImageClearRect'
  | 'ImageCopy'
  | 'ImageCrop'
  | 'ImageDrawArc'
  | 'ImageDrawBeveledRect'
  | 'ImageDrawCubicCurve'
  | 'ImageDrawLine'
  | 'ImageDrawLines'
  | 'ImageDrawOval'
  | 'ImageDrawPoint'
  | 'ImageDrawQuadraticCurve'
  | 'ImageDrawRect'
  | 'ImageDrawRoundRect'
  | 'ImageDrawText'
  | 'ImageFlip'
  | 'ImageGetBlob'
  | 'ImageGetBufferedImage'
  | 'ImageGetEXIFTag'
  | 'ImageGetHeight'
  | 'ImageGetIPTCTag'
  | 'ImageGetWidth'
  | 'ImageGrayscale'
  | 'ImageInfo'
  | 'ImageNegative'
  | 'ImageNew'
  | 'ImageOverlay'
  | 'ImagePaste'
  | 'ImageRead'
  | 'ImageReadBase64'
  | 'ImageResize'
  | 'ImageRotate'
  | 'ImageRotateDrawingAxis'
  | 'ImageScaleToFit'
  | 'ImageSetAntialiasing'
  | 'ImageSetBackgroundColor'
  | 'ImageSetDrawingColor'
  | 'ImageSetDrawingStroke'
  | 'ImageSetDrawingTransparency'
  | 'ImageSharpen'
  | 'ImageShear'
  | 'ImageShearDrawingAxis'
  | 'ImageTranslate'
  | 'ImageTranslateDrawingAxis'
  | 'ImageWrite'
  | 'ImageWriteBase64'
  | 'ImageXORDrawingMode'
  | 'IncrementValue'
  | 'InputBaseN'
  | 'Insert'
  | 'Int'
  | 'IsArray'
  | 'IsBinary'
  | 'IsBoolean'
  | 'IsCustomFunction'
  | 'IsDate'
  | 'IsDDX'
  | 'IsDebugMode'
  | 'IsDefined'
  | 'IsImage'
  | 'IsImageFile'
  | 'IsInstanceOf'
  | 'IsJSON'
  | 'IsK2ServerABroker'
  | 'IsK2ServerDocCountExceeded'
  | 'IsK2ServerOnline'
  | 'IsLeapYear'
  | 'IsLocalHost'
  | 'IsNull'
  | 'IsNumeric'
  | 'IsNumericDate'
  | 'IsObject'
  | 'IsPDFFile'
  | 'IsPDFObject'
  | 'IsQuery'
  | 'IsSimpleValue'
  | 'IsSOAPRequest'
  | 'IsSpreadsheetFile'
  | 'IsSpreadsheetObject'
  | 'IsStruct'
  | 'IsUserInAnyRole'
  | 'IsUserInRole'
  | 'IsUserLoggedIn'
  | 'IsValid'
  | 'IsWDDX'
  | 'IsXML'
  | 'IsXmlAttribute'
  | 'IsXmlDoc'
  | 'IsXmlElem'
  | 'IsXmlNode'
  | 'IsXmlRoot'
  | 'JavaCast'
  | 'JSStringFormat'
  | 'LCase'
  | 'Left'
  | 'Len'
  | 'ListAppend'
  | 'ListChangeDelims'
  | 'ListContains'
  | 'ListContainsNoCase'
  | 'ListDeleteAt'
  | 'ListFind'
  | 'ListFindNoCase'
  | 'ListFirst'
  | 'ListGetAt'
  | 'ListInsertAt'
  | 'ListLast'
  | 'ListLen'
  | 'ListPrepend'
  | 'ListQualify'
  | 'ListRest'
  | 'ListSetAt'
  | 'ListSort'
  | 'ListToArray'
  | 'ListValueCount'
  | 'ListValueCountNoCase'
  | 'LJustify'
  | 'Location'
  | 'Log'
  | 'Log10'
  | 'LSCurrencyFormat'
  | 'LSDateFormat'
  | 'LSEuroCurrencyFormat'
  | 'LSIsCurrency'
  | 'LSIsDate'
  | 'LSIsNumeric'
  | 'LSNumberFormat'
  | 'LSParseCurrency'
  | 'LSParseDateTime'
  | 'LSParseEuroCurrency'
  | 'LSParseNumber'
  | 'LSTimeFormat'
  | 'LTrim'
  | 'Max'
  | 'Mid'
  | 'Min'
  | 'Minute'
  | 'Month'
  | 'MonthAsString'
  | 'Now'
  | 'NumberFormat'
  | 'ObjectEquals'
  | 'ObjectLoad'
  | 'ObjectSave'
  | 'ORMClearSession'
  | 'ORMCloseSession'
  | 'ORMEvictCollection'
  | 'ORMEvictEntity'
  | 'ORMEvictQueries'
  | 'ORMExecuteQuery'
  | 'ORMFlush'
  | 'ORMGetSession'
  | 'ORMGetSessionFactory'
  | 'ORMReload'
  | 'ParagraphFormat'
  | 'ParseDateTime'
  | 'Pi'
  | 'PrecisionEvaluate'
  | 'PreserveSingleQuotes'
  | 'Quarter'
  | 'QueryAddColumn'
  | 'QueryAddRow'
  | 'QueryConvertForGrid'
  | 'QueryNew'
  | 'QuerySetCell'
  | 'QuotedValueList'
  | 'Rand'
  | 'Randomize'
  | 'RandRange'
  | 'REFind'
  | 'REFindNoCase'
  | 'ReleaseComObject'
  | 'REMatch'
  | 'REMatchNoCase'
  | 'RemoveChars'
  | 'RepeatString'
  | 'Replace'
  | 'ReplaceList'
  | 'ReplaceNoCase'
  | 'REReplace'
  | 'REReplaceNoCase'
  | 'Reverse'
  | 'Right'
  | 'RJustify'
  | 'Round'
  | 'RTrim'
  | 'Second'
  | 'SendGatewayMessage'
  | 'SerializeJSON'
  | 'SetLocale'
  | 'SetProfileString'
  | 'SetVariable'
  | 'Sgn'
  | 'Sin'
  | 'Sleep'
  | 'SpanExcluding'
  | 'SpanIncluding'
  | 'SpreadsheetAddColumn'
  | 'SpreadsheetAddFreezePane'
  | 'SpreadsheetAddImage'
  | 'SpreadsheetAddInfo'
  | 'SpreadsheetAddRow'
  | 'SpreadsheetAddRows'
  | 'SpreadsheetAddSplitPane'
  | 'SpreadsheetCreateSheet'
  | 'SpreadsheetDeleteColumn'
  | 'SpreadsheetDeleteColumns'
  | 'SpreadsheetDeleteRow'
  | 'SpreadsheetDeleteRows'
  | 'SpreadsheetDeleteRowsSpreadsheetDeleteRows'
  | 'SpreadsheetFormatCell'
  | 'SpreadsheetFormatCellRange'
  | 'SpreadsheetFormatColumn'
  | 'SpreadsheetFormatColumns'
  | 'SpreadsheetFormatRow'
  | 'SpreadsheetFormatRows'
  | 'SpreadsheetFormatRowSpreadsheetFormatRow'
  | 'SpreadsheetFormatRowsSpreadsheetFormatRows'
  | 'SpreadsheetGetCellComment'
  | 'SpreadsheetGetCellFormula'
  | 'SpreadsheetGetCellValue'
  | 'SpreadsheetInfo'
  | 'SpreadsheetMergeCells'
  | 'SpreadsheetNew'
  | 'SpreadsheetRead'
  | 'SpreadsheetReadBinary'
  | 'SpreadsheetRemoveSheet'
  | 'SpreadsheetSetActiveSheet'
  | 'SpreadsheetSetActiveSheetNumber'
  | 'SpreadsheetSetCellComment'
  | 'SpreadsheetSetCellFormula'
  | 'SpreadsheetSetCellValue'
  | 'SpreadsheetSetColumnWidth'
  | 'SpreadsheetSetFooter'
  | 'SpreadsheetSetHeader'
  | 'SpreadsheetSetRowHeight'
  | 'SpreadsheetShiftColumns'
  | 'SpreadsheetShiftRows'
  | 'SpreadsheetWrite'
  | 'Sqr'
  | 'StripCR'
  | 'StructAppend'
  | 'StructClear'
  | 'StructCopy'
  | 'StructCount'
  | 'StructDelete'
  | 'StructFind'
  | 'StructFindKey'
  | 'StructFindValue'
  | 'StructGet'
  | 'StructInsert'
  | 'StructIsEmpty'
  | 'StructKeyArray'
  | 'StructKeyExists'
  | 'StructKeyList'
  | 'StructNew'
  | 'StructSort'
  | 'StructUpdate'
  | 'Tan'
  | 'Throw'
  | 'TimeFormat'
  | 'ToBase64'
  | 'ToBinary'
  | 'ToScript'
  | 'ToString'
  | 'Trace'
  | 'TransactionCommit'
  | 'TransactionRollback'
  | 'TransactionSetSavePoint'
  | 'Trim'
  | 'UCase'
  | 'URLDecode'
  | 'URLEncodedFormat'
  | 'URLSessionFormat'
  | 'Val'
  | 'ValueList'
  | 'VerifyClient'
  | 'Week'
  | 'Wrap'
  | 'WriteDump'
  | 'WriteLog'
  | 'WriteOutput'
  | 'XmlChildPos'
  | 'XmlElemNew'
  | 'XmlFormat'
  | 'XmlGetNodeType'
  | 'XmlNew'
  | 'XmlParse'
  | 'XmlSearch'
  | 'XmlTransform'
  | 'XmlValidate'
  | 'Year'
  | 'YesNoFormat'
  ;

/*
A variable name must begin with a letter, underscore, or Unicode
currency symbol. The initial character can by followed by any number
of letters, numbers, underscore characters, and Unicode currency
symbols. A variable name cannot contain spaces. (http://adobe.ly/1btjPoA)

Reserved words ".. must not [be used] for .. variables, user-defined
function names, or custom tag names" (http://adobe.ly/1cMqKML)

This rule must have lower precendence than rules like BINARY_OPERATOR,
to prevent incorrectly lexing an operator as a VARIABLE_NAME.
*/
VARIABLE_NAME : [a-zA-Z_$] [a-zA-Z_$0-9]* ;

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

fragment TS : '<cf' ; // Tag Start
fragment TC : '</cf' ; // Tag Close
