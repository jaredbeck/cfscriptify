#!/usr/bin/env bash
set -e

function clean {
  git clean -Xdfq
}

function generate_parser {
  java -jar antlr-4.0-complete.jar src/CFML.g4
}

function compile {
  mkdir classes
  local classpath="classes:antlr-4.0-complete.jar:commons-lang3-3.1.jar"
  javac -sourcepath src -classpath "$classpath" src/*.java -d classes
}

clean
generate_parser
compile
