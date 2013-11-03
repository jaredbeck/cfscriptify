#!/usr/bin/env bash
set -e

# `abs_path_to_cfs_jar` - returns absolute path to the .jar
# built by `mvn assembly:assembly`
function abs_path_to_cfs_jar {
  local jar_filename="cfscriptify-0.0.1-jar-with-dependencies.jar"
  local cfs_dir="$(dir_of_this_script)"
  echo -n "$cfs_dir/../target/$jar_filename"
}

function cfscriptify {
  local jar="$(abs_path_to_cfs_jar)"
  verify_jar_exists "$jar"
  java -jar "$jar" <&0
}

# `dir_of_this_script` - See http://bit.ly/mS6MnB
function dir_of_this_script {
  echo -n "$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
}

function verify_jar_exists {
  if [ ! -f "$1" ]; then
    echo "File not found: $1" 1>&2
    echo 'Did you run `mvn assembly:assembly` yet?' 1>&2
    exit 1
  fi
}

cfscriptify
