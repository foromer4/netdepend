# netdepend - .Net dependency resolver

## General

This  project uses java code to resolve all dependencies between existing projects and solutions.
This can have any number of usage scenarios.
e.g. The project was designed to calculate what jobs in Jenkins should run as a result of pushed changes in git.

## Project's source overview

The main project is : dependency.
It is divided into a few packages, the main class Runner is located under package: picscout.depend.dependency.main.
The Runner class can be used in one of two basic modes:
1. Get prjects/ solutions that depend on given projects/solutions. There are some overloaded methods to support this.
2. Calculate dependencies - When calling CalculateDependencies() , then Runner will build a dependency map and save it.

If there is already a saved state , than when calling one of the methods to find dependent projects/solutions
they will used the already saved state. Otherwise they will calculate the state themselves (but will not save it).

The idea here is to give the options to pre-calculate the dependency map, to save time when querying for dependencies.

## Configuration

The project uses log4j and apache commons configuration frameworks.
Both should be configured in order for it to work properly.
this can be done by passing th following as environment variables:
*log4j.config= <path to log4j.xml file>
*config_file_path = <path to config.xml file>

Or you can pass them directly to the Runner via a specilaized constructor that accepts them.

The configuration file is used to define what folders should be scanned,  where to save persisted state and if to use it,
and what class implements guice injector (see next session).
An example config file can be found under the test/resources folder in the dependency project.


## Extending or replacing implenting classes

## Using with Jenkins


## Deployment


