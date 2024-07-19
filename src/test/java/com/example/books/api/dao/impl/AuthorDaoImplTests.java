package com.example.books.api.dao.impl;

import com.example.books.api.TestDataUtil;
import com.example.books.api.dao.impl.AuthorDaoImpl.AuthorRowMapper;
import com.example.books.api.domain.Author;
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
public class AuthorDaoImplTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl authorDao;

    @Test
    public void testThatCreateAuthorGeneratesCorrectSql() {
        Author author = TestDataUtil.createTestAuthorA();

        authorDao.create(author);

        verify(jdbcTemplate).update(
                eq("INSERT INTO authors (id, name, age) VALUES (?, ?, ?)"),
                eq(author.getId()), eq(author.getName()), eq(author.getAge())
        );
    }

    @Test
    public void testThatFindOneAuthorGeneratesCorrectSql() {
        Long id = 1L;

        authorDao.findOne(id);

        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors WHERE id = ?"),
                any(AuthorRowMapper.class),
                eq(id)
        );
    }

    @Test
    public void testThatFindAllAuthorsGeneratesCorrectSql() {
        authorDao.findAll();

        verify(jdbcTemplate).query(
                eq("SELECT id, name, age FROM authors"),
                any(AuthorRowMapper.class)
        );
    }

    @Test
    public void testThatUpdateAuthorGeneratesCorrectSql() {
        Author author = TestDataUtil.createTestAuthorA();
        Long id = 3L;

        authorDao.update(id, author);

        verify(jdbcTemplate).update(
                eq("UPDATE authors SET id = ?, name = ?, age = ? WHERE id = ?"),
                eq(author.getId()), eq(author.getName()), eq(author.getAge()), eq(id)
        );
    }

    @Test
    public void testThatDeleteAuthorGeneratesCorrectSql() {
        Long id = 1L;

        authorDao.delete(id);

        verify(jdbcTemplate).update(
                eq("DELETE FROM authors WHERE id = ?"),
                eq(id)
        );
    }
}
