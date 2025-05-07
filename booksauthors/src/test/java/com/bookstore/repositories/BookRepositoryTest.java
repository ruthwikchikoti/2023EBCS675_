package com.bookstore.repositories;

import com.bookstore.entities.Author;
import com.bookstore.entities.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private Author author1;
    private Author author2;

    @BeforeEach
    public void setup() {
        // Create and save authors
        author1 = new Author();
        author1.setName("Test Author 1");
        author1.setEmail("author1@example.com");
        author1 = authorRepository.save(author1);

        author2 = new Author();
        author2.setName("Test Author 2");
        author2.setEmail("author2@example.com");
        author2 = authorRepository.save(author2);
    }

    @Test
    public void testSaveBook() {
        // Create a book
        Book book = new Book();
        book.setTitle("Test Book");
        book.setIsbn("1234567890");
        book.setPublicationDate(LocalDate.of(2023, 1, 1));
        book.setPrice(new BigDecimal("19.99"));
        book.setDescription("Test description");
        book.setPageCount(200);
        book.setGenre("Test Genre");
        book.setAuthor(author1);

        // Save the book
        Book savedBook = bookRepository.save(book);

        // Verify the book was saved
        assertThat(savedBook.getId()).isNotNull();
        assertThat(savedBook.getTitle()).isEqualTo("Test Book");
        assertThat(savedBook.getIsbn()).isEqualTo("1234567890");
        assertThat(savedBook.getPublicationDate()).isEqualTo(LocalDate.of(2023, 1, 1));
        assertThat(savedBook.getPrice()).isEqualTo(new BigDecimal("19.99"));
        assertThat(savedBook.getDescription()).isEqualTo("Test description");
        assertThat(savedBook.getPageCount()).isEqualTo(200);
        assertThat(savedBook.getGenre()).isEqualTo("Test Genre");
        assertThat(savedBook.getAuthor().getId()).isEqualTo(author1.getId());
    }

    @Test
    public void testFindBookById() {
        // Create and save a book
        Book book = new Book();
        book.setTitle("Test Book");
        book.setIsbn("1234567890");
        book.setPageCount(200);
        book.setAuthor(author1);
        Book savedBook = bookRepository.save(book);

        // Find the book by ID
        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());

        // Verify the book was found
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getTitle()).isEqualTo("Test Book");
    }

    @Test
    public void testFindByTitleContainingIgnoreCase() {
        // Create and save books
        Book book1 = new Book();
        book1.setTitle("Harry Potter");
        book1.setPageCount(300);
        book1.setAuthor(author1);
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setTitle("Lord of the Rings");
        book2.setPageCount(400);
        book2.setAuthor(author1);
        bookRepository.save(book2);

        Book book3 = new Book();
        book3.setTitle("The Hobbit");
        book3.setPageCount(250);
        book3.setAuthor(author2);
        bookRepository.save(book3);

        // Find books by title containing "Potter" (case insensitive)
        List<Book> books = bookRepository.findByTitleContainingIgnoreCase("potter");

        // Verify the correct books were found
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo("Harry Potter");
    }

    @Test
    public void testFindByAuthorId() {
        // Create and save books
        Book book1 = new Book();
        book1.setTitle("Book 1");
        book1.setPageCount(300);
        book1.setAuthor(author1);
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setTitle("Book 2");
        book2.setPageCount(400);
        book2.setAuthor(author1);
        bookRepository.save(book2);

        Book book3 = new Book();
        book3.setTitle("Book 3");
        book3.setPageCount(250);
        book3.setAuthor(author2);
        bookRepository.save(book3);

        // Find books by author ID
        List<Book> books = bookRepository.findByAuthorId(author1.getId());

        // Verify the correct books were found
        assertThat(books).hasSize(2);
        assertThat(books).extracting(Book::getTitle).containsExactlyInAnyOrder("Book 1", "Book 2");
    }

    @Test
    public void testFindByGenre() {
        // Create and save books
        Book book1 = new Book();
        book1.setTitle("Book 1");
        book1.setGenre("Fantasy");
        book1.setPageCount(300);
        book1.setAuthor(author1);
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setTitle("Book 2");
        book2.setGenre("Fantasy");
        book2.setPageCount(400);
        book2.setAuthor(author2);
        bookRepository.save(book2);

        Book book3 = new Book();
        book3.setTitle("Book 3");
        book3.setGenre("Sci-Fi");
        book3.setPageCount(250);
        book3.setAuthor(author1);
        bookRepository.save(book3);

        // Find books by genre
        List<Book> books = bookRepository.findByGenre("Fantasy");

        // Verify the correct books were found
        assertThat(books).hasSize(2);
        assertThat(books).extracting(Book::getTitle).containsExactlyInAnyOrder("Book 1", "Book 2");
    }

    @Test
    public void testFindByPriceLessThan() {
        // Create and save books
        Book book1 = new Book();
        book1.setTitle("Book 1");
        book1.setPrice(new BigDecimal("19.99"));
        book1.setPageCount(300);
        book1.setAuthor(author1);
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setTitle("Book 2");
        book2.setPrice(new BigDecimal("29.99"));
        book2.setPageCount(400);
        book2.setAuthor(author1);
        bookRepository.save(book2);

        Book book3 = new Book();
        book3.setTitle("Book 3");
        book3.setPrice(new BigDecimal("9.99"));
        book3.setPageCount(250);
        book3.setAuthor(author2);
        bookRepository.save(book3);

        // Find books with price less than 20.00
        List<Book> books = bookRepository.findByPriceLessThan(new BigDecimal("20.00"));

        // Verify the correct books were found
        assertThat(books).hasSize(2);
        assertThat(books).extracting(Book::getTitle).containsExactlyInAnyOrder("Book 1", "Book 3");
    }

    @Test
    public void testFindByIsbn() {
        // Create and save a book
        Book book = new Book();
        book.setTitle("Test Book");
        book.setIsbn("1234567890");
        book.setPageCount(200);
        book.setAuthor(author1);
        bookRepository.save(book);

        // Find the book by ISBN
        Optional<Book> foundBook = bookRepository.findByIsbn("1234567890");

        // Verify the book was found
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getTitle()).isEqualTo("Test Book");
    }

    @Test
    public void testFindByPublicationDateAfter() {
        // Create and save books
        Book book1 = new Book();
        book1.setTitle("Book 1");
        book1.setPublicationDate(LocalDate.of(2020, 1, 1));
        book1.setPageCount(300);
        book1.setAuthor(author1);
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setTitle("Book 2");
        book2.setPublicationDate(LocalDate.of(2022, 1, 1));
        book2.setPageCount(400);
        book2.setAuthor(author1);
        bookRepository.save(book2);

        Book book3 = new Book();
        book3.setTitle("Book 3");
        book3.setPublicationDate(LocalDate.of(2023, 1, 1));
        book3.setPageCount(250);
        book3.setAuthor(author2);
        bookRepository.save(book3);

        // Find books published after 2021-01-01
        List<Book> books = bookRepository.findByPublicationDateAfter(LocalDate.of(2021, 1, 1));

        // Verify the correct books were found
        assertThat(books).hasSize(2);
        assertThat(books).extracting(Book::getTitle).containsExactlyInAnyOrder("Book 2", "Book 3");
    }

    @Test
    public void testFindBooksByAuthorNameContaining() {
        // Create and save books
        Book book1 = new Book();
        book1.setTitle("Book 1");
        book1.setPageCount(300);
        book1.setAuthor(author1);
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setTitle("Book 2");
        book2.setPageCount(400);
        book2.setAuthor(author1);
        bookRepository.save(book2);

        Book book3 = new Book();
        book3.setTitle("Book 3");
        book3.setPageCount(250);
        book3.setAuthor(author2);
        bookRepository.save(book3);

        // Find books by author name containing "Author 1"
        List<Book> books = bookRepository.findBooksByAuthorNameContaining("Author 1");

        // Verify the correct books were found
        assertThat(books).hasSize(2);
        assertThat(books).extracting(Book::getTitle).containsExactlyInAnyOrder("Book 1", "Book 2");
    }

    @Test
    public void testFindAllBooksWithAuthors() {
        // Create and save books
        Book book1 = new Book();
        book1.setTitle("Book 1");
        book1.setPageCount(300);
        book1.setAuthor(author1);
        bookRepository.save(book1);

        Book book2 = new Book();
        book2.setTitle("Book 2");
        book2.setPageCount(400);
        book2.setAuthor(author2);
        bookRepository.save(book2);

        // Find all books with authors
        List<Object[]> results = bookRepository.findAllBooksWithAuthors();

        // Verify the correct books and authors were found
        assertThat(results).hasSize(2);
        
        // First result
        Book resultBook1 = (Book) results.get(0)[0];
        Author resultAuthor1 = (Author) results.get(0)[1];
        assertThat(resultBook1.getTitle()).isIn("Book 1", "Book 2");
        assertThat(resultAuthor1.getName()).isIn("Test Author 1", "Test Author 2");
        
        // Second result
        Book resultBook2 = (Book) results.get(1)[0];
        Author resultAuthor2 = (Author) results.get(1)[1];
        assertThat(resultBook2.getTitle()).isIn("Book 1", "Book 2");
        assertThat(resultAuthor2.getName()).isIn("Test Author 1", "Test Author 2");
        
        // Make sure we have different books
        assertThat(resultBook1.getTitle()).isNotEqualTo(resultBook2.getTitle());
    }
} 