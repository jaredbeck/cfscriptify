#!/usr/bin/env bash
set -e
java -jar antlr-4.0-complete.jar CFML.g4
export CLASSPATH=".:antlr-4.0-complete.jar:commons-lang3-3.1.jar:"
javac *.java
