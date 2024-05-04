## Amaris-crud in Java

This application contains an implementation for a Spring application 
 

## Getting Started

### Requirement

This application requires Java 11 or later.

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
http://localhost:8090/persona/ addPersona
```
{
"tipoDocumento":"C",
"numeroDocumento":"16829228",
"primerNombre":"gustavos",
"segundoNombre":"adolfos",
"primerApellido":"soto",
"segundoApellido":"cano",
"telefono":"3113397499",
"direccion":"cra 1a113 #72-84",
"ciudad":"cali"

}
```
http://localhost:8090/persona 
```
 findAll clientes
```
/persona/{tipoDocumento}/dcto/{numeroDocumento}
```
find by tipo documento and numero documento
```
```
docker build -t amaris
docker run  -p 8090:8090 amaris
For find the ip address
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' containerName 