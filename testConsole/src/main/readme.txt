test console to run the the dependency plugin.
To build a jar with assemblies run maven like this:
mvn clean package assembly:single
this will create an assembly under target. run it with run.bat.

run like this: if you do not pass any params, than cals dpendencies will run. if you pass a list of solution names (with.sln) than
the the runner will calculate all dependant solutions.