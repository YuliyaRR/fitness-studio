FROM eclipse-temurin:19-jre-focal
MAINTAINER YR

ADD ./target/mail-service-1.0-SNAPSHOT.jar /app/

CMD ["java", "-Xmx200m", "-jar", "/app/mail-service-1.0-SNAPSHOT.jar"]

EXPOSE 8080 8089
