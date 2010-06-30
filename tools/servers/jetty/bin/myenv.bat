@echo off
rem 
rem     JAVA_HOME: (Required) if in DEVELOPMENT, must be a JDK
rem 
rem     ROOT_PATH: (Required) path to the application directory
rem 
rem    JETTY_HOME: (Required) path to Jetty installation directory
rem 
rem    JETTY_BASE: (Required) path to your own Jetty directory
rem
rem Note: 1. change_me means manual change required.
rem 
rem       2. root_path, app_name, db_type, and tools_dir properties are 
rem          automatically set by Scooter's initialization process. 
rem 
rem       3. Scooter's autoloader requires JAVA_HOME.
rem

set JAVA_HOME={change_me}

set ROOT_PATH=E:\scooter-project\blog

set JETTY_HOME={change_me}

set JETTY_BASE=%ROOT_PATH%\tools\servers\jetty

set jdbc_jar_path=%ROOT_PATH%\webapps\blog\WEB-INF\lib\mysql.jar

set tools_path=%ROOT_PATH%\tools\servers\helper\tools-1.6\tools.jar

rem 
rem The following are derived environment variables
rem 

set app_logs_dir=%ROOT_PATH%\logs
set jetty_logs_dir=%JETTY_BASE%\logs
set jetty_work_dir=%JETTY_BASE%\work
set jetty_class_path=%tools_path%;%jdbc_jar_path%
