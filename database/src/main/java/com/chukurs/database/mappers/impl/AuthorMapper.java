package com.chukurs.database.mappers.impl;

import com.chukurs.database.domain.dto.AuthorDto;
import com.chukurs.database.domain.entities.AuthorEntity;
import com.chukurs.database.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component //makes this a BEAN
public class AuthorMapper implements Mapper<AuthorEntity, AuthorDto> {
    //creating an object of the imported library ModelMapper
    private ModelMapper modelMapper;


    public AuthorMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AuthorDto mapTo(AuthorEntity authorEntity) {
        return modelMapper.map(authorEntity, AuthorDto.class);
    }

    @Override
    public AuthorEntity mapFrom(AuthorDto authorDto) {
        return modelMapper.map(authorDto, AuthorEntity.class);
    }
}
