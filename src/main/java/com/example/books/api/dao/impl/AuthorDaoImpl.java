package com.example.books.api.dao.impl;

import com.example.books.api.dao.AuthorDao;
import com.example.books.api.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
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

    @Override
    public Optional<Author> findOne(Long id) {
        String sql = "SELECT id, name, age FROM authors WHERE id = ?";
        return jdbcTemplate.query(sql, new AuthorRowMapper(), id).stream().findFirst();
    }

    @Override
    public List<Author> findAll() {
        String sql = "SELECT id, name, age FROM authors";
        return jdbcTemplate.query(sql, new AuthorRowMapper());
    }

    @Override
    public void update(Long id, Author author) {
        String sql = "UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?";
        jdbcTemplate.update(sql, author.getId(), author.getName(), author.getAge(), id);
    }

    public static class AuthorRowMapper implements RowMapper<Author> {

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Author.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .age(rs.getInt("age"))
                    .build();
        }
    }
}
