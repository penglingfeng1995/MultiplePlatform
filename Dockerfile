FROM openjdk:8-jdk-alpine
COPY target/mp.jar mp.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","/mp.jar"]