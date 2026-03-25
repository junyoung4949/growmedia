# Stage 1: Build
FROM gradle:8.14-jdk17-alpine AS build
WORKDIR /home/gradle/project

COPY --chown=gradle:gradle . .

# wrapper 사용 (핵심)
RUN ./gradlew build --no-daemon -x test


# Stage 2: Run
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

COPY --from=build /home/gradle/project/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]