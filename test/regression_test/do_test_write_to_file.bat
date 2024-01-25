REM This file must be compatible to write_to_file.pro
java -jar ..\..\jalog.jar -w=test_dir/ test_write_to_file.pro 1>test_write_to_file.ans 2>&1
echo Exit status: %ERRORLEVEL% ; >> test_write_to_file.ans
fc test_write_to_file.ans test_write_to_file.ref
if ERRORLEVEL 1 (
  echo test_write_to_file : Errorlevel %ERRORLEVEL%
  set /A jalog_errors=%jalog_errors%+1
)
fc test_dir\writetest3.txt test_dir\writetest3.ref
if ERRORLEVEL 1 (
  echo test_write_to_file : Errorlevel %ERRORLEVEL%
  set /A jalog_errors=%jalog_errors%+1
) else (
REM
)
echo ----------
