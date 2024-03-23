#API GATEWAY

This service is entry point for the Weather App backend service.
It takes all the requests and validates JWT token, if it valid then directs requests to correct web service.

Currently it registered with below services
1. service-discovery  -- responsible to locate services by service name instead of hardcoded url
2. authentication-service -- User Registration, User Management, JWT token generation service
3. weather-service -- Secured  Weather details api
 
# docker build -t username/service_name:tag .
docker build -t sagariprashanth/api-gateway:1.0 .
# docker login 
docker login
# docker push username/image_name:1.0
docker push sagariprashanth/api-gateway:1.0

# docker pull image_name
docker pull api-gateway:1.0

# docker run 
## docker run -d -p host_port:container_port --name container_name image_name:tag
docker run -d -p 8761:8080 --name api-gateway sagariprashanth/api-gateway:2.0
docker run -d -p 8761:8761 --name s-d-8761 sagariprashanth/api-gateway:1.0