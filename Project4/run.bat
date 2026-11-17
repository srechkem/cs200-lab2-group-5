@echo off
REM Compiles and runs the ChocAn program. Double-click this file to use it.
cd /d "%~dp0"

REM Use Java from the PATH if it is installed there.
REM If not, use the Java that comes with Android Studio.
set "JAVA_BIN="
where javac >nul 2>nul
if errorlevel 1 (
    if exist "C:\Program Files\Android\Android Studio\jbr\bin\javac.exe" set "JAVA_BIN=C:\Program Files\Android\Android Studio\jbr\bin\"
)

echo Compiling...
"%JAVA_BIN%javac" -d out src\*.java
if errorlevel 1 (
    echo.
    echo Compiling failed. See the errors above.
    pause
    exit /b 1
)

echo Starting ChocAn...
echo.
"%JAVA_BIN%java" -cp out Main
pause
