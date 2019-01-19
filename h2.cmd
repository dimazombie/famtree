@REM This script will give you H2 database shel

java -cp h2.jar org.h2.tools.Shell -url "jdbc:h2:./h2db;AUTO_SERVER=TRUE;DB_CLOSE_DELAY=-1" -user "sa" -password "sa"
