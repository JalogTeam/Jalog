if not exist classes  mkdir classes

javac -d classes -cp classes -sourcepath src src/io/github/JalogTeam/jalog/Jalog.java

xcopy src\META-INF classes\META-INF /S /I /Y

jar -cfM jalog.jar -C classes .

