/**
 * BookMapper handles the conversion between the Book entity and the BookDTO.
 * 
 * This separation allows the internal data model (Book) to evolve independently
 * of the API contract (BookDTO), and helps enforce a clean architecture.
 * 
 * Typical uses:
 * - Convert incoming BookDTOs to Book entities before saving to the database.
 * - Convert Book entities to BookDTOs before sending them in API responses.
 */
package com.endpoint.endpoint.mapper;

import com.endpoint.endpoint.model.Book;

import java.util.Date;
import java.util.Optional;

import com.endpoint.endpoint.dto.BookDTO;

public class BookMapper {

    public static BookDTO toDTO(Book book) {
        if (book == null)
            return null;

        return new BookDTO(book.getIsdn(), book.getTitle(), book.getAuthor());
    }

    public static Optional<BookDTO> OptionaltoDTO(Optional<Book> book) {
        if (book == null || book.isEmpty())
            return null;

        Book b = book.get();
        return Optional.of(new BookDTO(b.getIsdn(), b.getTitle(), b.getAuthor()));
    }

    public static Book toEntity(BookDTO bookDTO) {
        if (bookDTO == null)
            return null;

        Book book = new Book();
        book.setIsdn(bookDTO.getIsdn());
        book.setAuthor(bookDTO.getAuthor());
        book.setTitle(bookDTO.getTitle());

        book.setContent(null);
        book.setReleaseDate(new Date());

        return book;
    }

}
