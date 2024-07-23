package com.example.books.api.service;

import com.example.books.api.domain.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface BookService {

    BookEntity save(String isbn, BookEntity book);

    Page<BookEntity> findAll(Pageable pageable);

    Optional<BookEntity> findByIsbn(String isbn);

    Boolean existsByIsbn(String isbn);

    BookEntity partialUpdate(String isbn, BookEntity bookEntity);

    void deleteByIsbn(String isbn);
}
