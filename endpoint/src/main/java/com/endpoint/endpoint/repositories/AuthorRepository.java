package com.endpoint.endpoint.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.endpoint.endpoint.model.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

}