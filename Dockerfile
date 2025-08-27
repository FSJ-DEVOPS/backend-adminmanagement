# Stage 1: Build the dependency (sellerprofile) to a local repository.
FROM maven:3.9.6-eclipse-temurin-21-alpine AS dependency-builder
WORKDIR /usr/src/app
COPY . .

# Change into the sellerprofile repository and build it.
WORKDIR /usr/src/app/sellerprofile
RUN mvn clean install

# Stage 2: Build the adminmanagement service
FROM maven:3.9.6-eclipse-temurin-21-alpine AS build
WORKDIR /usr/src/app
# Copy the local Maven repository from the dependency-builder stage.
# This ensures that adminmanagement's Maven build can find sellerprofile.
COPY --from=dependency-builder /root/.m2 /root/.m2
COPY . .
WORKDIR /usr/src/app/ShopverseAdmin

# Now, build the adminmanagement service, which now has access to its dependency.
RUN mvn clean package -DskipTests

# Stage 3: Create the final production image
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
EXPOSE 8085
COPY --from=build /usr/src/app/ShopverseAdmin/target/ShopverseAdmin-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]