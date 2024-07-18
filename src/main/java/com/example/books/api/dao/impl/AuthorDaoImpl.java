package com.example.books.api.dao.impl;

import com.example.books.api.dao.AuthorDao;
import com.example.books.api.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;

public class AuthorDaoImpl implements AuthorDao {

    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Author author) {
        String sql = "INSERT INTO authors (id, name, age) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, author.getId(), author.getName(), author.getAge());
    }
}
