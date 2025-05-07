# Books and Authors Management System

A Spring Boot application for managing books and authors with CRUD operations.

## Features

- Create, read, and update operations for books and authors
- Entity relationship between books and authors (One-to-Many)
- Custom queries with inner joins
- JSP views with Bootstrap styling
- Form validation
- Unit tests for repository and service layers

## Technologies Used

- Spring Boot 2.7.12
- Spring Data JPA
- Spring MVC
- MySQL Database
- JSP and JSTL
- Bootstrap 5
- JUnit 5 and Mockito for testing
- Maven for dependency management

## Project Structure

```
booksauthors/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── bookstore/
│   │   │           ├── controllers/     # MVC Controllers
│   │   │           ├── dto/             # Data Transfer Objects
│   │   │           ├── entities/        # JPA Entities
│   │   │           ├── repositories/    # Spring Data JPA Repositories
│   │   │           ├── services/        # Service Layer
│   │   │           └── config/          # Configuration Classes
│   │   ├── resources/
│   │   │   └── application.properties   # Application Configuration
│   │   └── webapp/
│   │       └── WEB-INF/
│   │           └── views/               # JSP Views
│   └── test/
│       └── java/
│           └── com/
│               └── bookstore/
│                   ├── repositories/    # Repository Tests
│                   └── services/        # Service Tests
└── pom.xml                              # Maven Configuration
```

## Database Schema

### Authors Table
- id (Primary Key)
- name
- email
- biography
- birth_year
- country

### Books Table
- id (Primary Key)
- title
- isbn
- publication_date
- price
- description
- page_count
- genre
- author_id (Foreign Key)

## Setup and Running

1. Clone the repository https://github.com/ruthwikchikoti/2023EBCS675_
2. Configure MySQL database in `application.properties`
3. Run the application using Maven:
   ```
   mvn spring-boot:run
   ```
4. Access the application at: http://localhost:8080/

## Testing

Run the tests using Maven:
```
mvn test
```

## API Endpoints

### Authors
- GET `/authors` - List all authors
- GET `/authors/{id}` - View author details
- GET `/authors/new` - Display form to add a new author
- POST `/authors` - Create a new author
- GET `/authors/{id}/edit` - Display form to edit an author
- POST `/authors/{id}` - Update an author

### Books
- GET `/books` - List all books
- GET `/books/{id}` - View book details
- GET `/books/new` - Display form to add a new book
- POST `/books` - Create a new book
- GET `/books/{id}/edit` - Display form to edit a book
- POST `/books/{id}` - Update a book
- GET `/books/by-author/{authorId}` - List books by author
- GET `/books/by-genre?genre={genre}` - List books by genre 
