package com.example.books.api.controller;

import com.example.books.api.domain.dto.AuthorDto;
import com.example.books.api.domain.entity.AuthorEntity;
import com.example.books.api.mapper.Mapper;
import com.example.books.api.mapper.impl.AuthorMapper;
import com.example.books.api.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {

    private final AuthorService authorService;
    private final Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, AuthorMapper authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto) {
        AuthorEntity authorEntity = authorMapper.mapFrom(authorDto);
        AuthorEntity savedAuthor = authorService.createAuthor(authorEntity);
        return new ResponseEntity<>(authorMapper.mapTo(savedAuthor), HttpStatus.CREATED);
    }
}