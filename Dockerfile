FROM gradle:7.5.0-jdk17-alpine as build

WORKDIR /app
COPY . .
RUN gradle --no-daemon -x test build

FROM openjdk:17

COPY --from=build app/build/libs/*.jar task-tracker.jar

EXPOSE 8080
CMD exec java -jar task-tracker.jar
