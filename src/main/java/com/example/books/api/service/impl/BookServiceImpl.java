package com.example.books.api.service.impl;

import com.example.books.api.domain.entity.BookEntity;
import com.example.books.api.repository.BookRepository;
import com.example.books.api.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity save(String isbn, BookEntity book) {
        book.setIsbn(isbn);
        return bookRepository.save(book);
    }

    @Override
    public List<BookEntity> findAll() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false).toList();
    }

    @Override
    public Optional<BookEntity> findByIsbn(String isbn) {
        return bookRepository.findById(isbn);
    }

    @Override
    public Boolean existsByIsbn(String isbn) {
        return bookRepository.existsById(isbn);
    }
}
