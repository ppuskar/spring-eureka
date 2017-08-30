# spring-eureka
A sample project to showcase how eureka can be used within microservice architecture to inter-service comunication, taking following features in consideration :
1) Service-discovery
2) Scalability
3) Load balancing

Learned this stuff from https://cloud.spring.io/spring-cloud-netflix

All the modules are dockerized and the environment will be up in less then 30 sec. Keep playing by scaling up services and observe load balancing in the output.

### Requirements :
Docker 17.X, docker-compose 14.X


## Design overview :

It is requred to have a server which takes care of registering rest services and same can be used for discovery.
### spring-eureka-server
A sprng boot project which will run as a eureka server.
Endpoint : http://localhost:8761/eureka

### spring-rest-service-a
A spring boot project exposing an rest endpoint, which will display the name of the service(with which it will register itself with eureka for discvery) along with IP address of the system the service is hosted upon.
Endpoint : http://[host-address]:8080/sysinfo 

### spring-rest-service-b
A spring boot project which will do following things :

  a. Registers itself with eureka
  
  b. Make use of rest client (Spring restTemplate) to call **spring-rest-service-a** using Eureka to resolve the address by service-name (Keeping in mind that multiple instances of this service is running)
  
Endpoint : 
 
| Path | Descrioption |
| ------------- | ------------- |
| /sysInfo  | curent service name with which it has registered itself with eureka |
| /stackInfo  | ServiceName + /sysinfo of **spring-rest-service-a** |

## spring-rest-client
Under development, look at it if need to see the native way of calling rest services using Eureka-client.

## RunBook
`mvn clean install`
It will create the respective spring boot jar and as well as docker images for the respoective projects. Verify the docker images by ''docker images` command.

navigate to root of the parent project and do a
`docker-compose up -d`

It will launch all the containers(Eureka server as well as the rest service modules). 
Verify if all the containers are up and running using command `docker ps`
Open browser to check this url : http://localhost:8761/eureka

The registered services will be visible in the eureka UI.

Click on the services to see the endpoints are wokring(you need to modify the url though).
## Fun part
From Eureka UI, click on the **spring-rest-service-b** url, A url (with resolved IP and port for service-b) will open up in a new tab with /info, update the URL to call /stackInfo instead.

go back to your terminal and scale up **spring-rest-service-a** to run on 3 or may be 4 instances.

`docker-compose scale rest-service-a=3`

**rest-service-a** is the alias for **spring-rest-service-a** check docker-compose.yml file for all the aliases.
while the instances are getting up, keep refreshing the /stackInfo output and keep a watch on ip address of spring-rest-service-a (Load balancing).
Once all three instances are up and running, It will be visible in the Eureka Service UI as well. 

## future developement
- Highly available eureka server
- using different rest client for e.g Fiegn 
