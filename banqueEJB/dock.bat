@echo off
REM Compile le projet Maven
cd /d %~dp0
cd change
call mvn clean install

REM Build l'image Docker (optionnel si tu ne changes pas le Dockerfile)
call docker build -t change-ejb .

REM Copie le nouveau JAR dans le conteneur existant
call docker cp target/change-1.0-SNAPSHOT.war change-ejb:/opt/jboss/wildfly/standalone/deployments/change-1.0-SNAPSHOT.jar

REM (Optionnel) Copie le nouveau cours.csv si besoin
call docker cp src/main/resources/cours.csv change-ejb:/opt/jboss/wildfly/standalone/change/cours.csv

REM Redeploy le JAR via le CLI WildFly
call docker exec change-ejb /opt/jboss/wildfly/bin/jboss-cli.sh --connect --command="deployment redeploy change-1.0-SNAPSHOT.jar"

echo Déploiement terminé. Le EJB change est disponible sur le port 8081.
pause