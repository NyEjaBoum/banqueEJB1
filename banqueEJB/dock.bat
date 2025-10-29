@REM @echo off
@REM REM Compile le projet Maven
@REM cd /d %~dp0
@REM cd change
@REM call mvn clean install

@REM REM Build l'image Docker (optionnel si tu ne changes pas le Dockerfile)
@REM call docker build -t change-ejb .

@REM REM Copie le nouveau JAR dans le conteneur existant
@REM call docker cp target/change-1.0-SNAPSHOT.war change-ejb:/opt/jboss/wildfly/standalone/deployments/change-1.0-SNAPSHOT.war

@REM REM (Optionnel) Copie le nouveau cours.csv si besoin
@REM call docker cp src/main/resources/cours.csv change-ejb:/opt/jboss/wildfly/standalone/change/cours.csv

@REM REM Redeploy le JAR via le CLI WildFly
@REM call docker exec change-ejb /opt/jboss/wildfly/bin/jboss-cli.sh --connect --command="deployment redeploy change-1.0-SNAPSHOT.jar"

@REM echo Déploiement terminé. Le EJB change est disponible sur le port 8081.

@REM @REM docker run -d -p 8081:8080 --name change-ejb change-ejb create conteneur
@REM pause



@echo off
REM Compile le projet Maven
cd /d %~dp0
cd change
call mvn clean install

REM Build l'image Docker si besoin
call docker build -t change-ejb .

REM Supprime et recrée le conteneur
docker stop change-ejb
docker rm change-ejb
docker run -d -p 8081:8080 --name change-ejb change-ejb

REM Copie le JAR (EJB) dans le conteneur
docker cp target/change-1.0-SNAPSHOT.war change-ejb:/opt/jboss/wildfly/standalone/deployments/

REM Copie le WAR (REST) si besoin
REM docker cp target/change-1.0-SNAPSHOT.war change-ejb:/opt/jboss/wildfly/standalone/deployments/

REM Copie le fichier cours.csv
docker cp src/main/resources/cours.csv change-ejb:/opt/jboss/wildfly/standalone/change/cours.csv
docker exec -it change-ejb /opt/jboss/wildfly/bin/add-user.sh -a -u nyeja -p nyeja
echo Déploiement terminé. Le EJB change est disponible sur le port 8081.
pause
