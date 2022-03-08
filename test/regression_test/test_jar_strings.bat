@echo off
if not x==x%jalog_errors% (
  echo Variable jalog_errors not initially cleared.
  exit /B
)

REM 1.4 tests

call do_test_jar.bat test_strings

REM final report
echo.
java -jar ..\..\jalog.jar -v
echo.
if x==x%jalog_errors% (
  echo Passed all tests
) else (
  echo JALOG_ERRORS=%jalog_errors%
)
set jalog_errors=
