c:
cd\
cd C:\git\netdepend\testConsole\target
java -jar -Dlog4j.configuration=file:"C:\temp\log4j2.xml" -Dconfig_file_path="C:\temp\config.xml" console-0.0.1-SNAPSHOT-jar-with-dependencies.jar stam3.sln PicScout.Inf.Messaging.sln stam.sln
pause