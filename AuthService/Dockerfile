FROM maven:3.9.9-eclipse-temurin-21 AS build

WORKDIR /build

COPY pom.xml .

COPY AuthService ./AuthService

WORKDIR /build/AuthService

RUN mvn dependency:go-offline
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jdk

COPY --from=build /build/AuthService/target/*.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]