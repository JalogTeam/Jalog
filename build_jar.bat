rmdir /S classes
mkdir classes
javac Update_Version.java
java Update_Version src/io/github/JalogTeam/jalog/Version.java

javac -d classes -cp classes -sourcepath src src/io/github/JalogTeam/jalog/Jalog.java

xcopy src\META-INF classes\META-INF /S /I /Y
xcopy resources classes  /S /I /Y

mkdir classes\src\io\github\JalogTeam\jalog

copy src\io\github\JalogTeam\jalog\Version.java classes\src\io\github\JalogTeam\jalog\Version.java
copy src\io\github\JalogTeam\jalog\BuiltIns.java classes\src\io\github\JalogTeam\jalog\BuiltIns.java 

jar -cfM jalog.jar -C classes .

