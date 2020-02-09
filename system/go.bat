@echo off
echo ±àÒëjar......
cd ..\common
pause
call mvn clean install -Dmaven.test.skip=true package
pause
cd ..\system
pause
rd target /s/q
pause
call mvn clean install -Dmaven.test.skip=true package
pause
cd  target

pause
ren *.jar blog.jar
java -jar blog.jar
exit