package com.example.books.api.service;

import com.example.books.api.domain.entity.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookService {

    BookEntity createBook(String isbn, BookEntity book);

    List<BookEntity> findAll();

    Optional<BookEntity> findByIsbn(String isbn);
}
