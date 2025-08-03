FROM base-builder:latest AS auth-builder

# Copy và build auth-service
COPY ./auth-service/pom.xml ./auth-service/
COPY ./auth-service/src ./auth-service/src
RUN cd auth-service && mvn clean package -DskipTests


# Stage 2: Build with amazoncorretto jdk 21
FROM amazoncorretto:21.0.4

# Set the working directory in the container
WORKDIR /app

# Copy the jar file from the builder stage
COPY --from=auth-builder /app/auth-service/target/*.jar ./auth-service.jar

# Expose the port that the application will run on
EXPOSE 8090

# Run the jar file
ENTRYPOINT ["java","-Dspring.profiles.active=docker","-jar","auth-service.jar"]


