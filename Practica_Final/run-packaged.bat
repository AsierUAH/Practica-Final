@echo off
setlocal

set "JAVA_HOME=%USERPROFILE%\.jdks\openjdk-25.0.2"
if not exist "%JAVA_HOME%" set "JAVA_HOME=C:\Program Files\Zulu\zulu-25"
if not exist "%JAVA_HOME%" set "JAVA_HOME=C:\Program Files\JetBrains\IntelliJ IDEA 2025.3.2\jbr"

set "PATH=%JAVA_HOME%\bin;%PATH%"

if not exist "%~dp0target\practica-final-1.0-SNAPSHOT.jar" (
    echo Packaged jar not found. Run package.bat first.
    pause
    exit /b 1
)

java -jar "%~dp0target\practica-final-1.0-SNAPSHOT.jar"
pause
