# Stage 1: Build the dependency (sellerprofile) to a local repository.
FROM maven:3.9.6-eclipse-temurin-21 AS dependency-builder

# Set the working directory
WORKDIR /usr/src/app

# Define a build argument for the GitHub Personal Access Token
ARG GITHUB_PAT

# Clone the private sellerprofile repository using the PAT for authentication.
# The token is passed as a build argument, not stored in the image.
RUN git clone https://FSJ-DEVOPS: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@github.com/FSJ-DEVOPS/backend-sellerprofile.git sellerprofile

# Change into the cloned repository and build it.
WORKDIR /usr/src/app/sellerprofile
RUN mvn clean install

# Stage 2: Build the adminmanagement service
FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .

# Copy the local Maven repository from the dependency-builder stage.
# This ensures that adminmanagement's Maven build can find sellerprofile.
COPY --from=dependency-builder /root/.m2 /root/.m2

# Now, build the adminmanagement service, which now has access to its dependency.
RUN mvn clean package -DskipTests

# Stage 3: Create the final production image
FROM eclipse-temurin:21-jdk
WORKDIR /app
EXPOSE 8085
COPY --from=build /app/target/ShopverseAdmin-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]