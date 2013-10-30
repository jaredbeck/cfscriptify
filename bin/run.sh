#!/usr/bin/env bash
set -e

function cfscriptify {

  # Get the absolute path to the directory that this script (run.sh)
  # lives in. http://bit.ly/mS6MnB
  local cfs_dir="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

  # Relative to cfs_dir, it's easy to find the "uber jar" built
  # by `mvn assembly:assembly`
  local uber_jar="$cfs_dir/../target/cfscriptify-0.0.1-jar-with-dependencies.jar"

  if [ ! -f "$uber_jar" ]; then
    echo "File not found: $uber_jar" 1>&2
    echo 'Did you run `mvn assembly:assembly` yet?' 1>&2
  fi

  # CFScriptify.main() reads from stdin, so we pass stdin to java with
  # `<&0`. Also, main() takes zero  arguments, but we pass them anyway
  # so that checkUsage() can print a helpful message.
  java -jar "$uber_jar" "$@" <&0
}

cfscriptify "$@"
