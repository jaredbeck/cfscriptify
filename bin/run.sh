#!/usr/bin/env bash
set -e

function cfscriptify {

  # TODO: Omit `/Users/jared` ..
  # Can maven spit out the classpath?
  local jar1="/Users/jared/git/cfscriptify/target/cfscriptify-0.0.1.jar"
  local jar2="/Users/jared/.m2/repository/org/antlr/antlr4-runtime/4.1/antlr4-runtime-4.1.jar"
  local jar3="/Users/jared/.m2/repository/org/apache/commons/commons-lang3/3.1/commons-lang3-3.1.jar"
  local classpath="$jar1:$jar2:$jar3"

  # CFScriptify.main() reads from stdin, so we pass stdin to java with
  # `<&0`. Also, main() takes zero  arguments, but we pass them anyway
  # so that checkUsage() can print a helpful message.
  java -classpath "$classpath" CFScriptify "$@" <&0
}

cfscriptify "$@"
