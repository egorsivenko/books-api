package com.example.books.api.dao.impl;

import com.example.books.api.TestDataUtil;
import com.example.books.api.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AuthorDaoImplIntegrationTests {

    private final AuthorDaoImpl authorDao;

    @Autowired
    public AuthorDaoImplIntegrationTests(AuthorDaoImpl authorDao) {
        this.authorDao = authorDao;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthorA();

        authorDao.create(author);
        Optional<Author> result = authorDao.findOne(author.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }
}
