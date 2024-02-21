REM This file must be compatible to test_save.pro
del test_dir\savetest1.txt
del test_dir\savetest2.txt
java -jar ..\..\jalog.jar -w=test_dir/ test_save.pro 1>test_save.ans 2>&1
echo Exit status: %ERRORLEVEL% ; >> test_save.ans
fc test_save.ans test_save.ref
if ERRORLEVEL 1 (
  echo test_save : Errorlevel %ERRORLEVEL%
  set /A jalog_errors=%jalog_errors%+1
)
fc test_dir\savetest1.txt test_dir\savetest1.ref
if ERRORLEVEL 1 (
  echo test_save : Errorlevel %ERRORLEVEL%
  set /A jalog_errors=%jalog_errors%+1
) else (
REM
)
fc test_dir\savetest2.txt test_dir\savetest2.ref
if ERRORLEVEL 1 (
  echo test_save : Errorlevel %ERRORLEVEL%
  set /A jalog_errors=%jalog_errors%+1
) else (
REM
)
echo ----------
