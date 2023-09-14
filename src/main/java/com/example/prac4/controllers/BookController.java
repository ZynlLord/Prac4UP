package com.example.prac4.controllers;

import com.example.prac4.models.Author;
import com.example.prac4.models.Book;
import com.example.prac4.models.Library;
import com.example.prac4.repositories.AuthorRepository;
import com.example.prac4.repositories.BookRepository;
import com.example.prac4.repositories.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private LibraryRepository libraryRepository;

    @GetMapping("")
    public String getAllBooks(Model model) {
        List<Book> books = bookRepository.findAll();
        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("/create")
    public String showBookForm(Model model) {
        List<Author> authors = authorRepository.findAll();
        List<Library> libraries = libraryRepository.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("libraries", libraries);
        model.addAttribute("book", new Book());
        return "books/create";
    }

    @PostMapping("/create")
    public String createBook(@Valid Book book) {
        bookRepository.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book ID: " + id));
        List<Author> authors = authorRepository.findAll();
        List<Library> libraries = libraryRepository.findAll();
        model.addAttribute("book", book);
        model.addAttribute("authors", authors);
        model.addAttribute("libraries", libraries);
        return "books/edit";
    }

    @PostMapping("/{id}/edit")
    public String updateBook(@PathVariable Long id, @Valid Book book) {
        book.setId(id);
        bookRepository.save(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/delete")
    public String deleteBook(@PathVariable Long id) {
        bookRepository.deleteById(id);
        return "redirect:/books";
    }
}
