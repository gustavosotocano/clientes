FROM openjdk:11
VOLUME /tmp
EXPOSE 8090
ARG JAR_FILE=target/clients-0.0.1.jar
ADD ${JAR_FILE} clients-0.0.1.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/clients-0.0.1.jar"]
