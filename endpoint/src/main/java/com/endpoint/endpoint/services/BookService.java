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
import com.endpoint.endpoint.model.Book;
import com.endpoint.endpoint.repositories.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();

        return books.stream().map(b -> BookMapper.toDTO(b))
                .collect(Collectors.toList());
    }

    public Optional<List<BookDTO>> getBookByAuthor(String author) {
        Optional<List<Book>> listOfBooks = bookRepository.findByAuthor(author);

        return listOfBooks.map(books -> books.stream().map(b -> BookMapper.toDTO(b)).collect(Collectors.toList()));
    }

    public Optional<BookDTO> getByTitle(String title) {

        Optional<Book> book = bookRepository.findByTitle(title);
        return BookMapper.OptionaltoDTO(book);
    }

    public Optional<BookDTO> getByIsdn(Long isdn) {
        Optional<Book> book = bookRepository.findByIsdn(isdn);
        return BookMapper.OptionaltoDTO(book);
    }

    public BookDTO createBook(BookDTO bookDTO) {
        Book book = BookMapper.toEntity(bookDTO);
        Book savedBook = bookRepository.save(book);
        return BookMapper.toDTO(savedBook);
    }

    public BookDTO updateBook(Long isdn, String title, String content, String author, Date releaseDate, Book book) {
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

    public boolean deleteBook(Long isdn) {
        if (bookRepository.existsById(isdn)) {
            bookRepository.deleteById(isdn);
            return true;
        }
        return false;
    }

}