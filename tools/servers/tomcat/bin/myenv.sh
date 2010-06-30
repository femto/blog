#!/bin/sh
#
#     JAVA_HOME: (Required) if in DEVELOPMENT or using Scooter's autoloader
#                (Optional) if empty, JRE_HOME must be export
#
#      JRE_HOME: (Optional) if empty, JAVA_HOME must be export
#
#     ROOT_PATH: (Required) path to the application directory
#
# CATALINA_HOME: (Required) path to Tomcat installation directory
#
# CATALINA_BASE: (Required) path to your own Tomcat directory
#
#     JAVA_OPTS: (Required) Java runtime options, must at least contain "-Dtomcat=true"
#
# Note: 1. change_me means manual change required.
#
#       2. root_path and jre_home properties are automatically
#          export by Scooter's initialization process.
#

export JAVA_HOME={change_me}

export JRE_HOME=C:\Program Files\Java\jre6

export ROOT_PATH=E:\scooter-project\blog

export CATALINA_HOME={change_me}

export CATALINA_BASE=$ROOT_PATH/tools/servers/tomcat

export app_logs_dir=$ROOT_PATH/logs

export JAVA_OPTS="-Dtomcat=true -Dapp.logs=$app_logs_dir"

echo "myenv     JAVA_HOME: " $JAVA_HOME
echo "myenv      JRE_HOME: " $JRE_HOME
echo "myenv     ROOT_PATH: " $ROOT_PATH
echo "myenv CATALINA_HOME: " $CATALINA_HOME
echo "myenv CATALINA_BASE: " $CATALINA_BASE
echo "myenv     JAVA_OPTS: " $JAVA_OPTS
