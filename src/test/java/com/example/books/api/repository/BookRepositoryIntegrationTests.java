package com.example.books.api.repository;

import com.example.books.api.TestDataUtil;
import com.example.books.api.domain.entity.AuthorEntity;
import com.example.books.api.domain.entity.BookEntity;
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
public class BookRepositoryIntegrationTests {

    private final BookRepository bookRepository;

    @Autowired
    public BookRepositoryIntegrationTests(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();
        BookEntity bookEntity = TestDataUtil.createTestBookA(authorEntity);
        bookRepository.save(bookEntity);

        Optional<BookEntity> result = bookRepository.findById(bookEntity.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);
    }

    @Test
    public void testThatMultipleBooksCanBeCreatedAndRecalled() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();

        BookEntity bookEntityA = TestDataUtil.createTestBookA(authorEntity);
        BookEntity bookEntityB = TestDataUtil.createTestBookB(authorEntity);
        BookEntity bookEntityC = TestDataUtil.createTestBookC(authorEntity);

        bookRepository.saveAll(List.of(bookEntityA, bookEntityB, bookEntityC));

        Iterable<BookEntity> result = bookRepository.findAll();
        assertThat(result)
                .hasSize(3)
                .containsExactly(bookEntityA, bookEntityB, bookEntityC);
    }

    @Test
    public void testThatBookCanBeUpdated() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();

        BookEntity bookEntity = TestDataUtil.createTestBookA(authorEntity);
        bookRepository.save(bookEntity);

        bookEntity.setTitle("UPDATED");
        bookRepository.save(bookEntity);

        Optional<BookEntity> result = bookRepository.findById(bookEntity.getIsbn());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(bookEntity);
    }

    @Test
    public void testThatBookCanBeDeleted() {
        AuthorEntity authorEntity = TestDataUtil.createTestAuthorA();

        BookEntity bookEntity = TestDataUtil.createTestBookA(authorEntity);
        bookRepository.save(bookEntity);

        bookRepository.deleteById(bookEntity.getIsbn());

        Optional<BookEntity> result = bookRepository.findById(bookEntity.getIsbn());
        assertThat(result).isNotPresent();
    }
}
