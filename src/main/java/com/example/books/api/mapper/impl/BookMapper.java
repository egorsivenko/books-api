package com.example.books.api.mapper.impl;

import com.example.books.api.domain.dto.BookDto;
import com.example.books.api.domain.entity.BookEntity;
import com.example.books.api.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements Mapper<BookEntity, BookDto> {

    private final ModelMapper modelMapper;

    public BookMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDto mapTo(BookEntity entity) {
        return modelMapper.map(entity, BookDto.class);
    }

    @Override
    public BookEntity mapFrom(BookDto dto) {
        return modelMapper.map(dto, BookEntity.class);
    }
}
