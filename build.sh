#!/usr/bin/env bash
set -e

function clean {
  git clean -Xdfq
}

function generate_parser {
  java -jar antlr-4.0-complete.jar src/main/antlr/CFML.g4
}

function compile {
  local main="src/main"
  local clspth="target:antlr-4.0-complete.jar:commons-lang3-3.1.jar"
  local srcpth="$main/antlr:$main/java"
  local opts="-sourcepath $srcpth -classpath $clspth -d target"
  javac $opts $main/java/*.java
}

clean
generate_parser
compile
