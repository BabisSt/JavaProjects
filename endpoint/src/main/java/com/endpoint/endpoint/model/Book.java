package com.endpoint.endpoint.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
@Entity
public class Book {

    @Id
    @NotBlank
    @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "ISDN must be either 10 or 13 digits")
    private String isdn;

    @NotBlank
    private String title;

    @NotBlank
    private String author;
    private Date releaseDate;

    @NotBlank
    private String content;

    public Book() {};

    public Book(String isdn, String title, String author, Date releaseDate, String content) {
        this.isdn = isdn;
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.content = content;
    }

    public String getIsdn() {
        return this.isdn;
    }

    public void setIsdn(String isdn) {
        this.isdn = isdn;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getReleaseDate() {
        return this.releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
