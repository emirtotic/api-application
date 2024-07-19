FROM eclipse-temurin:17-jre-focal
## Koristi zvaničnu OpenJDK sliku
#FROM openjdk:17-jdk-slim

# Postavi radni direktorijum
WORKDIR /app

# Kopiraj JAR fajl
COPY target/CovidTracker-0.0.1-SNAPSHOT.jar app.jar

# Otvori port 8080
EXPOSE 8080

# Pokreni aplikaciju
ENTRYPOINT ["java", "-jar", "app.jar"]
