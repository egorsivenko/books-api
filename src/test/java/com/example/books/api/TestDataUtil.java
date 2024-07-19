package com.example.books.api;

import com.example.books.api.domain.Author;
import com.example.books.api.domain.Book;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestDataUtil {

    public Author createTestAuthorA() {
        return Author.builder()
                .id(1L)
                .name("Olivia Brooks")
                .age(37)
                .build();
    }

    public Author createTestAuthorB() {
        return Author.builder()
                .id(2L)
                .name("Noah Franklin")
                .age(48)
                .build();
    }

    public Author createTestAuthorC() {
        return Author.builder()
                .id(3L)
                .name("Sophia Turner")
                .age(24)
                .build();
    }

    public Book createTestBookA(Author author) {
        return Book.builder()
                .isbn("978-1-23456-786-9")
                .title("The Enchanted Forest")
                .author(author)
                .build();
    }

    public Book createTestBookB(Author author) {
        return Book.builder()
                .isbn("978-1-23456-788-3")
                .title("Beneath the Shadows")
                .author(author)
                .build();
    }

    public Book createTestBookC(Author author) {
        return Book.builder()
                .isbn("978-1-23456-787-6")
                .title("Whispers in the Wind")
                .author(author)
                .build();
    }
}
