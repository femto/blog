This is a directory where you put all database specific jdbc client jar files.

mysql.jar: is mysql-connector-java-bin.jar with version-5.1.X.
oracle.jar: is classes12.jar shipped with Oracle JDeveloper(10.1.2).

If you want to try code generator with your own jdbc client jar file, name it "mydb.jar" and place it here and also under /WEB-INF/lib/.

Code generator will then look for "mydb.jar" file when generating code.

Please notice that we cannot include mysql.jar or oracle.jar because of their licensing restrictions. 
You can download a copy from their sites. After you download the jars, you need to put a copy under
both here and WEB-INF\lib directory.