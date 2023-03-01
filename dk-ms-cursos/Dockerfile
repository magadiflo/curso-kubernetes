FROM openjdk:17-jdk-alpine as builder

WORKDIR /app/dk-ms-cursos

COPY ./pom.xml /app

COPY ./dk-ms-cursos/.mvn ./.mvn

COPY ./dk-ms-cursos/mvnw .

COPY ./dk-ms-cursos/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target

COPY ./dk-ms-cursos/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

WORKDIR /app

RUN mkdir ./logs

COPY --from=builder /app/dk-ms-cursos/target/dk-ms-cursos-0.0.1-SNAPSHOT.jar .

EXPOSE 8002

CMD ["java", "-jar", "dk-ms-cursos-0.0.1-SNAPSHOT.jar"]