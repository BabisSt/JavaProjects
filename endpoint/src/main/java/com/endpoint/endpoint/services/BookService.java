/*
 * Service layer for managing Book entities.
 * 
 * This class acts as an intermediary between controllers and the BookRepository,
 * providing methods to perform CRUD operations:
 * - Retrieve all books or filter by author, title, or ISDN.
 * - Create new books.
 * - Update existing books by ISDN.
 * - Delete books by ISDN.
 * 
 * Uses Spring's @Service annotation for service component detection and
 * @Autowired to inject the BookRepository dependency.
 * 
 * Note: Update returns null if the book with given ISDN does not exist.
 */

package com.endpoint.endpoint.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.endpoint.endpoint.dto.BookDTO;
import com.endpoint.endpoint.mapper.BookMapper;
import com.endpoint.endpoint.model.Author;
import com.endpoint.endpoint.model.Book;
import com.endpoint.endpoint.repositories.BookRepository;
import com.endpoint.endpoint.repositories.AuthorRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookService() {
    };

    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();

        return books.stream().map(b -> BookMapper.toDTO(b))
                .collect(Collectors.toList());
    }

    public Optional<List<BookDTO>> getBookByAuthor(Author author) {
        Optional<List<Book>> listOfBooks = bookRepository.findByAuthor(author);

        return listOfBooks.map(books -> books.stream().map(b -> BookMapper.toDTO(b)).collect(Collectors.toList()));
    }

    public Optional<BookDTO> getByTitle(String title) {

        Optional<Book> book = bookRepository.findByTitle(title);
        return BookMapper.OptionaltoDTO(book);
    }

    public Optional<BookDTO> getByIsdn(String isdn) {
        Optional<Book> book = bookRepository.findByIsdn(isdn);
        return BookMapper.OptionaltoDTO(book);
    }

    public BookDTO createBook(BookDTO bookDTO) {
        Book book = BookMapper.toEntity(bookDTO);

        // check if author exists
        if (book.getAuthor() == null || book.getAuthor().getId() == null) {
            throw new IllegalArgumentException("Author must be provided with a valid ID.");
        }

        // if author exists then link it with the new book
        Author author = authorRepository.findById(book.getAuthor().getId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Author not found with id: " + book.getAuthor().getId()));
        book.setAuthor(author);
        Book savedBook = bookRepository.save(book);

        return BookMapper.toDTO(savedBook);
    }

    public BookDTO updateBook(String isdn, String title, String content, Author author, Date releaseDate, Book book) {
        if (bookRepository.existsById(isdn)) {
            book.setIsdn(isdn);
            book.setTitle(title);
            book.setContent(content);
            book.setAuthor(author);
            book.setReleaseDate(releaseDate);
            Book savedBook = bookRepository.save(book);
            return BookMapper.toDTO(savedBook);
        }
        return null;
    }

    public boolean deleteBook(String isdn) {
        if (bookRepository.existsById(isdn)) {
            bookRepository.deleteById(isdn);
            return true;
        }
        return false;
    }

}