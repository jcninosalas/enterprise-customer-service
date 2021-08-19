FROM maven:3.6.3-openjdk-17 AS build

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
