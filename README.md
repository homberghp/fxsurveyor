# fxsurveyor
fx modular for javafx+jlink and jpackage


This project is a demo project for the following:

- Testing UI (JavaFX) applications with JUnit, AssertJ and TestFX.
- Java Platform Module (Java 9 a.ka. Jigsaw) System modules and maven modules.
- Build a deployable applications with jlink (universal) and jpackage (platform) specific.


The project has a top level project called fxsurveyor which resides in the root of this github project.


## Build

To run the project:
```
mvn -P fx compile
cd surveyor
mvn -P fx javafx:run

```

To test the project, in the root dir do

```
mvn -P fx java test
```

To create both a jlink image and a deploy-able package do (from the root dir)

```
mvn -P fx compile
cd surveyor
mvn -P fx javafx:jlink jpackage:jpackage
```
