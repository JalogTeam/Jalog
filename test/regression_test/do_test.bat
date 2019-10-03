java -cp ..\..\src io/github/JalogTeam/jalog/Jalog %1.pro %2 %3 %4 %5 %6 %7 %8 %9 > %1.ans
echo Exit status: %ERRORLEVEL% ; >> %1.ans
fc %1.ans %1.ref
REM fc %1.ans %1.pro
if ERRORLEVEL 1 (
  echo %1 : Errorlevel %ERRORLEVEL%
  set /A jalog_errors=%jalog_errors%+1
) else (
REM
)
echo ----------
