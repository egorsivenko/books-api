package com.example.books.api.dao.impl;

import com.example.books.api.dao.BookDao;
import com.example.books.api.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;

public class BookDaoImpl implements BookDao {

    private final JdbcTemplate jdbcTemplate;

    public BookDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Book book) {
        String sql = "INSERT INTO books (isbn, title, author_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, book.getIsbn(), book.getTitle(), book.getAuthorId());
    }
}
