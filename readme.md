CFScriptify
===========

Converts [CFML][17] to [cfscript][16] using Java.  ([video demo][25])

Usage
-----

Assuming you have [maven][24] 3 (standard on mac os 10.7, 10.8)

    mvn --version
    mvn -q package
    # edit bin/run.sh, fixing the hardcoded jar paths (sorry!)
    bin/test.rb
    bin/run.sh < src/test/input/01.cfm

### Limitations

- Tags: Only the most common 20% of tags are supported (See Appendix A).
- Operators: Mixed-case, eg. CoNtAiNs, is legal in CFML, but not supported here.

### Whitespace Output

- Line Endings: LF (but CR and CRLF are acceptable as input)
- Indentation: Tabs, but I'd welcome a pull request for spaces

### Sublime Text 2 Plugin

Nothing polished yet, but here's a starting point:

1. Make sure the tests (described above) pass
1. Tools -> New Plugin ..
1. Paste in `src/main/sublime/cfscriptify.py`
1. Update all the `/Users/jared` paths (sorry)
1. Select some CFML
1. Try your new plugin in sublime's python shell (ctrl-backtick)
    * `view.run_command("cfscriptify")`
1. If it works, bind a key, eg.
    * `{ "keys": ["ctrl+alt+super+s"], "command": "cfscriptify" }`

References
----------

* [Adobe ColdFusion 10 documentation][18]
* [ANTLR 4 Runtime API][1]
    * [ErrorType (ANTLR 4 Tool 4.1 API)][21] (describes some runtime errors)
    * [ANTLR 4 and AST visitors][2]
    * [ANTLR 4 tree inject/rewrite operator][3]
* [openbd.org][4]
    * [/trunk/src/com/naryx/tagfusion/cfm/parser/CFML.g][5] (ANTLR3 grammer)
* [cftag2cfscript][8] (similar project, but written in ColdFusion)
* Sublime Text 2
    * [Plugins - Sublime Text Unofficial Documentation][13]
    * [randomize.py][10] (example of replacing the selected text)
    * [Python - store output of subprocess.Popen call in a string][11]
    * [Calling an external command in Python][12]
* Left recursion
    * [Removing left recursion][20]
    * [ANTLR - left recursion removal assistance][19]

Appendix A: Supported CFML Tags
-------------------------------

### Supported (21)

cfabort
cfbreak
cfcatch
cfcomponent
cfcontinue
cfdefaultcase
cfelse
cfelseif
cffinally
cfif
cfinclude
cflog
cfparam
cfrethrow
cfreturn
cfscript
cfset
cfsilent
cfswitch
cfthrow
cftry

### Partially supported (4)

[cfargument][23] is supported except that `hint` and `displayName`
attributes are discarded, because the syntax for those attributes
is [awkward][22] in cfscript.  I'd welcome a pull request.

[cfcase][14] is supported for single values.  List values are not supported yet.

[cffunction][15] is supported for the following attributes:
name, access, and returnType.  The `output` attribute is
ignored, and all other attributes will cause an error.

[cfloop][9] is supported for list, array, and from/to/step.
Support is planned for loop over query, structure, and condition.
Loop over date range is rare, but wouldn't be too difficult to
implement.  Finally, loop over file is rare and has no convenient
cfscript equivalent.

### Not Supported Yet (3)

cfdump
cflocation
cflock

### No Plans to Support (119)

These tags either do not have convenient cfscript equivalents, have
complicated varients (eg. cffile), or are too rare to be worth the
trouble.  Pull requests, however, are welcome.

cfapplication
cfassociate
cfajaximport
cfajaxproxy
cfapplet
cfcache
cfcalendar
cfchart
cfchartdata
cfchartseries
cfcontent
cfcol
cfcollection
cfcookie
cfdbinfo
cfdirectory
cfdiv
cfdocument
cfdocumentitem
cfdocumentsection
cferror
cfexchangecalendar
cfexchangeconnection
cfexchangecontact
cfexchangefilter
cfexchangemail
cfexchangetask
cfexecute
cfexit
cffeed
cffile
cffileupload
cfflush
cfform
cfformgroup
cfformitem
cfftp
cfgrid
cfgridcolumn
cfgridrow
cfgridupdate
cfheader
cfhtmlhead
cfhttp
cfhttpparam
cfimage
cfimap
cfimport
cfindex
cfinput
cfinsert
cfinterface
cfinvoke
cfinvokeargument
cflayout
cflayoutarea
cfldap
cflogin
cfloginuser
cflogout
cfmail
cfmailparam
cfmailpart
cfmap
cfmapitem
cfmediaplayer
cfmenu
cfmenuitem
cfmessagebox
cfNTauthenticate
cfobject
cfobjectcache
cfoutput
cfpdf
cfpdfform
cfpdfformparam
cfpdfparam
cfpdfsubform
cfpod
cfpop
cfpresentation
cfpresentationslide
cfpresenter
cfprint
cfprocessingdirective
cfprocparam
cfprocresult
cfprogressbar
cfproperty
cfquery
cfqueryparam
cfregistry
cfreport
cfreportparam
cfsavecontent
cfschedule
cfsearch
cfselect
cfsetting
cfsharepoint
cfslider
cfspreadsheet
cfsprydataset
cfstoredproc
cftable
cftextarea
cfthread
cftimer
cftooltip
cftrace
cftransaction
cftree
cftreeitem
cfupdate
cfwddx
cfwindow
cfxml
cfzip
cfzipparam


[1]: http://www.antlr.org/api/Java/index.html
[2]: http://stackoverflow.com/questions/14667781/antlr-4-and-ast-visitors
[3]: http://t7263.codeinpro.us/q/515024e9e8432c0426262341
[4]: http://openbd.org/
[5]: http://websvn.openbd.org/websvn/filedetails.php?repname=OpenBD&path=%2Ftrunk%2Fsrc%2Fcom%2Fnaryx%2Ftagfusion%2Fcfm%2Fparser%2FCFML.g
[6]: http://openbd.org/about/
[7]: http://www.antlr.org/
[8]: https://github.com/pirategaspard/cftag2cfscript
[9]: http://adobe.ly/14mmCe5 "cfloop"
[10]: https://gist.github.com/dtao/2726609
[11]: http://stackoverflow.com/questions/2502833/python-store-output-of-subprocess-popen-call-in-a-string
[12]: http://stackoverflow.com/questions/89228/calling-an-external-command-in-python
[13]: http://docs.sublimetext.info/en/latest/extensibility/plugins.html
[14]: http://adobe.ly/SGHQR1 "cfcase"
[15]: http://adobe.ly/1edVKpf "cffunction"
[16]: http://adobe.ly/15u3Khl "cfscript"
[17]: http://help.adobe.com/en_US/ColdFusion/9.0/CFMLRef/index.html "Adobe ColdFusion 9 CFML Reference"
[18]: https://learn.adobe.com/wiki/display/coldfusionen/Home "Adobe ColdFusion 10 documentation"
[19]: http://stackoverflow.com/questions/11388099/antlr-left-recursion-removal-assistance
[20]: http://en.wikipedia.org/wiki/Left_recursion#Removing_left_recursion
[21]: http://www.antlr.org/api/Java/org/antlr/v4/tool/ErrorType.html
[22]: http://adobe.ly/9naZLp "Defining components and functions in CFScript"
[23]: http://adobe.ly/16LAPWv "cfargument"
[24]: http://maven.apache.org/
[25]: http://youtu.be/PlLoye06UOc
