package com.example.books.api.dao.impl;

import com.example.books.api.TestDataUtil;
import com.example.books.api.dao.AuthorDao;
import com.example.books.api.domain.Author;
import com.example.books.api.domain.Book;
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
public class BookDaoImplIntegrationTests {

    private final BookDaoImpl bookDao;
    private final AuthorDao authorDao;

    @Autowired
    public BookDaoImplIntegrationTests(BookDaoImpl bookDao, AuthorDao authorDao) {
        this.bookDao = bookDao;
        this.authorDao = authorDao;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthorB();
        authorDao.create(author);

        Book book = TestDataUtil.createTestBookA();
        book.setAuthorId(author.getId());
        bookDao.create(book);

        Optional<Book> result = bookDao.findOne(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled() {
        Author author = TestDataUtil.createTestAuthorB();
        authorDao.create(author);

        Book bookA = TestDataUtil.createTestBookA();
        bookA.setAuthorId(author.getId());
        bookDao.create(bookA);

        Book bookB = TestDataUtil.createTestBookB();
        bookB.setAuthorId(author.getId());
        bookDao.create(bookB);

        Book bookC = TestDataUtil.createTestBookC();
        bookC.setAuthorId(author.getId());
        bookDao.create(bookC);

        List<Book> result = bookDao.findAll();

        assertThat(result)
                .hasSize(3)
                .containsExactly(bookA, bookB, bookC);
    }

    @Test
    public void testThatBookCanBeUpdated() {
        Author author = TestDataUtil.createTestAuthorB();
        authorDao.create(author);

        Book book = TestDataUtil.createTestBookA();
        book.setAuthorId(author.getId());
        bookDao.create(book);

        book.setTitle("UPDATED");
        bookDao.update(book.getIsbn(), book);

        Optional<Book> result = bookDao.findOne(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }
}
