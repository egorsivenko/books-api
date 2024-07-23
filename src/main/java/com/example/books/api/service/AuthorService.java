package com.example.books.api.service;

import com.example.books.api.domain.entity.AuthorEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AuthorService {

    AuthorEntity save(AuthorEntity authorEntity);

    Page<AuthorEntity> findAll(Pageable pageable);

    Optional<AuthorEntity> findById(Long id);

    Boolean existsById(Long id);

    AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity);

    void deleteById(Long id);
}
