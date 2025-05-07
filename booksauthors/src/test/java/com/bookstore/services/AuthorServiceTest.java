package com.bookstore.services;

import com.bookstore.dto.AuthorDTO;
import com.bookstore.entities.Author;
import com.bookstore.repositories.AuthorRepository;
import com.bookstore.services.impl.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private Author author1;
    private Author author2;
    private AuthorDTO authorDTO1;

    @BeforeEach
    public void setup() {
        // Create test authors
        author1 = new Author();
        author1.setId(1L);
        author1.setName("Test Author 1");
        author1.setEmail("author1@example.com");
        author1.setBiography("Test biography 1");
        author1.setBirthYear(1980);
        author1.setCountry("USA");

        author2 = new Author();
        author2.setId(2L);
        author2.setName("Test Author 2");
        author2.setEmail("author2@example.com");
        author2.setBiography("Test biography 2");
        author2.setBirthYear(1990);
        author2.setCountry("UK");

        // Create test DTO
        authorDTO1 = new AuthorDTO();
        authorDTO1.setId(1L);
        authorDTO1.setName("Test Author 1");
        authorDTO1.setEmail("author1@example.com");
        authorDTO1.setBiography("Test biography 1");
        authorDTO1.setBirthYear(1980);
        authorDTO1.setCountry("USA");
    }

    @Test
    public void testFindAllAuthors() {
        // Mock repository behavior
        when(authorRepository.findAll()).thenReturn(Arrays.asList(author1, author2));

        // Call service method
        List<AuthorDTO> authors = authorService.findAllAuthors();

        // Verify repository was called
        verify(authorRepository, times(1)).findAll();

        // Verify results
        assertThat(authors).hasSize(2);
        assertThat(authors.get(0).getName()).isEqualTo("Test Author 1");
        assertThat(authors.get(1).getName()).isEqualTo("Test Author 2");
    }

    @Test
    public void testFindAuthorById() {
        // Mock repository behavior
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author1));
        when(authorRepository.findById(3L)).thenReturn(Optional.empty());

        // Call service method
        Optional<AuthorDTO> foundAuthor = authorService.findAuthorById(1L);
        Optional<AuthorDTO> notFoundAuthor = authorService.findAuthorById(3L);

        // Verify repository was called
        verify(authorRepository, times(1)).findById(1L);
        verify(authorRepository, times(1)).findById(3L);

        // Verify results
        assertThat(foundAuthor).isPresent();
        assertThat(foundAuthor.get().getName()).isEqualTo("Test Author 1");
        assertThat(notFoundAuthor).isEmpty();
    }

    @Test
    public void testSaveAuthor() {
        // Mock repository behavior
        when(authorRepository.save(any(Author.class))).thenReturn(author1);

        // Call service method
        AuthorDTO savedAuthor = authorService.saveAuthor(authorDTO1);

        // Verify repository was called
        verify(authorRepository, times(1)).save(any(Author.class));

        // Verify results
        assertThat(savedAuthor).isNotNull();
        assertThat(savedAuthor.getId()).isEqualTo(1L);
        assertThat(savedAuthor.getName()).isEqualTo("Test Author 1");
    }

    @Test
    public void testUpdateAuthor() {
        // Create updated DTO
        AuthorDTO updatedDTO = new AuthorDTO();
        updatedDTO.setId(1L);
        updatedDTO.setName("Updated Author");
        updatedDTO.setEmail("updated@example.com");
        updatedDTO.setBiography("Updated biography");
        updatedDTO.setBirthYear(1985);
        updatedDTO.setCountry("Canada");

        // Create updated entity
        Author updatedAuthor = new Author();
        updatedAuthor.setId(1L);
        updatedAuthor.setName("Updated Author");
        updatedAuthor.setEmail("updated@example.com");
        updatedAuthor.setBiography("Updated biography");
        updatedAuthor.setBirthYear(1985);
        updatedAuthor.setCountry("Canada");

        // Mock repository behavior
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author1));
        when(authorRepository.save(any(Author.class))).thenReturn(updatedAuthor);

        // Call service method
        AuthorDTO result = authorService.updateAuthor(1L, updatedDTO);

        // Verify repository was called
        verify(authorRepository, times(1)).findById(1L);
        verify(authorRepository, times(1)).save(any(Author.class));

        // Verify results
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("Updated Author");
        assertThat(result.getEmail()).isEqualTo("updated@example.com");
        assertThat(result.getBiography()).isEqualTo("Updated biography");
        assertThat(result.getBirthYear()).isEqualTo(1985);
        assertThat(result.getCountry()).isEqualTo("Canada");
    }

    @Test
    public void testDeleteAuthor() {
        // Mock repository behavior
        when(authorRepository.existsById(1L)).thenReturn(true);
        when(authorRepository.existsById(3L)).thenReturn(false);
        doNothing().when(authorRepository).deleteById(anyLong());

        // Call service method
        boolean result1 = authorService.deleteAuthor(1L);
        boolean result2 = authorService.deleteAuthor(3L);

        // Verify repository was called
        verify(authorRepository, times(1)).existsById(1L);
        verify(authorRepository, times(1)).existsById(3L);
        verify(authorRepository, times(1)).deleteById(1L);
        verify(authorRepository, never()).deleteById(3L);

        // Verify results
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @Test
    public void testFindAuthorsByName() {
        // Mock repository behavior
        when(authorRepository.findByNameContainingIgnoreCase("Test")).thenReturn(Arrays.asList(author1, author2));

        // Call service method
        List<AuthorDTO> authors = authorService.findAuthorsByName("Test");

        // Verify repository was called
        verify(authorRepository, times(1)).findByNameContainingIgnoreCase("Test");

        // Verify results
        assertThat(authors).hasSize(2);
        assertThat(authors.get(0).getName()).isEqualTo("Test Author 1");
        assertThat(authors.get(1).getName()).isEqualTo("Test Author 2");
    }

    @Test
    public void testFindAuthorByEmail() {
        // Mock repository behavior
        when(authorRepository.findByEmail("author1@example.com")).thenReturn(Optional.of(author1));
        when(authorRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // Call service method
        Optional<AuthorDTO> foundAuthor = authorService.findAuthorByEmail("author1@example.com");
        Optional<AuthorDTO> notFoundAuthor = authorService.findAuthorByEmail("nonexistent@example.com");

        // Verify repository was called
        verify(authorRepository, times(1)).findByEmail("author1@example.com");
        verify(authorRepository, times(1)).findByEmail("nonexistent@example.com");

        // Verify results
        assertThat(foundAuthor).isPresent();
        assertThat(foundAuthor.get().getName()).isEqualTo("Test Author 1");
        assertThat(notFoundAuthor).isEmpty();
    }

    @Test
    public void testFindAuthorsByCountry() {
        // Mock repository behavior
        when(authorRepository.findByCountry("USA")).thenReturn(Arrays.asList(author1));
        when(authorRepository.findByCountry("UK")).thenReturn(Arrays.asList(author2));

        // Call service method
        List<AuthorDTO> usaAuthors = authorService.findAuthorsByCountry("USA");
        List<AuthorDTO> ukAuthors = authorService.findAuthorsByCountry("UK");

        // Verify repository was called
        verify(authorRepository, times(1)).findByCountry("USA");
        verify(authorRepository, times(1)).findByCountry("UK");

        // Verify results
        assertThat(usaAuthors).hasSize(1);
        assertThat(usaAuthors.get(0).getName()).isEqualTo("Test Author 1");
        assertThat(ukAuthors).hasSize(1);
        assertThat(ukAuthors.get(0).getName()).isEqualTo("Test Author 2");
    }
} 