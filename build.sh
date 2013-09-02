#!/usr/bin/env bash
set -e
git clean -Xfq
java -jar antlr-4.0-complete.jar CFML.g4
javac -classpath ".:antlr-4.0-complete.jar:commons-lang3-3.1.jar:" *.java
