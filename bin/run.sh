#!/usr/bin/env bash
set -e

function cfscriptify {
  local classpath="target:antlr-4.0-complete.jar:commons-lang3-3.1.jar:"

  # CFScriptify.main() reads from stdin, so we pass stdin to java with
  # `<&0`. Also, main() takes zero  arguments, but we pass them anyway
  # so that checkUsage() can print a helpful message.
  java -classpath "$classpath" CFScriptify "$@" <&0
}

cfscriptify "$@"
