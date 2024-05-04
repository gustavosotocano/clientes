FROM openjdk:11
VOLUME /tmp
EXPOSE 8090
ARG JAR_FILE=target/crud-0.0.1.jar
ADD ${JAR_FILE} crud-0.0.1.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/crud-0.0.1.jar"]
