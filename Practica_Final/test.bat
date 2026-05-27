@echo off
setlocal

set "JAVA_HOME=%USERPROFILE%\.jdks\openjdk-25.0.2"
if not exist "%JAVA_HOME%" set "JAVA_HOME=C:\Program Files\Zulu\zulu-25"
if not exist "%JAVA_HOME%" set "JAVA_HOME=C:\Program Files\JetBrains\IntelliJ IDEA 2025.3.2\jbr"

set "PATH=%JAVA_HOME%\bin;%PATH%"

set "MAVEN_HOME=%~dp0..\maven\apache-maven-3.9.9"
set "PATH=%MAVEN_HOME%\bin;%PATH%"

echo Running tests...
call mvn test
pause
