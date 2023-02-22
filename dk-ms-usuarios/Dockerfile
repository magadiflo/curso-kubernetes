FROM openjdk:17.0.2

WORKDIR /app

COPY ./target/dk-ms-usuarios-0.0.1-SNAPSHOT.jar .

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "dk-ms-usuarios-0.0.1-SNAPSHOT.jar"]