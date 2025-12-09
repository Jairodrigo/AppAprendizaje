# -- Etapa de Compilación (Build Stage) --
# Usamos una imagen que ya tiene Java (JDK 17) y Maven.
FROM maven:3.9.5-eclipse-temurin-17 AS build

# Copia el código fuente al contenedor y configura el directorio de trabajo
COPY . /app
WORKDIR /app

# Ejecuta el comando de compilación de Maven (genera el JAR)
# Ya que usamos una imagen Maven oficial, no necesitamos './mvnw'
RUN mvn clean install -DskipTests

# -- Etapa de Ejecución (Runtime Stage) --
# Usamos una imagen más ligera solo con Java Runtime (JRE) para la ejecución
FROM eclipse-temurin:17-jre-alpine

# Copia el JAR compilado de la etapa de 'build' a la etapa de ejecución
COPY --from=build /app/target/*.jar app.jar

# Define el comando de inicio de la aplicación
CMD java -jar /app.jar
