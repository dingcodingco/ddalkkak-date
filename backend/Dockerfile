# Development stage with Spring Boot DevTools support
FROM gradle:8.10-jdk17

WORKDIR /app

# Copy Gradle files first for better caching
COPY build.gradle settings.gradle ./
COPY gradle ./gradle

# Download dependencies
RUN gradle dependencies --no-daemon || true

# Copy source code
COPY src ./src

# Expose Spring Boot port
EXPOSE 8080

# Run application in development mode with hot-reloading
ENTRYPOINT ["gradle", "bootRun", "--no-daemon", "-Dspring.profiles.active=local"]
