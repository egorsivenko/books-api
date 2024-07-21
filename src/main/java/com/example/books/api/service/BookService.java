package com.example.books.api.service;

import com.example.books.api.domain.entity.BookEntity;

import java.util.List;

public interface BookService {

    BookEntity createBook(String isbn, BookEntity book);

    List<BookEntity> findAll();
}
