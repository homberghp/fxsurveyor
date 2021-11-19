#!/bin/bash

if [ ! -f target/surveyor/bin/java ]; then
    mvn -P fx javafx:jlink
fi

target/surveyor/bin/surveyor.sh

