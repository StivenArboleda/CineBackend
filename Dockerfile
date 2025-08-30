# Etapa 1: Construcción con Maven
FROM maven:3.9.8-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-jdk-jammy
WORKDIR /app

# Copiar el jar generado
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto (debe coincidir con el de application.properties)
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java","-jar","app.jar"]
