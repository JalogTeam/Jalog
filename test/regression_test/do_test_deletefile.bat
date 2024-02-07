REM This file must be compatible to test_deletefile.pro
copy test_dir\deletestart.txt test_dir\deletetest.txt
copy test_dir\deletestart.txt deletetest.txt
java -jar ..\..\jalog.jar -w=test_dir/ test_deletefile.pro 1>test_deletefile.ans 2>&1
echo Exit status: %ERRORLEVEL% ; >> test_deletefile.ans
if EXIST test_dir\deletetest.txt ECHO ERROR: Delete test_dir\deletetest.txt failed >> test_deletefile.ans
if NOT EXIST test_dir\deletetest.txt ECHO OK: Delete test_dir\deletetest.txt succeeded >> test_deletefile.ans
if EXIST deletetest.txt ECHO OK: Delete deletetest.txt failed >> test_deletefile.ans
if NOT EXIST deletetest.txt ECHO ERROR: Delete deletetest.txt succeeded >> test_deletefile.ans
fc test_deletefile.ans test_deletefile.ref
if ERRORLEVEL 1 (
  echo test_deletefile : Errorlevel %ERRORLEVEL%
  set /A jalog_errors=%jalog_errors%+1
)
echo ----------
