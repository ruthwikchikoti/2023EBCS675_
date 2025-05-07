package com.bookstore.services.impl;

import com.bookstore.dto.AuthorDTO;
import com.bookstore.entities.Author;
import com.bookstore.repositories.AuthorRepository;
import com.bookstore.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDTO> findAllAuthors() {
        return authorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthorDTO> findAuthorById(Long id) {
        return authorRepository.findById(id)
                .map(this::convertToDTO);
    }

    @Override
    public AuthorDTO saveAuthor(AuthorDTO authorDTO) {
        Author author = convertToEntity(authorDTO);
        Author savedAuthor = authorRepository.save(author);
        return convertToDTO(savedAuthor);
    }

    @Override
    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        Optional<Author> existingAuthorOpt = authorRepository.findById(id);
        
        if (existingAuthorOpt.isPresent()) {
            Author existingAuthor = existingAuthorOpt.get();
            
            // Update fields
            existingAuthor.setName(authorDTO.getName());
            existingAuthor.setEmail(authorDTO.getEmail());
            existingAuthor.setBiography(authorDTO.getBiography());
            existingAuthor.setBirthYear(authorDTO.getBirthYear());
            existingAuthor.setCountry(authorDTO.getCountry());
            
            Author updatedAuthor = authorRepository.save(existingAuthor);
            return convertToDTO(updatedAuthor);
        } else {
            throw new RuntimeException("Author not found with id: " + id);
        }
    }

    @Override
    public boolean deleteAuthor(Long id) {
        if (authorRepository.existsById(id)) {
            authorRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDTO> findAuthorsByName(String name) {
        return authorRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AuthorDTO> findAuthorByEmail(String email) {
        return authorRepository.findByEmail(email)
                .map(this::convertToDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDTO> findAuthorsByCountry(String country) {
        return authorRepository.findByCountry(country).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Helper methods to convert between entity and DTO
    private AuthorDTO convertToDTO(Author author) {
        AuthorDTO dto = new AuthorDTO();
        dto.setId(author.getId());
        dto.setName(author.getName());
        dto.setEmail(author.getEmail());
        dto.setBiography(author.getBiography());
        dto.setBirthYear(author.getBirthYear());
        dto.setCountry(author.getCountry());
        return dto;
    }

    private Author convertToEntity(AuthorDTO dto) {
        Author author = new Author();
        author.setId(dto.getId());
        author.setName(dto.getName());
        author.setEmail(dto.getEmail());
        author.setBiography(dto.getBiography());
        author.setBirthYear(dto.getBirthYear());
        author.setCountry(dto.getCountry());
        return author;
    }
} 