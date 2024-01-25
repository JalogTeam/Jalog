REM This file must be compatible to test_append_to_file.pro
copy test_dir\appendstart.txt test_dir\appendtest.txt
java -jar ..\..\jalog.jar -w=test_dir/ test_append_to_file.pro 1>test_append_to_file.ans 2>&1
echo Exit status: %ERRORLEVEL% ; >> test_append_to_file.ans
fc test_append_to_file.ans test_append_to_file.ref
if ERRORLEVEL 1 (
  echo test_appende_to_file : Errorlevel %ERRORLEVEL%
  set /A jalog_errors=%jalog_errors%+1
)
fc test_dir\appendtest.txt test_dir\appendtest.ref
if ERRORLEVEL 1 (
  echo test_append_to_file : Errorlevel %ERRORLEVEL%
  set /A jalog_errors=%jalog_errors%+1
) else (
REM
)
echo ----------
