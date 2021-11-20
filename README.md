# fxsurveyor
fx modular for javafx+jlink and jpackage


This project is a demo project for the following:

- Testing UI (JavaFX) applications with JUnit, AssertJ and TestFX.
- Java Platform Module (Java 9 a.ka. Jigsaw) System modules and maven modules.
- Build a deployable applications with jlink (universal) and jpackage (platform) specific.


The project has a top level project called fxsurveyor which resides in the root of this github project.


## Build

Each sequence of commands assumes you are in the root directory of the project

To run the project to know what it does:
```
mvn -P fx compile
cd fxtriangulate
mvn -P fx -DskipTests -Dmaven.javadoc.skip=true install
cd ../surveyor
mvn -P fx javafx:run

```

## Run tests
To test the project, in the root dir do:

```
mvn -P fx java test
```

Note that you should not move the mouse, because that is controlled by the test script. Any interference from the user may fail the tests.
There a two tests, one for the fxtriangulate component and one for the application.

## Installable image for your OS

To create both a jlink image and a deploy-able package do (from the root dir):

```
mvn -P fx clean compile
cd fxtriangulate
mvn -P fx -DskipTests -Dmaven.javadoc.skip=true install
cd ../surveyor
mvn -P fx javafx:jlink jpackage:jpackage
```

Happy coding.
