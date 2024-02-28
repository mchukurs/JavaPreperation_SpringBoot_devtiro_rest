package com.chukurs.database.mappers.impl;

import com.chukurs.database.domain.dto.AuthorDto;
import com.chukurs.database.domain.dto.BookDto;
import com.chukurs.database.domain.entities.AuthorEntity;
import com.chukurs.database.domain.entities.BookEntity;
import com.chukurs.database.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component //makes this a BEAN
public class BookMapper implements Mapper<BookEntity, BookDto> {
    //creating an object of the imported library ModelMapper
    private ModelMapper modelMapper;


    public BookMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public BookDto mapTo(BookEntity bookEntity) {
        return modelMapper.map(bookEntity, BookDto.class);
    }

    @Override
    public BookEntity mapFrom(BookDto bookDto) {
        return modelMapper.map(bookDto, BookEntity.class);
    }
}
