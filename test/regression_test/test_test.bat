@echo off
if not x==x%jalog_errors% (
  echo Variable jalog_errors not initially cleared.
  exit /B
)


call do_test.bat test_fail
call do_test.bat test_ops
call do_test.bat test_types
call do_test.bat test_cut_
call do_test.bat test_write
call do_test.bat test_writeln
call do_test.bat test_writeq
REM call do_test.bat test__eq_


REM final report
echo.
if x==x%jalog_errors% (
  echo Passed all tests
) else (
  echo JALOG_ERRORS=%jalog_errors%
)
set jalog_errors=
