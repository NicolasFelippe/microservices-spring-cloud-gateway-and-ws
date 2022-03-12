# Criando e testando containers Docker

## MariaDB
```
 docker run --detach --network barber-net --name mariadb:latest --env MARIADB_USER=dev --env MARIADB_PASSWORD=Dev@123 --env MARIADB_ROOT_PASSWORD=root  mariadb:latest
```

### service-websocket !!
```
Precisar criar um docker rabbitMQ 3.9  e instalar plugin stomp // TODO: encontrar comando para colocar aqui
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3.9-management
acessar o docker e rodar o comando :: rabbitmq-plugins enable rabbitmq_stomp
```
## Criar rede docker para sistema hr
```
docker network create barber-net
```

## hr-config-server - fazer TODO
```
FROM openjdk:17
VOLUME /tmp
EXPOSE 8888
ADD ./target/hr-config-server-0.0.1-SNAPSHOT.jar hr-config-server.jar
ENTRYPOINT ["java","-jar","/hr-config-server.jar"]
``` 
```
mvnw clean package

docker build -t hr-config-server:v1 .

docker run -p 8888:8888 --name hr-config-server --network hr-net -e GITHUB_USER=acenelio -e GITHUB_PASS= hr-config-server:v1
```

## service-discovery
```
FROM openjdk:17
VOLUME /tmp
EXPOSE 8761
ADD ./target/service-discovery-0.0.1-SNAPSHOT.jar service-discovery.jar
ENTRYPOINT ["java","-jar","/service-discovery.jar"]
``` 
```
mvnw clean package -DskipTests

docker build -t service-discovery:v1 .

docker run --detach -p 8761:8761 --name service-discovery --network barber-net service-discovery:v1
```

## service-gateway
```
FROM openjdk:17
VOLUME /tmp
EXPOSE 8765
ADD ./target/service-gateway-0.0.1-SNAPSHOT.jar service-gateway.jar
ENTRYPOINT ["java","-jar","/service-gateway.jar"]
``` 
```
mvnw clean package -DskipTests

docker build -t service-gateway:v1 .

docker run --detach -p 8080:8080 --name service-gateway --network barber-net service-gateway:v1
```

## service-auth
```
FROM openjdk:17
VOLUME /tmp
ADD ./target/service-auth-0.0.1-SNAPSHOT.jar service-auth.jar
ENTRYPOINT ["java","-jar","/service-auth.jar"]
``` 
```
mvnw clean package -DskipTests

docker build -t service-auth:v1 .

docker run --detach -P --network barber-net service-auth:v1
```



## service-user
```
FROM openjdk:17
VOLUME /tmp
ADD ./target/service-user-0.0.1-SNAPSHOT.jar service-user.jar
ENTRYPOINT ["java","-jar","/service-user.jar"]
``` 
```
mvnw clean package -DskipTests

docker build -t service-user:v1 .

docker run --detach -P --network barber-net service-user:v1
```

## service-websocket
```
FROM openjdk:17
VOLUME /tmp
ADD ./target/service-websocket-0.0.1-SNAPSHOT.jar service-websocket.jar
ENTRYPOINT ["java","-jar","/service-websocket.jar"]
``` 
```
mvnw clean package -DskipTests

docker build -t service-websocket:v1 .

docker run -P --network barber-net service-websocket:v1
```



## Alguns comandos Docker
Criar uma rede Docker
```
docker network create <nome-da-rede>
```
Baixar imagem do Dockerhub
```
docker pull <nome-da-imagem:tag>
```
Ver imagens
```
docker images
```
Criar/rodar um container de uma imagem
```
docker run -p <porta-externa>:<porta-interna> --name <nome-do-container> --network <nome-da-rede> <nome-da-imagem:tag> 
```
Listar containers
```
docker ps

docker ps -a
```
Acompanhar logs do container em execução
```
docker logs -f <container-id>
```