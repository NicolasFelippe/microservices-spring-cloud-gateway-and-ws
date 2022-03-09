FROM openjdk:17
VOLUME /tmp
EXPOSE 8765
ADD ./target/service-gateway-0.0.1-SNAPSHOT.jar service-gateway.jar
ENTRYPOINT ["java","-jar","/service-gateway.jar"]