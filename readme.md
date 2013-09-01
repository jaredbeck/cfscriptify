CFScriptify
===========

Usage
-----

Given the following input file `01.cfm`

    <cfset x = 'foo'>
    <cfset x = true>
    <cfabort>

Compile and run

    javac *.java
    java CFScriptify test/input/01.cfm

Should produce

    x = 'foo';
    x = true;
    abort;

Parser
------

Install ANTLR4

    export CLASSPATH=".:/usr/local/lib/antlr-4.0-complete.jar:$CLASSPATH"
    alias antlr4='java -jar /usr/local/lib/antlr-4.0-complete.jar'

Generate and compile parser

    antlr4 CFML.g4
    javac *.java

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

[1]: http://www.antlr.org/api/Java/index.html
[2]: http://stackoverflow.com/questions/14667781/antlr-4-and-ast-visitors
[3]: http://t7263.codeinpro.us/q/515024e9e8432c0426262341
[4]: http://openbd.org/
[5]: http://websvn.openbd.org/websvn/filedetails.php?repname=OpenBD&path=%2Ftrunk%2Fsrc%2Fcom%2Fnaryx%2Ftagfusion%2Fcfm%2Fparser%2FCFML.g
[6]: http://openbd.org/about/
