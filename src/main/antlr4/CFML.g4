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

funcInvoc : ( dottedRef | BUILTIN_FUNC ) '(' positionalArguments? ')' ;
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
/* Known issue: It is legal for a variable name to begin with a capital
letter. However, this lexer rule requires a lowercase first letter to
avoid an operator like EQ from being interpreted as a variable name. */
VARIABLE_NAME : [a-z_$] [a-zA-Z_$0-9]* ;

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
