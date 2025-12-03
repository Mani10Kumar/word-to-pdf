# ======================
# 1. BUILD STAGE
# ======================
FROM eclipse-temurin:17-jdk as builder

WORKDIR /app

# Copy Maven files first
COPY pom.xml .
COPY src ./src

# Build JAR using Maven wrapper if exists, else use system Maven
RUN chmod +x mvnw || true
RUN ./mvnw -q -DskipTests package || mvn -q -DskipTests package

# ======================
# 2. RUNTIME STAGE
# ======================
FROM eclipse-temurin:17-jdk

# Install LibreOffice + fonts
RUN apt-get update && \
    apt-get install -y libreoffice fonts-dejavu fonts-liberation && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copy built jar from builder
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
