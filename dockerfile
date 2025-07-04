# ----------- ETAPA 1: Build con Maven -----------
FROM maven:3.9.6-eclipse-temurin-17-alpine AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# ----------- ETAPA 2: Solo Java para correr el JAR -----------
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/msclientes-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
