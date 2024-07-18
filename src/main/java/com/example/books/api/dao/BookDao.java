package com.example.books.api.dao;

import com.example.books.api.domain.Book;

import java.util.Optional;

public interface BookDao {

    void create(Book book);

    Optional<Book> findOne(String isbn);
}
