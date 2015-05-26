# netdepend - .Net dependency resolver 

## General

This  project uses java code to resolve all dependencies between existing .NET projects and solutions.
This can have any number of usage scenarios.
We developed it at [Picscout](http://www.picscout.com/) to help us calculate what jobs in Jenkins should run as a result of pushed changes in git.

## Project's source overview

The main project is : dependency.
It is divided into a few packages, the main class located here: `picscout.depend.dependency.main.Runner`.
The Runner class can be used in one of two basic modes:

1. Get prjects/ solutions that depend on given projects/solutions. There are some overloaded methods to support this.

2. Calculate dependencies - When calling `CalculateDependencies()` , then Runner will build a dependency map and save it.

If there is already a saved state , than when calling one of the methods to find dependent projects/solutions
they will used the already saved state. Otherwise they will calculate the state themselves (but will not save it).

The idea here is to give the options to pre-calculate the dependency map, to save time when querying for dependencies.

## Configuration

The project uses [log4j](http://logging.apache.org/log4j/2.x/) and [apache commons configuration](https://commons.apache.org/proper/commons-configuration/) frameworks.
Both should be configured in order for it to work properly.
this can be done by passing th following as environment variables:

**log4j.config=** *path to log4j.xml file*

**config_file_path=** *path to config.xml file*

Or you can pass them directly to the Runner class via a specilaized constructor that accepts them.

The configuration file is used to define what folders should be scanned,  where to save persisted state and if to use it,
and what class implements guice injector (see next session).
An example config file can be found under the test/resources folder in the dependency project.


## Extending or replacing implementing classes

Netdepend uses [Guice](https://github.com/google/guice) to inject dependencies between classes.
All the plumbing is done in: picscout.depend.dependency.main.AppInjector

But you can use [beans](https://commons.apache.org/proper/commons-configuration/userguide/howto_beans.html) technology to inject a your own class that does the bindings. This will allow you to change implentation of any class you wish to.

## Using NetDepend with Jenkins

This project was originaly created to be used along side Jenkins and git to detect what jobs should run as a result of pushed changes.
This can be done like this:

1. Use [git hooks](https://git-scm.com/book/es/v2/Customizing-Git-Git-Hooks) to understand what projects/solutions changed as a result of a push.
 
2. Use a groovy system script with the [Groovy plugin](https://wiki.jenkins-ci.org/display/JENKINS/Groovy+plugin) to transltate between Projects/Solutions and Jobs , run the Dependency Project and translate the results back to Jenkins changed Jobs. an Example of such a script is given at:
`deprunner/FindDependencies.groovy`

## Deployment

The dependency project can be downloaded from Github, it is now being processed as a Maven artifact in Main repos.
You can run: 
`mvn package assembly:single` 
to create a JAR.
You can also create an executalble JAR by running the same command on project: `picscout.depend.console`

Javadocs can be found [here](http://foromer4.github.io/netdepend/)

