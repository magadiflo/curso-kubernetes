FROM openjdk:17-jdk-alpine as builder

WORKDIR /app/dk-ms-usuarios

COPY ./pom.xml /app

COPY ./dk-ms-usuarios/.mvn ./.mvn

COPY ./dk-ms-usuarios/mvnw .

COPY ./dk-ms-usuarios/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target

COPY ./dk-ms-usuarios/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY --from=builder /app/dk-ms-usuarios/target/dk-ms-usuarios-0.0.1-SNAPSHOT.jar .

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "dk-ms-usuarios-0.0.1-SNAPSHOT.jar"]