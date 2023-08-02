@echo off
if not x==x%jalog_errors% (
  echo Variable jalog_errors not initially cleared.
  exit /B
)

REM 1.4 tests

call do_test_jar.bat test_retract
call do_test_jar.bat test_retractall
call do_test_jar.bat test_strings
call do_test_jar.bat test_frontchar
call do_test_jar.bat test_fronttoken
call do_test_jar.bat test_findall
call do_test_jar.bat test_member

REM regression tests

call do_test_jar.bat test_fail
call do_test_jar.bat test_ops
call do_test_jar.bat test_types
call do_test_jar.bat test_cut_
call do_test_jar.bat test_write
call do_test_jar.bat test_writeln
call do_test_jar.bat test_writeq
REM * nl is done in each test. Does not need own case.
call do_test_jar.bat test__eq_
call do_test_jar.bat test_foreach_
call do_test_jar.bat test_consult
call do_test_jar.bat test_consult_again
call do_test_jar.bat test_consult_data
call do_test_jar.bat test_consult_data_2
call do_test_jar.bat test_consult_dir
call do_test_jar.bat test_asserta
call do_test_jar.bat test_assertz
call do_test_jar.bat test_not
call do_test_jar.bat test_bound
call do_test_jar.bat test_free
call do_test_jar.bat test_trap
call do_test_jar.bat test_exit_0
call do_test_jar.bat test_exit_1
call do_test_jar.bat test_exit_1p1
call do_test_jar.bat test_exit_50
call do_test_jar.bat test_command_line_arguments "-a=b" -c d
call do_test_jar.bat test_command_line_no_arguments
call do_test_jar.bat test_dynamic
REM call do_test_jar.bat test_

call do_test_interface.bat test_interface
call do_test_redirect.bat test_redirect
call do_test_resource.bat test_resource resources/testdata.pro
call do_test_resource.bat test_subresource resources/testdata.pro resources/sub/testdata.pro
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
