FROM eclipse-temurin:11-jre-alpine
VOLUME /tmp
COPY target/cicd-demo-*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]