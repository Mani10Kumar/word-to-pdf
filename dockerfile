FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN apt-get update && apt-get install -y maven

RUN mvn -q -e -DskipTests clean package

# Rename your jar to a fixed name inside Docker
RUN cp target/*.jar app.jar

CMD ["java", "-jar", "app.jar"]

