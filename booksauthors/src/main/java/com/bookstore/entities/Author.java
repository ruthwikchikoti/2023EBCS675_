package com.bookstore.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 100, message = "Name must be between 3 and 100 characters")
    @Column(nullable = false)
    private String name;
    
    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;
    
    @Size(max = 1000, message = "Biography cannot exceed 1000 characters")
    @Column(length = 1000)
    private String biography;
    
    @Column(name = "birth_year")
    private Integer birthYear;
    
    @Column(name = "country")
    private String country;
    
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books = new ArrayList<>();
    
    // Helper method to add book
    public void addBook(Book book) {
        books.add(book);
        book.setAuthor(this);
    }
    
    // Helper method to remove book
    public void removeBook(Book book) {
        books.remove(book);
        book.setAuthor(null);
    }
} 