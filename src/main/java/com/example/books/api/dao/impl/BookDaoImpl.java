package com.example.books.api.dao.impl;

import com.example.books.api.dao.BookDao;
import com.example.books.api.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Component
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

    @Override
    public Optional<Book> findOne(String isbn) {
        String sql = "SELECT isbn, title, author_id FROM books WHERE isbn = ?";
        return jdbcTemplate.query(sql, new BookRowMapper(), isbn).stream().findFirst();
    }

    public static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Book.builder()
                    .isbn(rs.getString("isbn"))
                    .title(rs.getString("title"))
                    .authorId(rs.getLong("author_id"))
                    .build();
        }
    }
}
