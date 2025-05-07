package com.bookstore.services;

import com.bookstore.dto.BookDTO;
import com.bookstore.entities.Author;
import com.bookstore.entities.Book;
import com.bookstore.repositories.AuthorRepository;
import com.bookstore.repositories.BookRepository;
import com.bookstore.services.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Author author;
    private Book book1;
    private Book book2;
    private BookDTO bookDTO1;

    @BeforeEach
    public void setup() {
        // Create test author
        author = new Author();
        author.setId(1L);
        author.setName("Test Author");
        author.setEmail("author@example.com");

        // Create test books
        book1 = new Book();
        book1.setId(1L);
        book1.setTitle("Test Book 1");
        book1.setIsbn("1234567890");
        book1.setPublicationDate(LocalDate.of(2023, 1, 1));
        book1.setPrice(new BigDecimal("19.99"));
        book1.setDescription("Test description 1");
        book1.setPageCount(200);
        book1.setGenre("Fantasy");
        book1.setAuthor(author);

        book2 = new Book();
        book2.setId(2L);
        book2.setTitle("Test Book 2");
        book2.setIsbn("0987654321");
        book2.setPublicationDate(LocalDate.of(2023, 2, 1));
        book2.setPrice(new BigDecimal("29.99"));
        book2.setDescription("Test description 2");
        book2.setPageCount(300);
        book2.setGenre("Sci-Fi");
        book2.setAuthor(author);

        // Create test DTO
        bookDTO1 = new BookDTO();
        bookDTO1.setId(1L);
        bookDTO1.setTitle("Test Book 1");
        bookDTO1.setIsbn("1234567890");
        bookDTO1.setPublicationDate(LocalDate.of(2023, 1, 1));
        bookDTO1.setPrice(new BigDecimal("19.99"));
        bookDTO1.setDescription("Test description 1");
        bookDTO1.setPageCount(200);
        bookDTO1.setGenre("Fantasy");
        bookDTO1.setAuthorId(1L);
        bookDTO1.setAuthorName("Test Author");
    }

    @Test
    public void testFindAllBooks() {
        // Mock repository behavior
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // Call service method
        List<BookDTO> books = bookService.findAllBooks();

        // Verify repository was called
        verify(bookRepository, times(1)).findAll();

        // Verify results
        assertThat(books).hasSize(2);
        assertThat(books.get(0).getTitle()).isEqualTo("Test Book 1");
        assertThat(books.get(1).getTitle()).isEqualTo("Test Book 2");
    }

    @Test
    public void testFindBookById() {
        // Mock repository behavior
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        when(bookRepository.findById(3L)).thenReturn(Optional.empty());

        // Call service method
        Optional<BookDTO> foundBook = bookService.findBookById(1L);
        Optional<BookDTO> notFoundBook = bookService.findBookById(3L);

        // Verify repository was called
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).findById(3L);

        // Verify results
        assertThat(foundBook).isPresent();
        assertThat(foundBook.get().getTitle()).isEqualTo("Test Book 1");
        assertThat(notFoundBook).isEmpty();
    }

    @Test
    public void testSaveBook() {
        // Mock repository behavior
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenReturn(book1);

        // Call service method
        BookDTO savedBook = bookService.saveBook(bookDTO1);

        // Verify repository was called
        verify(authorRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));

        // Verify results
        assertThat(savedBook).isNotNull();
        assertThat(savedBook.getId()).isEqualTo(1L);
        assertThat(savedBook.getTitle()).isEqualTo("Test Book 1");
        assertThat(savedBook.getAuthorName()).isEqualTo("Test Author");
    }

    @Test
    public void testUpdateBook() {
        // Create updated DTO
        BookDTO updatedDTO = new BookDTO();
        updatedDTO.setId(1L);
        updatedDTO.setTitle("Updated Book");
        updatedDTO.setIsbn("9876543210");
        updatedDTO.setPublicationDate(LocalDate.of(2023, 3, 1));
        updatedDTO.setPrice(new BigDecimal("24.99"));
        updatedDTO.setDescription("Updated description");
        updatedDTO.setPageCount(250);
        updatedDTO.setGenre("Mystery");
        updatedDTO.setAuthorId(1L);

        // Create updated entity
        Book updatedBook = new Book();
        updatedBook.setId(1L);
        updatedBook.setTitle("Updated Book");
        updatedBook.setIsbn("9876543210");
        updatedBook.setPublicationDate(LocalDate.of(2023, 3, 1));
        updatedBook.setPrice(new BigDecimal("24.99"));
        updatedBook.setDescription("Updated description");
        updatedBook.setPageCount(250);
        updatedBook.setGenre("Mystery");
        updatedBook.setAuthor(author);

        // Mock repository behavior
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book1));
        when(bookRepository.save(any(Book.class))).thenReturn(updatedBook);

        // Call service method
        BookDTO result = bookService.updateBook(1L, updatedDTO);

        // Verify repository was called
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));

        // Verify results
        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Updated Book");
        assertThat(result.getIsbn()).isEqualTo("9876543210");
        assertThat(result.getPublicationDate()).isEqualTo(LocalDate.of(2023, 3, 1));
        assertThat(result.getPrice()).isEqualTo(new BigDecimal("24.99"));
        assertThat(result.getDescription()).isEqualTo("Updated description");
        assertThat(result.getPageCount()).isEqualTo(250);
        assertThat(result.getGenre()).isEqualTo("Mystery");
    }

    @Test
    public void testDeleteBook() {
        // Mock repository behavior
        when(bookRepository.existsById(1L)).thenReturn(true);
        when(bookRepository.existsById(3L)).thenReturn(false);
        doNothing().when(bookRepository).deleteById(anyLong());

        // Call service method
        boolean result1 = bookService.deleteBook(1L);
        boolean result2 = bookService.deleteBook(3L);

        // Verify repository was called
        verify(bookRepository, times(1)).existsById(1L);
        verify(bookRepository, times(1)).existsById(3L);
        verify(bookRepository, times(1)).deleteById(1L);
        verify(bookRepository, never()).deleteById(3L);

        // Verify results
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @Test
    public void testFindBooksByTitle() {
        // Mock repository behavior
        when(bookRepository.findByTitleContainingIgnoreCase("Test")).thenReturn(Arrays.asList(book1, book2));

        // Call service method
        List<BookDTO> books = bookService.findBooksByTitle("Test");

        // Verify repository was called
        verify(bookRepository, times(1)).findByTitleContainingIgnoreCase("Test");

        // Verify results
        assertThat(books).hasSize(2);
        assertThat(books.get(0).getTitle()).isEqualTo("Test Book 1");
        assertThat(books.get(1).getTitle()).isEqualTo("Test Book 2");
    }

    @Test
    public void testFindBooksByAuthorId() {
        // Mock repository behavior
        when(bookRepository.findByAuthorId(1L)).thenReturn(Arrays.asList(book1, book2));

        // Call service method
        List<BookDTO> books = bookService.findBooksByAuthorId(1L);

        // Verify repository was called
        verify(bookRepository, times(1)).findByAuthorId(1L);

        // Verify results
        assertThat(books).hasSize(2);
        assertThat(books.get(0).getTitle()).isEqualTo("Test Book 1");
        assertThat(books.get(1).getTitle()).isEqualTo("Test Book 2");
    }

    @Test
    public void testFindBooksByGenre() {
        // Mock repository behavior
        when(bookRepository.findByGenre("Fantasy")).thenReturn(Arrays.asList(book1));

        // Call service method
        List<BookDTO> books = bookService.findBooksByGenre("Fantasy");

        // Verify repository was called
        verify(bookRepository, times(1)).findByGenre("Fantasy");

        // Verify results
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo("Test Book 1");
    }

    @Test
    public void testFindBooksByAuthorName() {
        // Mock repository behavior
        when(bookRepository.findBooksByAuthorNameContaining("Test Author")).thenReturn(Arrays.asList(book1, book2));

        // Call service method
        List<BookDTO> books = bookService.findBooksByAuthorName("Test Author");

        // Verify repository was called
        verify(bookRepository, times(1)).findBooksByAuthorNameContaining("Test Author");

        // Verify results
        assertThat(books).hasSize(2);
        assertThat(books.get(0).getTitle()).isEqualTo("Test Book 1");
        assertThat(books.get(1).getTitle()).isEqualTo("Test Book 2");
    }

    @Test
    public void testFindAllBooksWithAuthors() {
        // Create test result
        Object[] result1 = new Object[]{book1, author};
        Object[] result2 = new Object[]{book2, author};

        // Mock repository behavior
        when(bookRepository.findAllBooksWithAuthors()).thenReturn(Arrays.asList(result1, result2));

        // Call service method
        List<BookDTO> books = bookService.findAllBooksWithAuthors();

        // Verify repository was called
        verify(bookRepository, times(1)).findAllBooksWithAuthors();

        // Verify results
        assertThat(books).hasSize(2);
        assertThat(books.get(0).getTitle()).isEqualTo("Test Book 1");
        assertThat(books.get(0).getAuthorName()).isEqualTo("Test Author");
        assertThat(books.get(1).getTitle()).isEqualTo("Test Book 2");
        assertThat(books.get(1).getAuthorName()).isEqualTo("Test Author");
    }

    @Test
    public void testFindBooksByGenreWithAuthors() {
        // Create test result
        Object[] result1 = new Object[]{book1, author};

        // Mock repository behavior
        when(bookRepository.findBooksByGenreWithAuthors("Fantasy")).thenReturn(Arrays.asList(result1));

        // Call service method
        List<BookDTO> books = bookService.findBooksByGenreWithAuthors("Fantasy");

        // Verify repository was called
        verify(bookRepository, times(1)).findBooksByGenreWithAuthors("Fantasy");

        // Verify results
        assertThat(books).hasSize(1);
        assertThat(books.get(0).getTitle()).isEqualTo("Test Book 1");
        assertThat(books.get(0).getGenre()).isEqualTo("Fantasy");
        assertThat(books.get(0).getAuthorName()).isEqualTo("Test Author");
    }
} 