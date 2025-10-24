@echo off
REM Compile le projet Maven
cd /d %~dp0
cd change
call mvn clean install

REM Build l'image Docker
call docker build -t change-ejb .

REM Supprime le conteneur existant s'il existe
call docker rm -f change-ejb

REM Lance le conteneur Docker
call docker run -d -p 8081:8080 --name change-ejb change-ejb

echo Déploiement terminé. Le EJB change est disponible sur le port 8081.