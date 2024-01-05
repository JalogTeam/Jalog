java -jar ..\..\jalog.jar %1 %2.pro %3 %4 %5 %6 %7 %8 %9 1> %2.ans 2>&1
echo Exit status: %ERRORLEVEL% ; >> %2.ans
fc %2.ans %2.ref
REM fc %2.ans %2.pro
if ERRORLEVEL 1 (
  echo %2 : Errorlevel %ERRORLEVEL%
  set /A jalog_errors=%jalog_errors%+1
) else (
REM
)
echo ----------
