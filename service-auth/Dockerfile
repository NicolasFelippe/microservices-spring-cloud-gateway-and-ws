FROM openjdk:17
VOLUME /tmp
ADD ./target/service-auth-0.0.1-SNAPSHOT.jar service-auth.jar
ENTRYPOINT ["java", "-jar", "/service-auth.jar"]