@echo off
REM filepath: c:\Users\NyEja\Documents\itu\S5\MrTahina\banqueEJB\deploy.bat
REM Copy centralisateur.war
copy centralisateur\target\centralisateur.war C:\wildfly-37.0.1.Final\standalone\deployments\

REM Copy comptecourant.jar
copy comptecourant\target\comptecourant-1.0-SNAPSHOT.jar C:\wildfly-37.0.1.Final\standalone\deployments\

REM Copy pret.jar
copy pret\target\pret-1.0-SNAPSHOT.jar C:\wildfly-37.0.1.Final\standalone\deployments\

echo Deployments copied!
pause