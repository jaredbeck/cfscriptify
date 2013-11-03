#!/usr/bin/env bash
set -e

function abs_path_to_cfs_jar {
  echo -n "$(dir_of_this_script)/../target/cfscriptify-0.0.1.jar"
}

function cfscriptify {
  local jar="$(abs_path_to_cfs_jar)"
  verify_jar_exists "$jar"
  java -jar "$jar" <&0
}

# http://bit.ly/mS6MnB
function dir_of_this_script {
  echo -n "$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
}

function verify_jar_exists {
  if [ ! -f "$1" ]; then
    echo "File not found: $1" 1>&2
    echo 'Use maven to build jar. See readme.' 1>&2
    exit 1
  fi
}

cfscriptify
