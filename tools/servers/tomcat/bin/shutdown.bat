@echo off

call "myenv.bat"

rem Validate CATALINA_BASE
if exist "%CATALINA_BASE%\bin\myenv.bat" goto okBase
echo The CATALINA_BASE environment variable is not defined correctly
echo This environment variable is needed to run this program
goto end
:okBase

rem Validate CATALINA_HOME
if exist "%CATALINA_HOME%\bin\catalina.bat" goto okHome
echo The CATALINA_HOME environment variable is not defined correctly
echo This environment variable is needed to run this program
echo You may set it in "%CATALINA_BASE%\bin\myenv.bat"
goto end

:okHome
rem Validate JAVA_HOME or JRE_HOME
if not "%JAVA_HOME%" == "" goto gotJava
if not "%JRE_HOME%" == "" goto gotJre
echo Either JAVA_HOME or JRE_HOME environment variable is not defined
echo This environment variable is needed to run this program
echo You may set it in "%CATALINA_BASE%\bin\myenv.bat"
goto end

:gotJava
if exist "%JAVA_HOME%\bin\java.exe" goto okJava
echo The JAVA_HOME environment variable is not defined correctly
echo This environment variable is needed to run this program
echo You may set it in "%CATALINA_BASE%\bin\myenv.bat"
goto end

:gotJre
if exist "%JRE_HOME%\bin\java.exe" goto okJava
echo The JRE_HOME environment variable is not defined correctly
echo This environment variable is needed to run this program
echo You may set it in "%CATALINA_BASE%\bin\myenv.bat"
goto end

:okJava

call "%CATALINA_HOME%\bin\shutdown.bat"

:end