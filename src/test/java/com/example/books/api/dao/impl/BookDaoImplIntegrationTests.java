package com.example.books.api.dao.impl;

import com.example.books.api.TestDataUtil;
import com.example.books.api.dao.AuthorDao;
import com.example.books.api.domain.Author;
import com.example.books.api.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
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
        Book book = TestDataUtil.createTestBook();
        book.setAuthorId(author.getId());

        authorDao.create(author);
        bookDao.create(book);
        Optional<Book> result = bookDao.findOne(book.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }
}
