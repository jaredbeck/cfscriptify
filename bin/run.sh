#!/usr/bin/env bash
set -e

function cfscriptify {

  if [ ! -f 'target/cfscriptify-0.0.1.jar' ]; then
    echo "File not found: target/cfscriptify-0.0.1.jar" 1>&2
    echo 'Did you run `mvn package` yet?' 1>&2
  fi

  if [ ! -f 'classpath.txt' ]; then
    echo "File not found: classpath.txt" 1>&2
    echo 'Did you run `mvn package` yet?' 1>&2
  fi

  local classpath="$(cat classpath.txt):target/cfscriptify-0.0.1.jar"

  # CFScriptify.main() reads from stdin, so we pass stdin to java with
  # `<&0`. Also, main() takes zero  arguments, but we pass them anyway
  # so that checkUsage() can print a helpful message.
  java -classpath "$classpath" CFScriptify "$@" <&0
}

cfscriptify "$@"
