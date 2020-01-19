FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
COPY pom.xml /build/
COPY src /build/src/
COPY lib /build/lib/
WORKDIR /build/
RUN mvn package


FROM openjdk:8-jre-alpine
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/asset-1.0.jar /app/
ENTRYPOINT ["java", "-jar", "asset-1.0.jar"]