/*
 * REST controller for handling HTTP requests related to Book entities.
 * 
 * Maps requests under "/api/books" and delegates operations to the BookService.
 * Provides endpoints to:
 * - Retrieve all books or filter by author, title, or ISDN.
 * - Create new books via POST.
 * - Update existing books via PUT.
 * - Delete books via DELETE.
 * 
 * Uses Spring's @RestController for RESTful web services and
 * @Autowired to inject the BookService dependency.
 * 
 * Note: Some endpoint mappings use path variables that may conflict (e.g., author, title, isdn),
 * consider differentiating the URL patterns to avoid ambiguity.
 */

package com.endpoint.endpoint.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.endpoint.endpoint.dto.BookDTO;
import com.endpoint.endpoint.model.Author;
import com.endpoint.endpoint.model.Book;
import com.endpoint.endpoint.services.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping()
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public List<BookDTO> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/author/{author}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Optional<List<BookDTO>> getBooksByAuthor(@PathVariable Author author) {
        return bookService.getBookByAuthor(author);
    }

    @GetMapping("/title/{title}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Optional<BookDTO> getBookByTitle(@PathVariable String title) {
        return bookService.getByTitle(title);
    }

    @GetMapping("/isdn/{isdn}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Optional<BookDTO> getBookByIsdn(@PathVariable String isdn) {
        return bookService.getByIsdn(isdn);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createBook(@RequestBody @Valid BookDTO bookDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        BookDTO createdBook = bookService.createBook(bookDto);
        return ResponseEntity.ok(createdBook);
    }

    @PutMapping("/{isdn}/{title}/{content}/{author}/{releaseDate}")
    @PreAuthorize("hasRole('ADMIN')")
    public BookDTO updateBook(@PathVariable("isdn") String isdn, @PathVariable("title") String title,
            @PathVariable("content") String content, @PathVariable("author") Author author,
            @PathVariable("date") Date releaseDate, @RequestBody @Valid Book book) {
        return bookService.updateBook(isdn, title, content, author, releaseDate, book);
    }

    @DeleteMapping("/{isdn}")
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteBook(@PathVariable String isdn) {
        return bookService.deleteBook(isdn);
    }
}