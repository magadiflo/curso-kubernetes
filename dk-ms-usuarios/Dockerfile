FROM openjdk:17.0.2

WORKDIR /app/dk-ms-usuarios

COPY ./pom.xml /app

COPY ./dk-ms-usuarios .

RUN ./mvnw clean package -DskipTests

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "./target/dk-ms-usuarios-0.0.1-SNAPSHOT.jar"]