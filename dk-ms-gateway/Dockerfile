FROM openjdk:17-jdk-alpine as builder

WORKDIR /app/dk-ms-gateway

COPY ./pom.xml /app

COPY ./dk-ms-gateway/.mvn ./.mvn

COPY ./dk-ms-gateway/mvnw .

COPY ./dk-ms-gateway/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target

COPY ./dk-ms-gateway/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app

RUN mkdir ./logs

COPY --from=builder /app/dk-ms-gateway/target/dk-ms-gateway-0.0.1-SNAPSHOT.jar .

EXPOSE 8090

CMD ["java", "-jar", "dk-ms-gateway-0.0.1-SNAPSHOT.jar"]