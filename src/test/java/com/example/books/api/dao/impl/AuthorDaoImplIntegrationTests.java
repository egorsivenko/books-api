package com.example.books.api.dao.impl;

import com.example.books.api.TestDataUtil;
import com.example.books.api.domain.Author;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
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

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled() {
        Author authorA = TestDataUtil.createTestAuthorA();
        authorDao.create(authorA);

        Author authorB = TestDataUtil.createTestAuthorB();
        authorDao.create(authorB);

        Author authorC = TestDataUtil.createTestAuthorC();
        authorDao.create(authorC);

        List<Author> result = authorDao.findAll();

        assertThat(result)
                .hasSize(3)
                .containsExactly(authorA, authorB, authorC);
    }

    @Test
    public void testThatAuthorCanBeUpdated() {
        Author author = TestDataUtil.createTestAuthorA();
        authorDao.create(author);

        author.setName("UPDATED");
        authorDao.update(author.getId(), author);

        Optional<Author> result = authorDao.findOne(author.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }
}
