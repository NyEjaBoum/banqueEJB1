FROM quay.io/wildfly/wildfly:37.0.1.Final-jdk17

COPY setup-datasource.cli /opt/jboss/wildfly/bin/
COPY configure-ejb-remoting.cli /opt/jboss/wildfly/bin/

RUN /opt/jboss/wildfly/bin/jboss-cli.sh --file=/opt/jboss/wildfly/bin/setup-datasource.cli
RUN /opt/jboss/wildfly/bin/jboss-cli.sh --file=/opt/jboss/wildfly/bin/configure-ejb-remoting.cli

COPY src/main/resources/cours.csv /opt/jboss/wildfly/standalone/change/cours.csv
COPY target/change-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/

EXPOSE 8080 9990

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]

