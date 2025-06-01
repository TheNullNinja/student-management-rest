# Student Management Microservice

📁 Project Structure

├── StudentApplication.java         # Main Spring Boot application

├── StudentController.java          # REST Controller for student endpoints

├── StudentService.java             # Service layer handling business logic

├── StudentResponse.java            # POJO representing student data

├── StudentConnectionUtil.java      # Utility to manage dynamic DB connections

├── DataSourceConfig.java           # Configuration for multiple data sources

├── application.properties          # Application configuration

🚀 Features
REST API for retrieving student data
Dynamic switching between multiple data sources
Separate DB connections for student and permission databases
Clean separation between controller, service, and data access layers

⚙️ Configuration
All environment-specific configuration is done in application.properties:
spring.application.name=student
server.port=8081

# DataSource Configurations
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# STUDENT DB (Primary)
spring.datasource.student.url=your-db-url
spring.datasource.student.username=your-userName
spring.datasource.student.password=your-password

# PERMISSION DB (Secondary)
spring.datasource.permission.url=your-db-url
spring.datasource.permission.username=your-userName
spring.datasource.permission.password=your-password

Replace your-db-url, your-userName, and your-password with actual values.

🛠️ Core Components
1. StudentApplication.java
The entry point of the Spring Boot application. It boots up the entire context.
2. StudentController.java
3. StudentService.java
Contains the business logic for student-related operations. It:
Calls StudentConnectionUtil to get the right data source
Returns a StudentResponse object with relevant data
4. StudentResponse.java
A simple POJO (Plain Old Java Object) to structure student data in the API response.
5. StudentConnectionUtil.java
Handles the logic for selecting the appropriate data source (student or permission) using JdbcTemplate.
6. DataSourceConfig.java
Configures:
Two DataSource beans for student and permission databases
Associated JdbcTemplate beans
Uses Spring annotations such as @Primary and @Qualifier to distinguish between beans.

📌 Notes
If you want to add more databases, extend DataSourceConfig.java and update StudentConnectionUtil.
This microservice is ideal for integration into a larger system with modular DB connections.

📄 License
This project is provided for educational/demo purposes and does not include any specific license. Add a license file if needed for production use.

