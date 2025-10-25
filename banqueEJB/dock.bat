@echo off
REM Compile le projet Maven
cd /d %~dp0
cd change
call mvn clean install

REM Build l'image Docker
call docker build -t change-ejb .

REM Arrête le conteneur existant s'il tourne
call docker stop change-ejb

REM Démarre le conteneur existant (ou crée-le s'il n'existe pas)
call docker start change-ejb || docker run -d -p 8081:8080 --name change-ejb change-ejb

echo Déploiement terminé. Le EJB change est disponible sur le port 8081.
pause

@REM docker exec -it change-ejb /opt/jboss/wildfly/bin/add-user.sh -a -u nyeja -p nyeja