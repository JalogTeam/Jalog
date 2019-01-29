if not x==x%jalog_errors% exit /B
java -cp ..\..\src Jalog test_fail.pro > test_fail.ans
fc test_fail.ans test_fail.ref
REM fc test_fail.ans test_fail.pro
if ERRORLEVEL 1 (
  echo tuli 1 tai suurempi
  set /A jalog_errors=%jalog_errors%+1
) else (
  echo tuli 0 tai pienempi
)
if x==x%jalog_errors% (
  echo no errors
) else (
  echo JALOG_ERRORS=%jalog_errors%
)
set jalog_errors=
