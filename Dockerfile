# Stage 1: Build the Maven project
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Stage 2: Run the app
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/sdpproject-*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
