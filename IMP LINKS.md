#spring-boot-docker
https://spring.io/guides/topicals/spring-boot-docker


## docker run -e "SPRING_PROFILES_ACTIVE=docker" your-image


sample 
docker build -t sagariprashanth/api-gateway:2.0 .
docker push sagariprashanth/api-gateway:2.0

docker build -t sagariprashanth/weather-service:2.0 .
docker push sagariprashanth/weather-service:2.0

docker build -t sagariprashanth/authentication-service:2.0 .
docker push sagariprashanth/authentication-service:2.0

docker build -t sagariprashanth/service-discovery:2.0 .
docker push sagariprashanth/service-discovery:2.0 .
