#!/bin/bash

# clean maven slate
rm -fr ~/.m2/repository/io/github/homberghp

mvn -P fx compile
cd fxtriangulate
mvn -P fx -DskipTests -Dmaven.javadoc.skip=true install
cd ../surveyor
mvn -P fx javafx:run


