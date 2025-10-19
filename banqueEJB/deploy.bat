@echo off
REM Compile comptecourant
cd comptecourant
call mvn clean install
cd ..

REM Compile centralisateur
cd centralisateur
call mvn clean install
cd ..

REM Compile pret
cd pret
call mvn clean install
cd ..

REM Déploiement dans WildFly
call copy centralisateur\target\centralisateur.war C:\wildfly-37.0.1.Final\standalone\deployments\
call copy comptecourant\target\comptecourant-1.0-SNAPSHOT.jar C:\wildfly-37.0.1.Final\standalone\deployments\
call copy pret\target\pret-1.0-SNAPSHOT.jar C:\wildfly-37.0.1.Final\standalone\deployments\

echo Compilation et déploiement terminés !
pause