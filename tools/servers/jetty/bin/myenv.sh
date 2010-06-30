#!/bin/sh
#
#     JAVA_HOME: (Required) if in DEVELOPMENT, must be a JDK
#
#     ROOT_PATH: (Required) path to the application directory
#
#    JETTY_HOME: (Required) path to Jetty installation directory
#
#    JETTY_BASE: (Required) path to your own Jetty directory
#
# Note: 1. change_me means manual change required.
#
#       2. root_path, app_name, db_type, and tools_dir properties are
#          automatically set by Scooter's initialization process.
#
#       3. Scooter's autoloader requires JAVA_HOME.
#

export JAVA_HOME={change_me}

export ROOT_PATH=E:\scooter-project\blog

export JETTY_HOME={change_me}

export JETTY_BASE=$ROOT_PATH/tools/servers/jetty

export jdbc_jar_path=$ROOT_PATH/webapps/blog/WEB-INF/lib/mysql.jar

export tools_path=$ROOT_PATH/tools/servers/helper/tools-1.6/tools.jar

#
# The following are derived environment variables
#

export app_logs_dir=$ROOT_PATH/logs
export jetty_logs_dir=$JETTY_BASE/logs
export jetty_work_dir=$JETTY_BASE/work
export jetty_class_path=$tools_path:$jdbc_jar_path

echo "myenv        JAVA_HOME: " $JAVA_HOME
echo "myenv         JRE_HOME: " $JRE_HOME
echo "myenv        ROOT_PATH: " $ROOT_PATH
echo "myenv       JETTY_HOME: " $JETTY_HOME
echo "myenv       JETTY_BASE: " $JETTY_BASE
echo "myenv jetty_class_path: " $jetty_class_path
