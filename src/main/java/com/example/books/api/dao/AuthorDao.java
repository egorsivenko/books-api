package com.example.books.api.dao;

import com.example.books.api.domain.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {

    void create(Author author);

    Optional<Author> findOne(Long id);

    List<Author> findAll();
}
