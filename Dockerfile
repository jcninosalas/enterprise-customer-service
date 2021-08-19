FROM maven:3.6.3-openjdk-14-slim AS build
#FROM maven:3.8.2-amazoncorretto-16 AS build

RUN mkdir -p /workspace
WORKDIR /workspace
COPY pom.xml /workspace
COPY src /workspace/src
RUN mvn -B package --file pom.xml -DskipTests

FROM adoptopenjdk/openjdk11

RUN adduser --system --group spring
USER spring:spring
ARG JAR_FILE=/workspace/target/*.jar
COPY --from=build ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]




#FROM adoptopenjdk/openjdk11
#
#RUN adduser --system --group spring
#USER spring:spring
#ARG JAR_FILE=target/*.jar
#COPY ${JAR_FILE} enterprise-customer-service-0.0.1-SNAPSHOT.jar
#ENTRYPOINT ["java", "-jar", "/enterprise-customer-service-0.0.1-SNAPSHOT.jar"]