# authentication-service

Authenticate Credentials and provide JWT token
User Registration, User Management, JWT token generation service

# docker build -t username/service_name:tag .
docker build -t sagariprashanth/authentication-service:1.0 .
# docker login 
docker login
# docker push username/image_name:1.0
docker push sagariprashanth/authentication-service:1.0

# docker pull image_name
docker pull authentication-service:1.0

# docker run 
## docker run -d -p host_port:container_port --name container_name image_name:tag
docker run -d -p 8761:8080 --name api-gateway sagariprashanth/authentication-service:2.0
docker run -d -p 8761:8761 --name s-d-8761 sagariprashanth/authentication-service:1.0