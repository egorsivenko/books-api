package com.example.books.api.dao.impl;

import com.example.books.api.TestDataUtil;
import com.example.books.api.dao.impl.BookDaoImpl.BookRowMapper;
import com.example.books.api.domain.Book;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl bookDao;

    @Test
    public void testThatCreateBookGeneratesCorrectSql() {
        Book book = TestDataUtil.createTestBookA();

        bookDao.create(book);

        verify(jdbcTemplate).update(
                eq("INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)"),
                eq(book.getIsbn()), eq(book.getTitle()), eq(book.getAuthorId())
        );
    }

    @Test
    public void testThatFindOneBookGeneratesCorrectSql() {
        String isbn = "978-1-23456-786-9";

        bookDao.findOne(isbn);

        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books WHERE isbn = ?"),
                any(BookRowMapper.class),
                eq(isbn)
        );
    }

    @Test
    public void testThatFindAllBooksGeneratesCorrectSql() {
        bookDao.findAll();

        verify(jdbcTemplate).query(
                eq("SELECT isbn, title, author_id FROM books"),
                any(BookRowMapper.class)
        );
    }

    @Test
    public void testThatUpdateBookGeneratesCorrectSql() {
        Book book = TestDataUtil.createTestBookA();
        String isbn = "978-1-23456-787-6";

        bookDao.update(isbn, book);

        verify(jdbcTemplate).update(
                eq("UPDATE books SET isbn = ?, title = ?, author_id = ? WHERE isbn = ?"),
                eq(book.getIsbn()), eq(book.getTitle()), eq(book.getAuthorId()), eq(isbn)
        );
    }

    @Test
    public void testThatDeleteBookGeneratesCorrectSql() {
        String isbn = "978-1-23456-788-3";

        bookDao.delete(isbn);

        verify(jdbcTemplate).update(
                eq("DELETE FROM books WHERE isbn = ?"),
                eq(isbn)
        );
    }
}
