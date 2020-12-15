copy ..\..\jalog.jar test.jar
jar -uf test.jar resources/resourcetest.pro resources/testdata.pro
java -jar test.jar res:resources/resourcetest.pro > %1.ans
echo Exit status: %ERRORLEVEL% ; >> %1.ans
del test.jar
fc %1.ans %1.ref
REM fc %1.ans %1.pro
if ERRORLEVEL 1 (
  echo %1 : Errorlevel %ERRORLEVEL%
  set /A jalog_errors=%jalog_errors%+1
) else (
REM
)
echo ----------
