FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY . .

RUN chmod +x mvnw
RUN ./mvnw clean package -DskipTests

RUN cp target/*.jar app.jar

EXPOSE 8081

FROM eclipse-temurin:17-jdk

LABEL authors="samzhand"
WORKDIR /app
COPY target/*.jar app.jar
# ENV
ENTRYPOINT ["java", "-jar", "app.jar"]
