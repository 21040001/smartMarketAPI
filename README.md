SmartMarket Backend API

A robust, scalable, and secure backend system built for retail management, supporting desktop and mobile applications.

🚀 Technologies Used
Framework & Language
Java 17

Spring Boot 3.x

Spring Security with JWT authentication

Database
MSSQL – Relational database for structured data storage

Spring Data JPA – ORM for database operations

API & Documentation
RESTful APIs – Clean and structured endpoints

OpenAPI (Swagger) – Interactive API documentation available at /swagger-ui.html

Security
JWT (JSON Web Token) – Stateless authentication

Spring Security – Role-based access control (RBAC)

Password Encryption – BCrypt password encoding

Development & Deployment
Maven – Dependency management

Docker – Containerized deployment

Git – Version control

📦 Project Structure
text
src/  
├── main/  
│   ├── java/  
│   │   └── com/smartmarket/  
│   │       ├── controller/     → REST endpoints  
│   │       ├── service/        → Business logic  
│   │       ├── repository/     → Data access layer (JPA)  
│   │       ├── model/          → Entity classes  
│   │       ├── config/         → Security & application config  
│   │       ├── security/       → JWT and authentication logic  
│   │       └── dto/            → Data Transfer Objects  
│   └── resources/  
│       ├── application.yml     → Configuration  
│       └── logback-spring.xml → Logging configuration  
└── test/                      → Unit & integration tests  
🔐 Authentication & Authorization
JWT-based authentication

Endpoints secured with role-based access (e.g., ADMIN, USER)

Token refresh mechanism supported

📡 API Features
Product management (CRUD operations)

User registration and login

Sales and inventory tracking

Secure file upload/download (if applicable)

RESTful standards with consistent response formats

🧪 Testing
JUnit 5 – Unit testing

Mockito – Mocking dependencies

Test Coverage – Measured with JaCoCo (target ≥80%)

🐳 Docker Deployment
dockerfile
FROM openjdk:17-jdk-slim  
COPY target/smartmarket-api.jar app.jar  
ENTRYPOINT ["java","-jar","/app.jar"]  
Run with:

bash
docker build -t smartmarket-api .  
docker run -p 8080:8080 smartmarket-api  
📊 API Documentation
Access Swagger UI after running the application:
🔗 http://localhost:8080/swagger-ui.html

🚧 Environment Variables
Configure in application.yml or use environment variables:

yaml
spring:
  datasource:
    url: ${DB_URL}
    username: ${DB_USER}
    password: ${DB_PASSWORD}
  jpa:
    hibernate:
      ddl-auto: update

security:
  jwt:
    secret-key: ${JWT_SECRET}
✅ Future Improvements
Redis integration for caching

Kafka/RabbitMQ for async messaging

Kubernetes deployment setup

Enhanced monitoring with Prometheus/Grafana

👨‍💻 Developer
Developed by Davronbek
📧 Email: abdurazzoqovdavronbek3@gmail.com]

⭐ Like this project? Give it a star on GitHub!
