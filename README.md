
# <p align="center">TWEETS SERVICE API</p>

## Description

This project was built using [Spring Boot](https://github.com/) framework starter framework.


## Dependencies & Services
- Springboot 
- Postgres


## Get started Notes:
- Create a copy of `application.properties` file by from `.application.properties.example` file.
- Update `.application.properties` file with the necessary credentials
- Application Endpoint: `http://localhost:8080`

## Installation

### Running the app (Without Docker)

Install the application dependencies by running this command:
```bash
./mvnw clean install -U 
```

After installing the dependencies and configuring `application.properties` file, start the applications using:
```bash
./mvnw spring-boot:run
```


### Running the app (Using Docker)

Build the application docker image using the command:
```bash
docker build -t [name:tag] .
```
Example:
```bash
docker build -t agavitalis/tweets:latest .
```

Run the generated docker image in a container using the command:
```bash
docker run -d -p [host_port]:[container_port] --name [container_name] [image_id/image_tag]
```
Example:
```bash
docker run -d -p 8080:8080 --name tweets agavitalis/tweets:latest
```

Verify that your docker container is running using the command:
```bash
docker container ps
```






## Stay in touch

Author - [Ogbonna Vitalis](agavitalisogbonna@gmail.com)

## License

[MIT licensed](LICENSE).
