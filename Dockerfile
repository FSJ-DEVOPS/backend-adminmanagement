# Stage 1: Build the application with Maven
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .

# Build the project, skipping tests for a faster build
RUN mvn clean package -DskipTests

# Stage 2: Create the final production image
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Expose the correct port for the ShopverseAdmin service
EXPOSE 8085

# Copy the JAR file from the 'build' stage into the final image
COPY --from=build /app/target/ShopverseAdmin-0.0.1-SNAPSHOT.jar app.jar

# Set the entrypoint to run the JAR file
ENTRYPOINT ["java", "-jar", "app.jar"]