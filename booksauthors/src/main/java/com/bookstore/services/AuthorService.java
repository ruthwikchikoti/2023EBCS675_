package com.bookstore.services;

import com.bookstore.dto.AuthorDTO;

import java.util.List;
import java.util.Optional;

public interface AuthorService {
    List<AuthorDTO> findAllAuthors();
    Optional<AuthorDTO> findAuthorById(Long id);
    AuthorDTO saveAuthor(AuthorDTO authorDTO);
    AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO);
    boolean deleteAuthor(Long id);
    List<AuthorDTO> findAuthorsByName(String name);
    Optional<AuthorDTO> findAuthorByEmail(String email);
    List<AuthorDTO> findAuthorsByCountry(String country);
} 