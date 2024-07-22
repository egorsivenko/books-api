package com.example.books.api.service;

import com.example.books.api.domain.entity.AuthorEntity;

import java.util.List;
import java.util.Optional;

public interface AuthorService {

    AuthorEntity save(AuthorEntity authorEntity);

    List<AuthorEntity> findAll();

    Optional<AuthorEntity> findById(Long id);

    Boolean existsById(Long id);

    AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity);
}
