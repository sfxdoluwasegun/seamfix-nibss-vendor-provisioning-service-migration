FROM registry.seamfix.com/seamfix/microservice_wildfly:13.0.0.Final
ADD customization/startup.sh /opt/jboss/wildfly/customization/startup.sh
ADD target/license_service.war /opt/jboss/wildfly/customization/device-provision-service.war

RUN chmod +x /opt/jboss/wildfly/customization/startup.sh

EXPOSE 9990 9993 8009 8080 8443 4712 4713 25

CMD ["/opt/jboss/wildfly/customization/startup.sh"]

