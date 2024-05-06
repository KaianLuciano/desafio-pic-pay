FROM gradle:jdk17 AS build

COPY --chown=gradle:gradle . /home/gradle/src

ENV APP_PROFILE=dev

WORKDIR /home/gradle/src

RUN gradle build -x test

FROM eclipse-temurin:17-jre-alpine

RUN mkdir /app

COPY --from=build /home/gradle/src/build/libs/PicpaychallengeApplication-*-SNAPSHOT.jar /app/spring-boot-application.jar

ENTRYPOINT ["java","-jar","/app/spring-boot-application.jar"]