package com.example.books.api.service;

import com.example.books.api.domain.entity.BookEntity;

public interface BookService {

    BookEntity createBook(String isbn, BookEntity book);
}
