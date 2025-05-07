package com.bookstore.controllers;

import com.bookstore.dto.BookDTO;
import com.bookstore.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final BookService bookService;

    @Autowired
    public HomeController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<BookDTO> recentBooks = bookService.findAllBooksWithAuthors();
        model.addAttribute("recentBooks", recentBooks);
        return "home";
    }
} 