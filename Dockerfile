FROM quay.io/wildfly/wildfly

LABEL authors="Micke"

EXPOSE 8080

ADD  ./target/SA-Lab2-1.0-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/

ENTRYPOINT ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]