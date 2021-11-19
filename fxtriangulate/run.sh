#!/bin/bash

if [ ! -f target/fxtriangulate/bin/java ]; then
    mvn -P fx -Dexec.mainClass=fxtriangulate.App javafx:jlink
fi

target/fxtriangulate/bin/java -m fxtriangulate/fxtriangulate.App

