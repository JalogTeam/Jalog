rmdir /S classes
mkdir classes
javac Update_Version.java
java Update_Version src/io/github/JalogTeam/jalog/Version.java

javac -d classes -cp classes -sourcepath src src/io/github/JalogTeam/jalog/Jalog.java

xcopy src\META-INF classes\META-INF /S /I /Y
xcopy resources classes  /S /I /Y
jar -cfM jalog.jar -C classes .

