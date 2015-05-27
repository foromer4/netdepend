# NetDepend - .Net dependency resolver

## Introduction

This open source project uses java code to resolve all dependencies between existing .NET projects and solutions.
This can have any number of usage scenarios.
We  at [Picscout](http://www.picscout.com/) developed it to help us calculate what jobs in Jenkins should run as a result of pushed changes in git.

## Project's source overview

The main project is : dependency.
It is divided into a few packages, the main class located here: 
[com.picscout.depend.dependency.main.Runner](https://github.com/foromer4/netdepend/blob/master/dependency/src/main/java/picscout/depend/dependency/main/Runner.java)

The Runner class can be used in one of two basic modes:

1. Get prjects/solutions that depend on the given projects/solutions. There are some overloaded methods to support this.

2. Calculate dependencies - When calling `CalculateDependencies()` , then Runner will build a dependency map and save it.

If there is already a saved state , than when calling one of the methods to find dependent projects/solutions
they will use the already saved state. Otherwise they will calculate the state themselves (but will not save it).

The idea here is to give the option to pre-calculate the dependency map,in order to save time when querying for dependencies.

## Configuration

The project uses [log4j](http://logging.apache.org/log4j/2.x/) and [apache commons configuration](https://commons.apache.org/proper/commons-configuration/) frameworks.
Both should be configured in order for it to work properly.
this can be done by setting the following environment variables:

**log4j.config=** *path to log4j.xml file*

**config_file_path=** *path to config.xml file*

Or you can pass them directly to the Runner class via a specilaized constructor that accepts them.

The configuration file is used to define what folders should be scanned,  where to save persisted state and if to use it,
and what class implements Guice injector (see next session).
An example config file can be found under the test/resources folder [here](https://github.com/foromer4/netdepend/blob/master/dependency/src/test/resources/config.xml).


## Extending or replacing the implementing classes

Netdepend uses [Guice](https://github.com/google/guice) to inject dependencies between classes.
All the plumbing is done in: 
[com.picscout.depend.dependency.main.AppInjector](https://github.com/foromer4/netdepend/blob/master/dependency/src/main/java/picscout/depend/dependency/main/AppInjector.java)

But you can use [beans](https://commons.apache.org/proper/commons-configuration/userguide/howto_beans.html) technology to inject a your own class that does the bindings. This will allow you to change implentation of any class you wish to.

## Using NetDepend with Jenkins

This project was originaly created to be used along side Jenkins and git to detect what jobs should run as a result of pushed changes.
This can be done like this:

1. Use [git hooks](https://git-scm.com/book/es/v2/Customizing-Git-Git-Hooks) to understand what projects/solutions changed as a result of a push.
 
2. Use a groovy system script with the [Groovy plugin](https://wiki.jenkins-ci.org/display/JENKINS/Groovy+plugin) to transltate between Projects/Solutions and Jobs , run the Dependency Project and translate the results back to Jenkins changed Jobs. an Example of such a script is given at:
[FindDependencies.groovy](https://github.com/foromer4/netdepend/blob/master/ndeprunner/src/ndeprunner/FindDependencies.groovy)

## Deployment

The dependency project can be downloaded from Github, it is now being processed as a Maven artifact in Main repos.
You can run: 
`mvn package assembly:single` 
to create a JAR.
You can also create an executalble JAR by running the same command on project: `com.picscout.depend.console`

Javadocs can be found [here](http://foromer4.github.io/netdepend/)

**This project was developed as an open source project by:**

![alt tag](https://picscout.s3.amazonaws.com/Images/Picscout_Logo.png)

