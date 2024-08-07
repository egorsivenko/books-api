package com.example.books.api.service.impl;

import com.example.books.api.domain.entity.AuthorEntity;
import com.example.books.api.repository.AuthorRepository;
import com.example.books.api.service.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }

    @Override
    public Page<AuthorEntity> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    @Override
    public Optional<AuthorEntity> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public Boolean existsById(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public AuthorEntity partialUpdate(Long id, AuthorEntity authorEntity) {
        return authorRepository.findById(id).map(existingAuthor -> {
            Optional.ofNullable(authorEntity.getName()).ifPresent(existingAuthor::setName);
            Optional.ofNullable(authorEntity.getAge()).ifPresent(existingAuthor::setAge);
            return authorRepository.save(existingAuthor);
        }).orElseThrow(() -> new RuntimeException("Author does not exist"));
    }

    @Override
    public void deleteById(Long id) {
        authorRepository.deleteById(id);
    }
}
