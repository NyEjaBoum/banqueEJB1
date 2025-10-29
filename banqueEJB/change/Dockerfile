FROM quay.io/wildfly/wildfly:37.0.1.Final-jdk17

COPY setup-datasource.cli /opt/jboss/wildfly/bin/
COPY configure-ejb-remoting.cli /opt/jboss/wildfly/bin/

RUN /opt/jboss/wildfly/bin/jboss-cli.sh --file=/opt/jboss/wildfly/bin/setup-datasource.cli
RUN /opt/jboss/wildfly/bin/jboss-cli.sh --file=/opt/jboss/wildfly/bin/configure-ejb-remoting.cli

EXPOSE 8080 9990

CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]