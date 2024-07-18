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

    public Book createTestBookA() {
        return Book.builder()
                .isbn("978-1-23456-786-9")
                .title("The Enchanted Forest")
                .authorId(1L)
                .build();
    }

    public Book createTestBookB() {
        return Book.builder()
                .isbn("978-1-23456-788-3")
                .title("Beneath the Shadows")
                .authorId(2L)
                .build();
    }

    public Book createTestBookC() {
        return Book.builder()
                .isbn("978-1-23456-787-6")
                .title("Whispers in the Wind")
                .authorId(3L)
                .build();
    }
}
