package com.bookstore.repositories;

import com.bookstore.entities.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    public void testSaveAuthor() {
        // Create an author
        Author author = new Author();
        author.setName("Test Author");
        author.setEmail("test@example.com");
        author.setBiography("Test biography");
        author.setBirthYear(1980);
        author.setCountry("Test Country");

        // Save the author
        Author savedAuthor = authorRepository.save(author);

        // Verify the author was saved
        assertThat(savedAuthor.getId()).isNotNull();
        assertThat(savedAuthor.getName()).isEqualTo("Test Author");
        assertThat(savedAuthor.getEmail()).isEqualTo("test@example.com");
        assertThat(savedAuthor.getBiography()).isEqualTo("Test biography");
        assertThat(savedAuthor.getBirthYear()).isEqualTo(1980);
        assertThat(savedAuthor.getCountry()).isEqualTo("Test Country");
    }

    @Test
    public void testFindAuthorById() {
        // Create and save an author
        Author author = new Author();
        author.setName("Test Author");
        author.setEmail("test@example.com");
        author.setBiography("Test biography");
        author.setBirthYear(1980);
        author.setCountry("Test Country");
        Author savedAuthor = authorRepository.save(author);

        // Find the author by ID
        Optional<Author> foundAuthor = authorRepository.findById(savedAuthor.getId());

        // Verify the author was found
        assertThat(foundAuthor).isPresent();
        assertThat(foundAuthor.get().getName()).isEqualTo("Test Author");
    }

    @Test
    public void testFindByNameContainingIgnoreCase() {
        // Create and save authors
        Author author1 = new Author();
        author1.setName("John Doe");
        author1.setEmail("john@example.com");
        authorRepository.save(author1);

        Author author2 = new Author();
        author2.setName("Jane Doe");
        author2.setEmail("jane@example.com");
        authorRepository.save(author2);

        Author author3 = new Author();
        author3.setName("Bob Smith");
        author3.setEmail("bob@example.com");
        authorRepository.save(author3);

        // Find authors by name containing "doe" (case insensitive)
        List<Author> authors = authorRepository.findByNameContainingIgnoreCase("doe");

        // Verify the correct authors were found
        assertThat(authors).hasSize(2);
        assertThat(authors).extracting(Author::getName).containsExactlyInAnyOrder("John Doe", "Jane Doe");
    }

    @Test
    public void testFindByEmail() {
        // Create and save an author
        Author author = new Author();
        author.setName("Test Author");
        author.setEmail("unique@example.com");
        authorRepository.save(author);

        // Find the author by email
        Optional<Author> foundAuthor = authorRepository.findByEmail("unique@example.com");

        // Verify the author was found
        assertThat(foundAuthor).isPresent();
        assertThat(foundAuthor.get().getName()).isEqualTo("Test Author");
    }

    @Test
    public void testFindByCountry() {
        // Create and save authors
        Author author1 = new Author();
        author1.setName("Author 1");
        author1.setEmail("author1@example.com");
        author1.setCountry("USA");
        authorRepository.save(author1);

        Author author2 = new Author();
        author2.setName("Author 2");
        author2.setEmail("author2@example.com");
        author2.setCountry("UK");
        authorRepository.save(author2);

        Author author3 = new Author();
        author3.setName("Author 3");
        author3.setEmail("author3@example.com");
        author3.setCountry("USA");
        authorRepository.save(author3);

        // Find authors by country
        List<Author> authors = authorRepository.findByCountry("USA");

        // Verify the correct authors were found
        assertThat(authors).hasSize(2);
        assertThat(authors).extracting(Author::getName).containsExactlyInAnyOrder("Author 1", "Author 3");
    }
} 