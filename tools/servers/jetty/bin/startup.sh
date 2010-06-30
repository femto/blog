#!/bin/sh

. myenv.sh

exec $JAVA_HOME/bin/java -Droot.path=$ROOT_PATH -Dapp.logs=$app_logs_dir -Djetty.home=$JETTY_HOME -Dmyjetty.home=$JETTY_BASE -Djetty.logs=$jetty_logs_dir -Djava.io.tmpdir=$jetty_work_dir -Djetty.class.path=$jetty_class_path -jar $JETTY_HOME/start.jar $JETTY_BASE/etc/jetty.xml
