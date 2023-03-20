FROM eclipse-temurin:19-jre-focal
MAINTAINER YR

ADD ./target/audit-service-1.0-SNAPSHOT.jar /app/

CMD ["java", "-Xmx200m", "-jar", "/app/audit-service-1.0-SNAPSHOT.jar"]

EXPOSE 8080 8089