FROM maven:3.8.5-openjdk-17 AS build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:17.0.1-jdk-slim
COPY --from=build /target/WorldApp-0.0.1-SNAPSHOT.jar WorldApp.jar
EXPOSE 8080
ENTRYPOINT ["java" , "-jar" , "WorldApp.jar"]