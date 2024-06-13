@echo off
if not x==x%jalog_errors% (
  echo Variable jalog_errors not initially cleared.
  exit /B
)

REM 1.5 tests

REM goto a1

call do_test_jar_w_option.bat "-r=/" test_openread
call do_test_jar_w_option.bat "-r=/" test_readdevice
call do_test_jar_w_option.bat "-r=/" test_readln
call do_test_jar_w_option.bat "-w=test_dir/" test_openwrite
call do_test_jar_w_option.bat "-w=test_dir/" test_writedevice

call do_test_write_to_file.bat
call do_test_write_to_files.bat
call do_test_append_to_file.bat
call do_test_deletefile.bat

call do_test_save.bat

call do_test_jar_w_option.bat "-r=test_dir/" test_existfile

call do_test_jar.bat test_file_str
call do_test_jar.bat test_term_str

:a1

call do_test_jar.bat ..\regression_test_material\consult_test_file
call do_test_jar.bat D:\wa\JalogTeam\Jalog\test\regression_test_material\consult_test_file_abs

REM goto end

REM 1.4 tests

call do_test_jar.bat test_retract
call do_test_jar.bat test_retractall
call do_test_jar.bat test_strings
call do_test_jar.bat test_frontchar
call do_test_jar.bat test_frontstr
call do_test_jar.bat test_fronttoken
call do_test_jar.bat test_findall
call do_test_jar.bat test_member
call do_test_jar.bat test_isname
call do_test_jar.bat test_str_int

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
call do_test_jar.bat test_consult_data_3
call do_test_jar.bat test_consult_data_4
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
call do_test_resource.bat test_resource resources/testdata.pro resources/testprog.pro
call do_test_resource.bat test_subresource resources/testdata.pro resources/sub/testdata.pro

:end

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
