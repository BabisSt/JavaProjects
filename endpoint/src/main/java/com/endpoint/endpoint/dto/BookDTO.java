/**
 * BookDTO (Data Transfer Object) is used to expose a simplified version of the Book entity
 * through the API. It helps decouple the internal data model from the external representation,
 * improving security and flexibility.
 *
 * This DTO only includes selected fields to be sent to or received from clients,
 * such as during create/update operations or API responses.
 */

package com.endpoint.endpoint.dto;

public class BookDTO {
    private Long isdn;
    private String title;
    private String author;

    // Constructors
    public BookDTO() {
    }

    public BookDTO(Long isdn, String title, String author) {
        this.isdn = isdn;
        this.title = title;
        this.author = author;
    }

    // Getters and setters
    public Long getIsdn() {
        return this.isdn;
    }

    public void setIsdn(Long isdn) {
        this.isdn = isdn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
