FROM adoptopenjdk/openjdk11

RUN adduser --system --group spring
USER spring:spring
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} enterprise-customer-service-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar", "/enterprise-customer-service-0.0.1-SNAPSHOT.jar"]