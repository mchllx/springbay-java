FROM maven:3-eclipse-temurin-21 AS builder

WORKDIR /src

COPY mvnw .
COPY mvnw.cmd .
COPY pom.xml .
COPY .mvn .mvn
COPY src src

# compile the Java application
RUN mvn clean package -Dmaven.skip.test=true

FROM maven:3-eclipse-temurin-21
WORKDIR /app

# Copy and rename to app.jar
COPY --from=builder /src/target/skyward-java-1-0.0.1-SNAPSHOT.jar app.jar

ENV PORT=8080
ENV SPRING_REDIS_HOST=localhost SPRING_REDIS_PORT=1234
ENV SPRING_REDIS_DATABASE=0
ENV SPRING_REDIS_USERNAME= SPRING_REDIS_PASSWORD=

EXPOSE ${PORT}

ENTRYPOINT SERVER_PORT=${PORT} java -jar ./app.jar