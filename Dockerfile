# Etapa de build con Maven y JDK 21
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

# Copiamos pom.xml y descargamos dependencias
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copiamos el código fuente
COPY src ./src

# Construimos el jar
RUN mvn clean package -DskipTests

# Imagen final con JDK 21
FROM eclipse-temurin:21-jdk

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
