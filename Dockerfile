FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app

# Copy pom.xml and download dependencies first (for better Docker caching)
COPY pom.xml .
COPY src ./src

# Install Maven and build the JAR inside the container
RUN apk add --no-cache maven && mvn clean package -DskipTests

# Run the application
CMD ["java", "-jar", "target/*.jar"]
