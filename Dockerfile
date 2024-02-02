# Fetching latest version of Java
FROM openjdk:17-jdk-slim

# Setting up work directory
WORKDIR /app

# Copy the jar file into our app
COPY ./target/tweets-0.0.1-SNAPSHOT.jar /app

# Exposing port 9090
EXPOSE 9090

# Starting the application
CMD ["java", "-jar", "tweets-0.0.1-SNAPSHOT.jar"]