# Base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Maven Wrapper and pom.xml
COPY .mvn/ .mvn
COPY mvnw .
COPY pom.xml .

# Add execute permission for Maven Wrapper
RUN chmod +x mvnw

# Cache dependencies
RUN ./mvnw dependency:resolve

# Copy the project files
COPY src ./src

# Build the project
RUN ./mvnw package -DskipTests

# Copy the built jar file to the container
COPY target/hospital-management-0.0.1-SNAPSHOT.jar app.jar

# Expose the application port
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
