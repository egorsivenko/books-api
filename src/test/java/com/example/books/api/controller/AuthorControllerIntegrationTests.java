package com.example.books.api.controller;

import com.example.books.api.TestDataUtil;
import com.example.books.api.domain.entity.AuthorEntity;
import com.example.books.api.service.AuthorService;
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
public class AuthorControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final AuthorService authorService;

    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc, ObjectMapper objectMapper, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.authorService = authorService;
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturns201Created() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        author.setId(null);
        String authorJson = objectMapper.writeValueAsString(author);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        author.setId(null);
        String authorJson = objectMapper.writeValueAsString(author);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(author.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(author.getAge())
        );
    }

    @Test
    public void testThatListAuthorsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListAuthorsReturnsListOfAuthors() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        authorService.save(author);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].name").value(author.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].age").value(author.getAge())
        );
    }

    @Test
    public void testThatFindExistingAuthorReturnsHttpStatus200() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        authorService.save(author);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + author.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFindNonExistingAuthorReturns404NotFound() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/100")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFindExistingAuthorReturnsAuthor() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthorA();
        authorService.save(author);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + author.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(author.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(author.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(author.getAge())
        );
    }

    @Test
    public void testThatFullUpdateExistingAuthorReturnsHttpStatus200() throws Exception {
        AuthorEntity testAuthorB = TestDataUtil.createTestAuthorB();
        AuthorEntity savedAuthor = authorService.save(testAuthorB);

        AuthorEntity testAuthorC = TestDataUtil.createTestAuthorC();
        String authorJson = objectMapper.writeValueAsString(testAuthorC);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateNonExistingAuthorReturns404NotFound() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthorB();
        String authorJson = objectMapper.writeValueAsString(author);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdateUpdatesExistingAuthor() throws Exception {
        AuthorEntity testAuthorB = TestDataUtil.createTestAuthorB();
        AuthorEntity savedAuthor = authorService.save(testAuthorB);

        AuthorEntity testAuthorC = TestDataUtil.createTestAuthorC();
        testAuthorC.setId(savedAuthor.getId());
        String authorJson = objectMapper.writeValueAsString(testAuthorC);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.content().json(authorJson)
        );
    }

    @Test
    public void testThatPartialUpdateExistingAuthorReturnsHttpStatus200() throws Exception {
        AuthorEntity testAuthorB = TestDataUtil.createTestAuthorB();
        AuthorEntity savedAuthor = authorService.save(testAuthorB);

        AuthorEntity testAuthorC = TestDataUtil.createTestAuthorC();
        String authorJson = objectMapper.writeValueAsString(testAuthorC);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateNonExistingAuthorReturns404NotFound() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthorB();
        String authorJson = objectMapper.writeValueAsString(author);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatPartialUpdateUpdatesExistingAuthor() throws Exception {
        AuthorEntity testAuthorB = TestDataUtil.createTestAuthorB();
        AuthorEntity savedAuthor = authorService.save(testAuthorB);

        AuthorEntity testAuthorC = TestDataUtil.createTestAuthorC();
        testAuthorC.setId(savedAuthor.getId());
        testAuthorC.setAge(savedAuthor.getAge());
        String authorJson = objectMapper.writeValueAsString(testAuthorC);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.content().json(authorJson)
        );
    }

    @Test
    public void testThatDeleteExistingAuthorReturns204NoContent() throws Exception {
        AuthorEntity author = TestDataUtil.createTestAuthorC();
        AuthorEntity savedAuthor = authorService.save(author);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/" + savedAuthor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

    @Test
    public void testThatDeleteNonExistingAuthorReturns204NoContent() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/100")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }
}
