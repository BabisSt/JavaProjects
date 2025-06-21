package com.endpoint.endpoint.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "author")
public class Author extends Person {

    @Column(name = "about_section")
    private String aboutSection;

    public Author() {
    };

    public String getAboutSection() {
        return this.aboutSection;
    }

    public void setAboutSection(String aboutSection) {
        this.aboutSection = aboutSection;
    }

}
