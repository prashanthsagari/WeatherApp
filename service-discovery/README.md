# service discovery
This service maintains registry of up and running services, we can see health or status of registered services
1. api-gateway
2. authentication-service
3. weather-service

# docker build -t username/service_name:tag .
docker build -t sagariprashanth/service-discovery:1.0 .
# docker login 
docker login
# docker push username/image_name:1.0
docker push sagariprashanth/service-discovery:1.0

# docker pull image_name
docker pull service-discovery:1.0

# docker run 
## docker run -d -p host_port:container_port --name container_name image_name:tag
docker run -d -p 8761:8080 --name service-discovery sagariprashanth/service-discovery
docker run -d -p 8761:8761 --name s-d-8761 sagariprashanth/service-discovery:1.0