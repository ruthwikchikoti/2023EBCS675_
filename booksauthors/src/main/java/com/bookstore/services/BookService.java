package com.bookstore.services;

import com.bookstore.dto.BookDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookDTO> findAllBooks();
    Optional<BookDTO> findBookById(Long id);
    BookDTO saveBook(BookDTO bookDTO);
    BookDTO updateBook(Long id, BookDTO bookDTO);
    boolean deleteBook(Long id);
    List<BookDTO> findBooksByTitle(String title);
    List<BookDTO> findBooksByAuthorId(Long authorId);
    List<BookDTO> findBooksByGenre(String genre);
    List<BookDTO> findBooksByPriceLessThan(BigDecimal price);
    Optional<BookDTO> findBookByIsbn(String isbn);
    List<BookDTO> findBooksByPublicationDateAfter(LocalDate date);
    List<BookDTO> findBooksByAuthorName(String authorName);
    
    // Method for the custom query with inner join
    List<BookDTO> findAllBooksWithAuthors();
    List<BookDTO> findBooksByGenreWithAuthors(String genre);
} 