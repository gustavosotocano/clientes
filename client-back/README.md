## Clients in Java

This application contains an implementation for a Spring application 
 

## Getting Started

### Requirement

This application requires Java 17 or later.

### Documentación

En la ruta http://localhost:8090/swagger-ui/index.html#/ se encuentra el swagger de la aplicación
### Building and running the application

To build the application run this command in the project directory:
```
mvn package
```
To start the application run this command:
```
mvn spring-boot:run
```
To Access the application
```
The application open  port 8090
``` 
  
  addPersona

```
http://localhost:8090/v1/client/
```

```
{
"sharedKey":"jperezc",
"bussinessId":"ghffhghgf",
"email":"gustavo@domain.com",
"phone":"adolfo",
"added":"2024-05-01",
"started":"2024-05-25",
"ended":"2024-05-25",
"name":"cra 1a113 #72-84"
}
```
  findAll clientes
```
http://localhost:8090/v1/client 
```

find by E-mail

```
http://localhost/v1/client/email/{email}
```

find by shared Key

```
http://localhost/v1/client/sharedKey/{sharedKey}
```

```
docker build -t clients
docker run  -p 8090:8090 amaris
For find the ip address
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' containerName 