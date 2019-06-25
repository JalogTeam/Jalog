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
REM * nl is done in each test. Does not need own case.
call do_test.bat test__eq_
call do_test.bat test_foreach_
call do_test.bat test_consult
call do_test.bat test_assertz
call do_test.bat test_not
call do_test.bat test_bound
call do_test.bat test_trap
call do_test.bat test_exit_0
call do_test.bat test_exit_1
call do_test.bat test_exit_50
REM call do_test.bat test_

call do_test_interface.bat test_interface

REM final report
echo.
if x==x%jalog_errors% (
  echo Passed all tests
) else (
  echo JALOG_ERRORS=%jalog_errors%
)
set jalog_errors=
