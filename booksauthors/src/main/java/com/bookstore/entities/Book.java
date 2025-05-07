package com.bookstore.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
    @Column(nullable = false)
    private String title;
    
    @Column(name = "isbn", unique = true)
    private String isbn;
    
    @Column(name = "publication_date")
    private LocalDate publicationDate;
    
    @Min(value = 0, message = "Price cannot be negative")
    private BigDecimal price;
    
    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    @Column(length = 2000)
    private String description;
    
    @NotNull(message = "Page count is required")
    @Min(value = 1, message = "Page count must be at least 1")
    @Column(name = "page_count")
    private Integer pageCount;
    
    @Column(name = "genre")
    private String genre;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;
} 