package com.chukurs.database.controllers;

import com.chukurs.database.domain.dto.AuthorDto;
import com.chukurs.database.domain.entities.AuthorEntity;
import com.chukurs.database.mappers.Mapper;
import com.chukurs.database.services.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorController {
    //CRUD functionality is created here
    //This is considered as the presentation layer
    //Here we DONT expose Entities(objects from SERVICE/PERSISTENCE layer), we map them to DTO (Data Transfer Object)

    //inject SERVICE layer to link PRESENTATION and PERSISTENCE layer
    private AuthorService authorService;
    private Mapper<AuthorEntity, AuthorDto> authorMapper;

    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> authorMapper) {
        this.authorService = authorService;
        this.authorMapper = authorMapper;
    }

    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto author) {
        //as we get AuthorEntity from SERVICE/PRESENTATION, we need to map that to AuthorDto
        //can use modelmapper library for that
        AuthorEntity authorEntity = authorMapper.mapFrom(author);
        AuthorEntity savedAuthorEntity = authorService.save(authorEntity);

        return new ResponseEntity<>(authorMapper.mapTo(savedAuthorEntity), HttpStatus.CREATED);
    }
}
