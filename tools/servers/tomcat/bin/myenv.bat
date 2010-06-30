@echo off
rem 
rem     JAVA_HOME: (Required) if in DEVELOPMENT or using Scooter's autoloader
rem                (Optional) if empty, JRE_HOME must be set
rem 
rem      JRE_HOME: (Optional) if empty, JAVA_HOME must be set
rem 
rem     ROOT_PATH: (Required) path to the application directory
rem 
rem CATALINA_HOME: (Required) path to Tomcat installation directory
rem 
rem CATALINA_BASE: (Required) path to your own Tomcat directory
rem 
rem     JAVA_OPTS: (Required) Java runtime options, must at least contain "-Dtomcat=true"
rem
rem Note: 1. change_me means manual change required.
rem 
rem       2. root_path and jre_home properties are automatically 
rem          set by Scooter's initialization process. 
rem

set JAVA_HOME={change_me}

set JRE_HOME=C:\Program Files\Java\jre6

set ROOT_PATH=E:\scooter-project\blog

set CATALINA_HOME={change_me}

set CATALINA_BASE=%ROOT_PATH%\tools\servers\tomcat

set app_logs_dir=%ROOT_PATH%\logs

set JAVA_OPTS=-Dtomcat=true -Dapp.logs=%app_logs_dir%
