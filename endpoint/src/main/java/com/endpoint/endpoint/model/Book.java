package com.endpoint.endpoint.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Book {

    @Id
    private Long isdn;
    private String title;
    private String author;
    private Date releaseDate;
    private String content;

    public Book(){};

    public Book (Long isdn , String title, String author, Date releaseDate, String content){
        this.isdn = isdn;
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.content = content;
    }
    
    public Long getIsdn(){
        return this.isdn;
    }

    public void setIsdn(Long isdn){
        this.isdn = isdn;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getAuthor(){
        return this.author;
    }

    public void setAuthor(String author){
        this.author = author;
    }

    public Date getReleaseDate(){
        return this.releaseDate;
    }

    public void setReleaseDate(Date releaseDate){
        this.releaseDate = releaseDate;
    }

    public String getContent(){
        return this.content;
    }

    public void setContent(String content){
        this.content = content;
    }
}
