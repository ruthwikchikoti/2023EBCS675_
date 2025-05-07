package com.bookstore.repositories;

import com.bookstore.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);
    List<Book> findByAuthorId(Long authorId);
    List<Book> findByGenre(String genre);
    List<Book> findByPriceLessThan(BigDecimal price);
    Optional<Book> findByIsbn(String isbn);
    List<Book> findByPublicationDateAfter(LocalDate date);
    
    // Custom query with inner join
    @Query("SELECT b FROM Book b INNER JOIN b.author a WHERE a.name LIKE %:authorName%")
    List<Book> findBooksByAuthorNameContaining(@Param("authorName") String authorName);
    
    // Custom query to find books with author information
    @Query("SELECT b, a FROM Book b INNER JOIN b.author a")
    List<Object[]> findAllBooksWithAuthors();
    
    // Custom query to find books by genre with author information
    @Query("SELECT b, a FROM Book b INNER JOIN b.author a WHERE b.genre = :genre")
    List<Object[]> findBooksByGenreWithAuthors(@Param("genre") String genre);
} 