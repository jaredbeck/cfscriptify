#!/usr/bin/env bash
set -e
java -classpath ".:antlr-4.0-complete.jar:commons-lang3-3.1.jar:" CFScriptify "$*"
