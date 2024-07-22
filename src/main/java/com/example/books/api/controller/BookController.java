package com.example.books.api.controller;

import com.example.books.api.domain.dto.BookDto;
import com.example.books.api.domain.entity.BookEntity;
import com.example.books.api.mapper.Mapper;
import com.example.books.api.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class BookController {

    private final BookService bookService;
    private final Mapper<BookEntity, BookDto> bookMapper;

    public BookController(BookService bookService, Mapper<BookEntity, BookDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable String isbn, @RequestBody BookDto bookDto) {
        boolean bookExisted = bookService.existsByIsbn(isbn);
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBook = bookService.save(isbn, bookEntity);

        HttpStatus httpStatus = bookExisted ? HttpStatus.OK : HttpStatus.CREATED;
        return new ResponseEntity<>(bookMapper.mapTo(savedBook), httpStatus);
    }

    @PatchMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(@PathVariable String isbn, @RequestBody BookDto bookDto) {
        if (!bookService.existsByIsbn(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        BookEntity bookEntity = bookMapper.mapFrom(bookDto);
        BookEntity updatedBook = bookService.partialUpdate(isbn, bookEntity);
        return new ResponseEntity<>(bookMapper.mapTo(updatedBook), HttpStatus.OK);
    }

    @GetMapping(path = "/books")
    public List<BookDto> listBooks() {
        List<BookEntity> bookEntities = bookService.findAll();
        return bookEntities.stream()
                .map(bookMapper::mapTo)
                .toList();
    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable String isbn) {
        Optional<BookEntity> optionalBook = bookService.findByIsbn(isbn);
        return optionalBook.map(book -> new ResponseEntity<>(bookMapper.mapTo(book), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
