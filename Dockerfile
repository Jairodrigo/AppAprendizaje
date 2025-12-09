FROM maven:3.9.5-eclipse-temurin-17 AS build

COPY . /app
WORKDIR /app

RUN mvn clean install -DskipTests

---

FROM eclipse-temurin:17-jre-alpine

# Crea y establece el directorio de trabajo donde estará la aplicación
WORKDIR /app 

# Copia el JAR del stage 'build' al nuevo WORKDIR /app
COPY --from=build /app/target/*.jar /app/app.jar

# ENTRYPOINT que ejecuta el JAR. Nota: apuntamos a /app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
