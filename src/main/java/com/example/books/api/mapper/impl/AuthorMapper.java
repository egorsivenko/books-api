package com.example.books.api.mapper.impl;

import com.example.books.api.domain.dto.AuthorDto;
import com.example.books.api.domain.entity.AuthorEntity;
import com.example.books.api.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper implements Mapper<AuthorEntity, AuthorDto> {

    private final ModelMapper modelMapper;

    public AuthorMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDto mapTo(AuthorEntity entity) {
        return modelMapper.map(entity, AuthorDto.class);
    }

    @Override
    public AuthorEntity mapFrom(AuthorDto dto) {
        return modelMapper.map(dto, AuthorEntity.class);
    }
}
