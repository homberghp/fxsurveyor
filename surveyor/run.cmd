@echo off
set java=target/fxtriangulate/bin/java
if not exist %java% cls & echo Maven... & call mvn -P fx -Dexec.mainClass=fxtriangulate.App javafx:jlink
echo.
target/fxtriangulate/bin/java -m fxtriangulate/fxtriangulate.App

