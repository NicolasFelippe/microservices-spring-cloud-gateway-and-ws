FROM openjdk:17
VOLUME /tmp
ADD ./target/service-websocket-0.0.1-SNAPSHOT.jar service-websocket.jar
ENTRYPOINT ["java","-jar","/service-websocket.jar"]