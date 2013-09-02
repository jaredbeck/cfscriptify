CFScriptify
===========

Converts CFML tags to cfscript using pure Java.

Usage
-----

Given the following input file `01.cfm`

    <cfset x = 'foo'>
    <cfset x = true>
    <cfabort>

Compile and run

    ./build.sh
    ./run.sh test/input/01.cfm

Should produce

    x = 'foo';
    x = true;
    abort;

Apologies for not using ant or maven
------------------------------------

They both looked painfully complicated.  Maybe it was the XML.
I'm used to the pleasent experience of [bundler][9].

Parser Generator
----------------

`build.sh` uses the included jar of [ANTLR4][7] to generate the
lexer and parser.

Thanks
------

Thanks to the guys from [OpenBD][4] who wrote the [ANTLR3 grammer][5]
for CFML, I'll be leaning on it heavily as I write an ANTLR4 grammar.
According to SVN blame, that was `andy` and `alan` (perhaps
[Andy Wu and Alan Williamson][6]?).

References
----------

* [ANTLR 4 Runtime API][1]
* Useful examples:
    * [ANTLR 4 and AST visitors][2]
    * [ANTLR 4 tree inject/rewrite operator][3]
* [openbd.org][4]
    * [/trunk/src/com/naryx/tagfusion/cfm/parser/CFML.g][5] (ANTLR3 grammer)
* [cftag2cfscript][8] (similar project, but written in ColdFusion)

[1]: http://www.antlr.org/api/Java/index.html
[2]: http://stackoverflow.com/questions/14667781/antlr-4-and-ast-visitors
[3]: http://t7263.codeinpro.us/q/515024e9e8432c0426262341
[4]: http://openbd.org/
[5]: http://websvn.openbd.org/websvn/filedetails.php?repname=OpenBD&path=%2Ftrunk%2Fsrc%2Fcom%2Fnaryx%2Ftagfusion%2Fcfm%2Fparser%2FCFML.g
[6]: http://openbd.org/about/
[7]: http://www.antlr.org/
[8]: https://github.com/pirategaspard/cftag2cfscript
[9]: http://bundler.io/
