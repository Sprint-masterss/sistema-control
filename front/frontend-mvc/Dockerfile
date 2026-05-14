FROM eclipse-temurin:17-jdk
LABEL authors="samzhand"
WORKDIR /app
COPY target/*.jar app.jar
# ENV
ENTRYPOINT ["java", "-jar", "app.jar"]