
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
You can run the app in 3 ways: 
1. Without Docker
2. Using Docker as an image
3. Using Docker Compose (Recommended)

### Running the app (Without Docker)

Install the application dependencies by running this command:
```bash
./mvnw clean install -U 
```

After installing the dependencies and configuring `application.properties` file, start the applications using:
```bash
./mvnw spring-boot:run
```

### Running the app (Using Docker as an image)

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

To delete a docker container use the command:
```bash
docker stop <container_id>
```

To delete a docker container use the command:
```bash
docker rm <container_id>
```

### Running the app (Using Docker Compose -- Recommended)

Build the application docker image using the command:
```bash
docker compose build
```
Run the app using:
```bash
docker compose up 
```
You can also run in detached mood using:
```bash
docker compose up -d
```
To quit and delete use the command:
```bash
docker compose down
```
The access the app while running via docker use the URL: http://0.0.0.0:8070

### Architecture Overview

#### Entity-Driven Design:

- **User, Post, Comment, and Like Entities:** Key business concepts, including users, posts, comments, and likes, are modeled as entities. Each entity encapsulates both data and behavior relevant to its domain.

#### Aggregate Roots:

- **User as Aggregate Root:** The `User` entity serves as an aggregate root, managing related entities such as `Post`, `Comment`, `Like`, and the new entity `Follow` (representing the follow relationship).

#### Repository Pattern:

- **UserRepository, PostRepository, CommentRepository, LikeRepository, FollowRepository:** Repositories abstract the data access layer, providing a way to retrieve and store aggregates. Each entity type has its corresponding repository, facilitating seamless data access.

#### Value Objects:

- **Post and Comment content as Value Objects:** Certain aspects, such as the content of a post or comment, can be modeled as value objects if they lack a conceptual identity and are immutable.

#### Service Layer:

- **UserService, PostService, CommentService, FollowService:** A service layer is introduced to encapsulate business logic that extends beyond the domain of a specific entity. The `FollowService` manages user follow/unfollow operations, introducing a many-to-many relationship.

#### Relationships:

- **One-to-Many and Many-to-Many Relationships:**
    - One-to-many relationships exist between `User` and `Post`, `User` and `Comment`, as well as `User` and `Like`.
    - A many-to-many relationship is established through the `Follow` entity, allowing users to follow and unfollow each other.

#### Authentication:

- **User Authentication:** Authentication mechanisms, such as token-based authentication or OAuth, are integrated to secure user access. Authentication services validate user credentials and generate tokens for authorized access to protected resources.

#### Comments:

- **Comment Entity and Repository:** The introduction of the `Comment` entity and repository allows users to post comments on posts, expanding the interaction possibilities within the system.

### Challenges and Considerations:

1. **Consistency in Aggregates:**
    - Ensuring consistency within aggregates is crucial, especially when dealing with multiple entities. Maintaining the integrity of related entities within the same aggregate and managing consistency across aggregates is a primary concern.

2. **Transaction Management:**
    - Careful handling of transactions is necessary, especially when dealing with multiple aggregates. Ensuring that operations are atomic and that the system remains in a consistent state is a key consideration.

3. **Complexity of Domain Logic:**
    - As more business logic is encapsulated within the domain layer, managing the complexity of domain logic, especially when dealing with intricate rules, relationships, and user interactions, can be challenging.

4. **Query Performance:**
    - Balancing a rich domain model with efficient querying is essential. Denormalization or other strategies might be employed for complex query scenarios involving user posts, comments, likes, and follow relationships.

5. **Evolution of the Domain Model:**
    - Evolving the domain model as business requirements change, especially with the introduction of new features like follows/unfollows, is a challenge. DDD encourages an iterative approach, but managing changes while ensuring backward compatibility is crucial.

In summary, the application architecture combines DDD principles with additional features like authentication, comments, and a many-to-many relationship for user follows/unfollows. Addressing challenges related to consistency, transactions, complexity, and evolution is vital for building a robust and adaptable system.




## Stay in touch

Author - [Ogbonna Vitalis](agavitalisogbonna@gmail.com)

## License

[MIT licensed](LICENSE).
