// Spring Data JPA will auto-implement this method based on its name.
// Example: List<Book> findByAuthor(String author);
// It maps to: SELECT * FROM book WHERE author = ?;
// No need to write a method body or query manually — just ensure
// 'author' is a field in your Book entity and the method name matches it.

package com.endpoint.endpoint.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.endpoint.endpoint.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, String> {

    Optional<Book> findByTitle(String title);

    Optional<Book> findByIsdn(String isdn);

    Optional<List<Book>> findByAuthor(String author);
}