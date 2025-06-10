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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.endpoint.endpoint.model.Book;
import com.endpoint.endpoint.repositories.BookRepository;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks () {
        return bookRepository.findAll();
    }

    public Optional<List<Book>> getBookByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public Optional<Book> getByTitle(String title){
        return bookRepository.findByTitle(title);
    }

    public Optional<Book> getByIsdn(Long isdn){
        return bookRepository.findByIsdn(isdn);
    }

    public Book createBook (Book book){
        return bookRepository.save(book);
    }

    public Book updateBook (Long isdn, String title, String content, String author, Date releaseDate,Book book){
        if(bookRepository.existsById(isdn)){
            book.setIsdn(isdn);
            book.setTitle(title);
            book.setContent(content);
            book.setAuthor(author);
            book.setReleaseDate(releaseDate);

            return bookRepository.save(book);
        }
        return null;
    }

    public boolean deleteBook(Long isdn){
        if(bookRepository.existsById(isdn)){
            bookRepository.deleteById(isdn);
            return true;
        }
        return false;
    }
    
}