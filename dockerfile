FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN apt-get update && apt-get install -y maven

RUN mvn -q -e -DskipTests clean package

CMD ["java", "-jar", "target/*.jar"]
