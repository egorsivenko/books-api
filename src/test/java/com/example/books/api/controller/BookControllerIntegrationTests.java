package com.example.books.api.controller;

import com.example.books.api.TestDataUtil;
import com.example.books.api.domain.entity.BookEntity;
import com.example.books.api.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final BookService bookService;

    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, BookService bookService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.bookService = bookService;
    }

    @Test
    public void testThatCreateBookSuccessfullyReturns201Created() throws Exception {
        BookEntity book = TestDataUtil.createTestBookA(null);
        String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnsSavedBook() throws Exception {
        BookEntity book = TestDataUtil.createTestBookA(null);
        String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").doesNotExist()
        );
    }

    @Test
    public void testThatListBooksReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListBooksReturnsListOfBooks() throws Exception {
        BookEntity book = TestDataUtil.createTestBookA(null);
        bookService.save(book.getIsbn(), book);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].isbn").value(book.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].title").value(book.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].author").doesNotExist()
        );
    }

    @Test
    public void testThatFindExistingBookReturnsHttpStatus200() throws Exception {
        BookEntity book = TestDataUtil.createTestBookA(null);
        bookService.save(book.getIsbn(), book);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFindNonExistingBookReturns404NotFound() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/978-1-23456-786-9")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFindExistingBookReturnsBook() throws Exception {
        BookEntity book = TestDataUtil.createTestBookA(null);
        bookService.save(book.getIsbn(), book);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(book.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(book.getTitle())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.author").doesNotExist()
        );
    }

    @Test
    public void testThatFullUpdateExistingBookReturnsHttpStatus200() throws Exception {
        BookEntity testBookB = TestDataUtil.createTestBookB(null);
        BookEntity savedBook = bookService.save(testBookB.getIsbn(), testBookB);

        BookEntity testBookC = TestDataUtil.createTestBookC(null);
        String bookJson = objectMapper.writeValueAsString(testBookC);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateNonExistingBookReturns201Created() throws Exception {
        BookEntity book = TestDataUtil.createTestBookB(null);
        String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/978-1-23456-786-9")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatFullUpdateUpdatesExistingBook() throws Exception {
        BookEntity testBookB = TestDataUtil.createTestBookB(null);
        BookEntity savedBook = bookService.save(testBookB.getIsbn(), testBookB);

        BookEntity testBookC = TestDataUtil.createTestBookC(null);
        String bookJson = objectMapper.writeValueAsString(testBookC);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(savedBook.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBookC.getTitle())
        );
    }

    @Test
    public void testThatPartialUpdateExistingBookReturnsHttpStatus200() throws Exception {
        BookEntity testBookB = TestDataUtil.createTestBookB(null);
        BookEntity savedBook = bookService.save(testBookB.getIsbn(), testBookB);

        BookEntity testBookC = TestDataUtil.createTestBookC(null);
        String bookJson = objectMapper.writeValueAsString(testBookC);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPatchUpdateNonExistingBookReturns404NotFound() throws Exception {
        BookEntity book = TestDataUtil.createTestBookB(null);
        String bookJson = objectMapper.writeValueAsString(book);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/978-1-23456-786-9")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatPartialUpdateUpdatesExistingBook() throws Exception {
        BookEntity testBookB = TestDataUtil.createTestBookB(null);
        BookEntity savedBook = bookService.save(testBookB.getIsbn(), testBookB);

        BookEntity testBookC = TestDataUtil.createTestBookC(null);
        String bookJson = objectMapper.writeValueAsString(testBookC);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + savedBook.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(savedBook.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBookC.getTitle())
        );
    }

    @Test
    public void testThatDeleteExistingBookReturns204NoContent() throws Exception {
        BookEntity book = TestDataUtil.createTestBookC(null);
        bookService.save(book.getIsbn(), book);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/" + book.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeleteNonExistingBookReturns204NoContent() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/978-1-23456-786-9")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}
