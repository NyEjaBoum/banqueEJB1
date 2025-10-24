FROM jboss/wildfly:latest

COPY target/change-1.0-SNAPSHOT.jar /opt/jboss/wildfly/standalone/deployments/
COPY src/main/java/com/change/cours.csv /opt/jboss/wildfly/standalone/change/cours.csv

EXPOSE 8080