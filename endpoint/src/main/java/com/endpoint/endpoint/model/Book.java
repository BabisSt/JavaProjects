package com.endpoint.endpoint.model;

import java.util.Date;

import org.springframework.core.style.ToStringCreator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @NotBlank
    @Pattern(regexp = "^(\\d{10}|\\d{13})$", message = "ISDN must be either 10 or 13 digits")
    @Column(name = "isdn")
    private String isdn;

    @NotBlank
    @Column(name = "title")
    private String title;

    @NotBlank
    @ManyToOne // Each Book Has One Author
    @JoinColumn(name = "author_id")
    private Author author;

    @NotBlank
    @Column(name = "release_date")
    private Date releaseDate;

    @NotBlank
    @Column(name = "content")
    private String content;

    public Book() {
    };

    public Book(String isdn, String title, Author author, Date releaseDate, String content) {
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

    public Author getAuthor() {
        return this.author;
    }

    public void setAuthor(Author author) {
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

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("isdn", this.getIsdn())
                .append("title", this.getTitle())
                .append("author", this.getAuthor())
                .append("releadeDate", this.getReleaseDate())
                .append("content", this.getContent()).toString();
    }
}
