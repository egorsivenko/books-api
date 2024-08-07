package com.example.books.api;

import com.example.books.api.domain.entity.AuthorEntity;
import com.example.books.api.domain.entity.BookEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TestDataUtil {

    public AuthorEntity createTestAuthorA() {
        return AuthorEntity.builder()
                .id(1L)
                .name("Olivia Brooks")
                .age(37)
                .build();
    }

    public AuthorEntity createTestAuthorB() {
        return AuthorEntity.builder()
                .id(2L)
                .name("Noah Franklin")
                .age(48)
                .build();
    }

    public AuthorEntity createTestAuthorC() {
        return AuthorEntity.builder()
                .id(3L)
                .name("Sophia Turner")
                .age(24)
                .build();
    }

    public BookEntity createTestBookA(AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("978-1-23456-786-9")
                .title("The Enchanted Forest")
                .authorEntity(authorEntity)
                .build();
    }

    public BookEntity createTestBookB(AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("978-1-23456-788-3")
                .title("Beneath the Shadows")
                .authorEntity(authorEntity)
                .build();
    }

    public BookEntity createTestBookC(AuthorEntity authorEntity) {
        return BookEntity.builder()
                .isbn("978-1-23456-787-6")
                .title("Whispers in the Wind")
                .authorEntity(authorEntity)
                .build();
    }
}
