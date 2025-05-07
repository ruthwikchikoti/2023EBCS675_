package com.bookstore.config;

import com.bookstore.entities.Author;
import com.bookstore.entities.Book;
import com.bookstore.repositories.AuthorRepository;
import com.bookstore.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @Autowired
    public DatabaseInitializer(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) {
        // Check if database is already populated
        if (authorRepository.count() > 0) {
            return;
        }

        // Create authors
        List<Author> authors = Arrays.asList(
            createAuthor("J.K. Rowling", "jkrowling@example.com", "British author best known for the Harry Potter series", 1965, "United Kingdom"),
            createAuthor("George Orwell", "georgeorwell@example.com", "English novelist, essayist, journalist, and critic", 1903, "United Kingdom"),
            createAuthor("Jane Austen", "janeausten@example.com", "English novelist known for her six major novels", 1775, "United Kingdom"),
            createAuthor("Ernest Hemingway", "ehemingway@example.com", "American novelist, short-story writer, and journalist", 1899, "USA"),
            createAuthor("Agatha Christie", "achristie@example.com", "English writer known for her detective novels", 1890, "United Kingdom"),
            createAuthor("Gabriel García Márquez", "ggmarquez@example.com", "Colombian novelist and Nobel Prize winner", 1927, "Colombia"),
            createAuthor("Leo Tolstoy", "ltolstoy@example.com", "Russian writer regarded as one of the greatest authors of all time", 1828, "Russia"),
            createAuthor("Stephen King", "sking@example.com", "American author of horror, supernatural fiction, and fantasy", 1947, "USA"),
            createAuthor("Virginia Woolf", "vwoolf@example.com", "English writer and modernist", 1882, "United Kingdom"),
            createAuthor("Franz Kafka", "fkafka@example.com", "German-speaking Bohemian novelist and short-story writer", 1883, "Czech Republic")
        );
        
        authorRepository.saveAll(authors);

        // Create books
        List<Book> books = Arrays.asList(
            createBook("Harry Potter and the Philosopher's Stone", "9780747532743", LocalDate.of(1997, 6, 26), 
                    new BigDecimal("19.99"), "The first novel in the Harry Potter series", 223, "Fantasy", authors.get(0)),
            createBook("1984", "9780451524935", LocalDate.of(1949, 6, 8), 
                    new BigDecimal("12.99"), "A dystopian social science fiction novel", 328, "Dystopian", authors.get(1)),
            createBook("Pride and Prejudice", "9780141439518", LocalDate.of(1813, 1, 28), 
                    new BigDecimal("9.99"), "A romantic novel of manners", 432, "Romance", authors.get(2)),
            createBook("The Old Man and the Sea", "9780684801223", LocalDate.of(1952, 9, 1), 
                    new BigDecimal("11.99"), "A short novel about an aging Cuban fisherman", 127, "Fiction", authors.get(3)),
            createBook("Murder on the Orient Express", "9780062693662", LocalDate.of(1934, 1, 1), 
                    new BigDecimal("14.99"), "A detective novel featuring Hercule Poirot", 256, "Mystery", authors.get(4)),
            createBook("One Hundred Years of Solitude", "9780060883287", LocalDate.of(1967, 5, 30), 
                    new BigDecimal("15.99"), "A landmark of magical realism", 417, "Magical Realism", authors.get(5)),
            createBook("War and Peace", "9781400079988", LocalDate.of(1869, 1, 1), 
                    new BigDecimal("24.99"), "A novel about Russian society during the Napoleonic era", 1225, "Historical Fiction", authors.get(6)),
            createBook("The Shining", "9780307743657", LocalDate.of(1977, 1, 28), 
                    new BigDecimal("16.99"), "A horror novel set in an isolated hotel", 447, "Horror", authors.get(7)),
            createBook("To the Lighthouse", "9780156907392", LocalDate.of(1927, 5, 5), 
                    new BigDecimal("13.99"), "A novel centered on the Ramsay family", 209, "Modernist", authors.get(8)),
            createBook("The Metamorphosis", "9780553213690", LocalDate.of(1915, 10, 15), 
                    new BigDecimal("8.99"), "A novella about a man who transforms into an insect", 201, "Absurdist Fiction", authors.get(9)),
            createBook("Harry Potter and the Chamber of Secrets", "9780747538486", LocalDate.of(1998, 7, 2), 
                    new BigDecimal("19.99"), "The second novel in the Harry Potter series", 251, "Fantasy", authors.get(0)),
            createBook("Animal Farm", "9780451526342", LocalDate.of(1945, 8, 17), 
                    new BigDecimal("10.99"), "An allegorical novella", 112, "Political Satire", authors.get(1)),
            createBook("Sense and Sensibility", "9780141439662", LocalDate.of(1811, 10, 30), 
                    new BigDecimal("9.99"), "A novel about the Dashwood sisters", 409, "Romance", authors.get(2)),
            createBook("For Whom the Bell Tolls", "9780684803357", LocalDate.of(1940, 10, 21), 
                    new BigDecimal("14.99"), "A novel set during the Spanish Civil War", 471, "War Fiction", authors.get(3)),
            createBook("Death on the Nile", "9780062073556", LocalDate.of(1937, 11, 1), 
                    new BigDecimal("13.99"), "A mystery novel featuring Hercule Poirot", 333, "Mystery", authors.get(4))
        );
        
        bookRepository.saveAll(books);
    }

    private Author createAuthor(String name, String email, String biography, int birthYear, String country) {
        Author author = new Author();
        author.setName(name);
        author.setEmail(email);
        author.setBiography(biography);
        author.setBirthYear(birthYear);
        author.setCountry(country);
        return author;
    }

    private Book createBook(String title, String isbn, LocalDate publicationDate, BigDecimal price, 
                           String description, int pageCount, String genre, Author author) {
        Book book = new Book();
        book.setTitle(title);
        book.setIsbn(isbn);
        book.setPublicationDate(publicationDate);
        book.setPrice(price);
        book.setDescription(description);
        book.setPageCount(pageCount);
        book.setGenre(genre);
        book.setAuthor(author);
        return book;
    }
} 