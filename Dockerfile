
FROM maven:3.9.5-eclipse-temurin-17 AS build

COPY . /app
WORKDIR /app

RUN mvn clean install -DskipTests

FROM eclipse-temurin:17-jre-alpine
COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
