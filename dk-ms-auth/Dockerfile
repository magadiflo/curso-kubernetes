ARG MS_NAME=dk-ms-auth

FROM openjdk:17-jdk-alpine as builder

ARG MS_NAME

WORKDIR /app/$MS_NAME

COPY ./pom.xml /app

COPY ./$MS_NAME/.mvn ./.mvn

COPY ./$MS_NAME/mvnw .

COPY ./$MS_NAME/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target

COPY ./$MS_NAME/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:17-jdk-alpine

ARG MS_NAME

WORKDIR /app

RUN mkdir ./logs

ARG MS_NAME=dk-ms-auth

ARG TARGET_FOLDER=/app/$MS_NAME/target

COPY --from=builder $TARGET_FOLDER/dk-ms-auth-0.0.1-SNAPSHOT.jar .

ARG PORT_APP=9000

ENV PORT $PORT_APP

EXPOSE $PORT

CMD ["java", "-jar", "dk-ms-auth-0.0.1-SNAPSHOT.jar"]