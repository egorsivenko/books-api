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

    public Book createTestBook() {
        return Book.builder()
                .isbn("978-1-23456-786-9")
                .title("The Enchanted Forest")
                .authorId(1L)
                .build();
    }
}
