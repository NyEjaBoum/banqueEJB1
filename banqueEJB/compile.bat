@echo off
REM Compile comptecourant
cd comptecourant
mvn clean package
cd ..

REM Compile centralisateur
cd centralisateur
mvn clean package
cd ..

echo Compilation finished!
pause