# Fase 1: Compilación de la aplicación usando Maven y Java 17
FROM maven:3.8.4-openjdk-20 AS build
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests

# Fase 2: Ejecución de la aplicación en un contenedor ligero
FROM openjdk:20-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]