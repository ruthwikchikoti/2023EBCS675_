package com.bookstore.services.impl;

import com.bookstore.dto.BookDTO;
import com.bookstore.entities.Author;
import com.bookstore.entities.Book;
import com.bookstore.repositories.AuthorRepository;
import com.bookstore.repositories.BookRepository;
import com.bookstore.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> findAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDTO> findBookById(Long id) {
        return bookRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public BookDTO saveBook(BookDTO bookDTO) {
        Book book = convertToEntity(bookDTO);
        Book savedBook = bookRepository.save(book);
        return convertToDTO(savedBook);
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Optional<Book> existingBookOpt = bookRepository.findById(id);
        
        if (existingBookOpt.isPresent()) {
            Book existingBook = existingBookOpt.get();
            
            // Update fields
            existingBook.setTitle(bookDTO.getTitle());
            existingBook.setIsbn(bookDTO.getIsbn());
            existingBook.setPublicationDate(bookDTO.getPublicationDate());
            existingBook.setPrice(bookDTO.getPrice());
            existingBook.setDescription(bookDTO.getDescription());
            existingBook.setPageCount(bookDTO.getPageCount());
            existingBook.setGenre(bookDTO.getGenre());
            
            // Update author if changed
            if (!existingBook.getAuthor().getId().equals(bookDTO.getAuthorId())) {
                Author newAuthor = authorRepository.findById(bookDTO.getAuthorId())
                        .orElseThrow(() -> new RuntimeException("Author not found with id: " + bookDTO.getAuthorId()));
                existingBook.setAuthor(newAuthor);
            }
            
            Book updatedBook = bookRepository.save(existingBook);
            return convertToDTO(updatedBook);
        } else {
            throw new RuntimeException("Book not found with id: " + id);
        }
    }

    @Override
    public boolean deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> findBooksByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> findBooksByAuthorId(Long authorId) {
        return bookRepository.findByAuthorId(authorId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> findBooksByGenre(String genre) {
        return bookRepository.findByGenre(genre).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> findBooksByPriceLessThan(BigDecimal price) {
        return bookRepository.findByPriceLessThan(price).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDTO> findBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> findBooksByPublicationDateAfter(LocalDate date) {
        return bookRepository.findByPublicationDateAfter(date).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> findBooksByAuthorName(String authorName) {
        return bookRepository.findBooksByAuthorNameContaining(authorName).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> findAllBooksWithAuthors() {
        List<Object[]> results = bookRepository.findAllBooksWithAuthors();
        List<BookDTO> bookDTOs = new ArrayList<>();
        
        for (Object[] result : results) {
            Book book = (Book) result[0];
            Author author = (Author) result[1];
            BookDTO bookDTO = convertToDTO(book);
            bookDTO.setAuthorName(author.getName());
            bookDTOs.add(bookDTO);
        }
        
        return bookDTOs;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> findBooksByGenreWithAuthors(String genre) {
        List<Object[]> results = bookRepository.findBooksByGenreWithAuthors(genre);
        List<BookDTO> bookDTOs = new ArrayList<>();
        
        for (Object[] result : results) {
            Book book = (Book) result[0];
            Author author = (Author) result[1];
            BookDTO bookDTO = convertToDTO(book);
            bookDTO.setAuthorName(author.getName());
            bookDTOs.add(bookDTO);
        }
        
        return bookDTOs;
    }

    // Helper methods to convert between entity and DTO
    private BookDTO convertToDTO(Book book) {
        BookDTO dto = new BookDTO();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setIsbn(book.getIsbn());
        dto.setPublicationDate(book.getPublicationDate());
        dto.setPrice(book.getPrice());
        dto.setDescription(book.getDescription());
        dto.setPageCount(book.getPageCount());
        dto.setGenre(book.getGenre());
        
        if (book.getAuthor() != null) {
            dto.setAuthorId(book.getAuthor().getId());
            dto.setAuthorName(book.getAuthor().getName());
        }
        
        return dto;
    }

    private Book convertToEntity(BookDTO dto) {
        Book book = new Book();
        book.setId(dto.getId());
        book.setTitle(dto.getTitle());
        book.setIsbn(dto.getIsbn());
        book.setPublicationDate(dto.getPublicationDate());
        book.setPrice(dto.getPrice());
        book.setDescription(dto.getDescription());
        book.setPageCount(dto.getPageCount());
        book.setGenre(dto.getGenre());
        
        if (dto.getAuthorId() != null) {
            Author author = authorRepository.findById(dto.getAuthorId())
                    .orElseThrow(() -> new RuntimeException("Author not found with id: " + dto.getAuthorId()));
            book.setAuthor(author);
        }
        
        return book;
    }
} 