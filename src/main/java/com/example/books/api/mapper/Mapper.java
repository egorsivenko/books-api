package com.example.books.api.mapper;

public interface Mapper<E, D> {

    D mapTo(E entity);

    E mapFrom(D dto);
}
