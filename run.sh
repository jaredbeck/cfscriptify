#!/usr/bin/env bash
set -e

function cfscriptify {
  local classpath="target:antlr-4.0-complete.jar:commons-lang3-3.1.jar:"
  java -classpath "$classpath" CFScriptify "$@"
}

cfscriptify "$*"
