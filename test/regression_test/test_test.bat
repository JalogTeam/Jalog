@echo off
if not x==x%jalog_errors% (
  echo Variable jalog_errors not initially cleared.
  exit /B
)

REM testing fail

call do_test.bat test_fail
call do_test.bat test_ops

REM final report
echo.
if x==x%jalog_errors% (
  echo Passed all tests
) else (
  echo JALOG_ERRORS=%jalog_errors%
)
set jalog_errors=
