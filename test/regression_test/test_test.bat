@echo off
if not x==x%jalog_errors% (
  echo Variable jalog_errors not initially cleared.
  exit /B
)

REM testing fail

REM call do_test.bat test_fail
REM call do_test.bat test_ops
REM call do_test.bat test_types
REM call do_test.bat test_cut_
REM call do_test.bat test_write
REM call do_test.bat test_writeln
REM call do_test.bat test_writeq
call do_test.bat test__eq_


REM final report
echo.
if x==x%jalog_errors% (
  echo Passed all tests
) else (
  echo JALOG_ERRORS=%jalog_errors%
)
set jalog_errors=
