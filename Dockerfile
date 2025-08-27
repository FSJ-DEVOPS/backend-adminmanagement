# Stage 1: Build the dependency (sellerprofile) to a local repository.
FROM maven:3.9.6-eclipse-temurin-21 AS dependency-builder

# Clone the sellerprofile repository and build it.
# YOU MUST replace 'your-github-username' with your actual GitHub username and 'sellerprofile-repo' with the repository name for sellerprofile.
WORKDIR /usr/src/app
RUN git clone https://github.com/FSJ-DEVOPS/backend-sellerprofile.git sellerprofile
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