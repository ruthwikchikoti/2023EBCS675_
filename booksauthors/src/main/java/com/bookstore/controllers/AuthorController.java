package com.bookstore.controllers;

import com.bookstore.dto.AuthorDTO;
import com.bookstore.services.AuthorService;
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
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String listAuthors(Model model) {
        List<AuthorDTO> authors = authorService.findAllAuthors();
        model.addAttribute("authors", authors);
        return "authors/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("author", new AuthorDTO());
        return "authors/form";
    }

    @PostMapping
    public String createAuthor(@Valid @ModelAttribute("author") AuthorDTO authorDTO, 
                              BindingResult result, 
                              RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "authors/form";
        }
        
        try {
            AuthorDTO savedAuthor = authorService.saveAuthor(authorDTO);
            redirectAttributes.addFlashAttribute("message", "Author created successfully!");
            return "redirect:/authors";
        } catch (DataIntegrityViolationException e) {
            result.rejectValue("email", "error.author", "Email already exists");
            return "authors/form";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<AuthorDTO> authorOpt = authorService.findAuthorById(id);
        
        if (authorOpt.isPresent()) {
            model.addAttribute("author", authorOpt.get());
            return "authors/form";
        } else {
            redirectAttributes.addFlashAttribute("error", "Author not found");
            return "redirect:/authors";
        }
    }

    @PostMapping("/{id}")
    public String updateAuthor(@PathVariable Long id, 
                             @Valid @ModelAttribute("author") AuthorDTO authorDTO,
                             BindingResult result, 
                             RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "authors/form";
        }
        
        try {
            AuthorDTO updatedAuthor = authorService.updateAuthor(id, authorDTO);
            redirectAttributes.addFlashAttribute("message", "Author updated successfully!");
            return "redirect:/authors";
        } catch (DataIntegrityViolationException e) {
            result.rejectValue("email", "error.author", "Email already exists");
            return "authors/form";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/authors";
        }
    }

    @GetMapping("/{id}")
    public String viewAuthor(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<AuthorDTO> authorOpt = authorService.findAuthorById(id);
        
        if (authorOpt.isPresent()) {
            model.addAttribute("author", authorOpt.get());
            return "authors/view";
        } else {
            redirectAttributes.addFlashAttribute("error", "Author not found");
            return "redirect:/authors";
        }
    }
} 