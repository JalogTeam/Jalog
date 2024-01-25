REM This file must be compatible to write_to_files.pro
java -jar ..\..\jalog.jar -w=test_dir/ test_write_to_files.pro 1>test_write_to_files.ans 2>&1
echo Exit status: %ERRORLEVEL% ; >> test_write_to_files.ans
fc test_write_to_files.ans test_write_to_files.ref
if ERRORLEVEL 1 (
  echo test_write_to_files : Errorlevel %ERRORLEVEL%
  set /A jalog_errors=%jalog_errors%+1
)
fc test_dir\writetest4.txt test_dir\writetest4.ref
if ERRORLEVEL 1 (
  echo test_write_to_files : Errorlevel %ERRORLEVEL%
  set /A jalog_errors=%jalog_errors%+1
)
fc test_dir\writetest5.txt test_dir\writetest5.ref
REM fc %2.ans %2.pro
if ERRORLEVEL 1 (
  echo test_write_to_files : Errorlevel %ERRORLEVEL%
  set /A jalog_errors=%jalog_errors%+1
) else (
REM
)
echo ----------
