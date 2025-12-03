FROM openjdk:17-jdk-slim

# Install LibreOffice + fonts
RUN apt-get update && \
    apt-get install -y libreoffice fonts-dejavu fonts-liberation && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

# App directory
WORKDIR /app

# Copy built jar file
COPY target/pdfconverter.jar app.jar

# Expose port
EXPOSE 8080

# Start application
CMD ["java", "-jar", "app.jar"]
