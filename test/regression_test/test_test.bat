@echo off
if not x==x%jalog_errors% (
  echo Variable jalog_errors not initially cleared.
  exit /B
)


REM call do_test.bat test_fail
REM call do_test.bat test_ops
call do_test.bat test_types
REM call do_test.bat test_cut_
REM call do_test.bat test_write
REM call do_test.bat test_writeln
REM call do_test.bat test_writeq
REM * nl is done in each test. Does not need own case.
REM call do_test.bat test__eq_
REM call do_test.bat test_foreach_
REM call do_test.bat test_consult
REM call do_test.bat test_assertz
REM call do_test.bat test_not
REM call do_test.bat test_bound
REM call do_test.bat test_trap
REM call do_test.bat test_exit_0
REM call do_test.bat test_exit_1
REM call do_test.bat test_exit_50
REM call do_test.bat test_

REM final report
echo.
if x==x%jalog_errors% (
  echo Passed all tests
) else (
  echo JALOG_ERRORS=%jalog_errors%
)
set jalog_errors=
