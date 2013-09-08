CFScriptify
===========

Converts common CFML tags to cfscript using pure Java.

Usage
-----

    bin/build.sh
    src/test/test.rb
    bin/run.sh my.cfm

See Appendix A for a list of supported CFML tags.

### Whitespace

Output line endings are LF, but CR and CRLF are acceptable as
input.  Output is indented with tabs, but I'd welcome a pull
request for spaces.

### Sublime Text 2 Plugin

Nothing polished yet, but you can use `docs/sublime/cfscriptify.py`
as a starting point.

1. Tools -> New Plugin ..
1. Paste in `src/sublime/cfscriptify.py`
1. Update all the `/Users/jared` paths (sorry)
1. Select some CFML
1. In sublime's python shell (ctrl-backtick)
    * `view.run_command("cfscriptify")`
1. Or, bind a key, eg.
    * `{ "keys": ["ctrl+alt+r"], "command": "cfscriptify" }`

### Apologies for not using ant or maven

They both looked painfully complicated.  Maybe it was the XML.

Thanks
------

Thanks to the guys from [OpenBD][4] who wrote the [ANTLR3 grammer][5]
for CFML, I'll be leaning on it heavily as I write an [ANTLR4][7] grammar.
According to SVN blame, that was `andy` and `alan` (perhaps
[Andy Wu and Alan Williamson][6]?).

References
----------

* [ANTLR 4 Runtime API][1]
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

Appendix A: Supported CFML Tags
-------------------------------

### Supported (14)

cfabort
cfbreak
cfcatch
cfelse
cfelseif
cfif
cfinclude
cfparam
cfrethrow
cfscript
cfset
cfsilent
cfthrow
cftry

### Partially supported (1)

[cfloop][9] is supported for list, array, and from/to/step.
Support is planned for loop over query, structure, and condition.
Loop over date range is rare, but wouldn't be too difficult to
implement.  Finally, loop over file is rare and has no convenient
cfscript equivalent.

### Not Supported Yet (14)

cfargument
cfcase
cfcomponent
cfcontinue
cfdefaultcase
cfdump
cffinally
cfflush
cffunction
cflocation
cflock
cflog
cfreturn
cfswitch

### No Plans to Support (118)

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
[9]: http://adobe.ly/14mmCe5
[10]: https://gist.github.com/dtao/2726609
[11]: http://stackoverflow.com/questions/2502833/python-store-output-of-subprocess-popen-call-in-a-string
[12]: http://stackoverflow.com/questions/89228/calling-an-external-command-in-python
[13]: http://docs.sublimetext.info/en/latest/extensibility/plugins.html
