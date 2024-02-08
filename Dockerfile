FROM tomcat:jre21-temurin-jammy

COPY target/demo-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps-javaee/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]