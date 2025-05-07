package com.bookstore.controllers;

import com.bookstore.dto.AuthorDTO;
import com.bookstore.dto.BookDTO;
import com.bookstore.services.AuthorService;
import com.bookstore.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping
    public String listBooks(Model model) {
        List<BookDTO> books = bookService.findAllBooksWithAuthors();
        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new BookDTO());
        List<AuthorDTO> authors = authorService.findAllAuthors();
        model.addAttribute("authors", authors);
        return "books/form";
    }

    @PostMapping
    public String createBook(@Valid @ModelAttribute("book") BookDTO bookDTO,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<AuthorDTO> authors = authorService.findAllAuthors();
            model.addAttribute("authors", authors);
            return "books/form";
        }

        try {
            BookDTO savedBook = bookService.saveBook(bookDTO);
            redirectAttributes.addFlashAttribute("message", "Book created successfully!");
            return "redirect:/books";
        } catch (DataIntegrityViolationException e) {
            result.rejectValue("isbn", "error.book", "ISBN already exists");
            List<AuthorDTO> authors = authorService.findAllAuthors();
            model.addAttribute("authors", authors);
            return "books/form";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/books";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<BookDTO> bookOpt = bookService.findBookById(id);

        if (bookOpt.isPresent()) {
            model.addAttribute("book", bookOpt.get());
            List<AuthorDTO> authors = authorService.findAllAuthors();
            model.addAttribute("authors", authors);
            return "books/form";
        } else {
            redirectAttributes.addFlashAttribute("error", "Book not found");
            return "redirect:/books";
        }
    }

    @PostMapping("/{id}")
    public String updateBook(@PathVariable Long id,
                           @Valid @ModelAttribute("book") BookDTO bookDTO,
                           BindingResult result,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            List<AuthorDTO> authors = authorService.findAllAuthors();
            model.addAttribute("authors", authors);
            return "books/form";
        }

        try {
            BookDTO updatedBook = bookService.updateBook(id, bookDTO);
            redirectAttributes.addFlashAttribute("message", "Book updated successfully!");
            return "redirect:/books";
        } catch (DataIntegrityViolationException e) {
            result.rejectValue("isbn", "error.book", "ISBN already exists");
            List<AuthorDTO> authors = authorService.findAllAuthors();
            model.addAttribute("authors", authors);
            return "books/form";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/books";
        }
    }

    @GetMapping("/{id}")
    public String viewBook(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<BookDTO> bookOpt = bookService.findBookById(id);

        if (bookOpt.isPresent()) {
            model.addAttribute("book", bookOpt.get());
            return "books/view";
        } else {
            redirectAttributes.addFlashAttribute("error", "Book not found");
            return "redirect:/books";
        }
    }

    @GetMapping("/by-author/{authorId}")
    public String listBooksByAuthor(@PathVariable Long authorId, Model model, RedirectAttributes redirectAttributes) {
        Optional<AuthorDTO> authorOpt = authorService.findAuthorById(authorId);
        
        if (authorOpt.isPresent()) {
            List<BookDTO> books = bookService.findBooksByAuthorId(authorId);
            model.addAttribute("books", books);
            model.addAttribute("author", authorOpt.get());
            return "books/by-author";
        } else {
            redirectAttributes.addFlashAttribute("error", "Author not found");
            return "redirect:/books";
        }
    }
    
    @GetMapping("/by-genre")
    public String listBooksByGenre(@RequestParam String genre, Model model) {
        List<BookDTO> books = bookService.findBooksByGenreWithAuthors(genre);
        model.addAttribute("books", books);
        model.addAttribute("genre", genre);
        return "books/by-genre";
    }
} 