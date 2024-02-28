package com.chukurs.database.services.impl;

import com.chukurs.database.domain.entities.AuthorEntity;
import com.chukurs.database.repositories.AuthorRepository;
import com.chukurs.database.services.AuthorService;
import org.springframework.stereotype.Service;

@Service //makes it a bean
public class AuthorServiceImpl implements AuthorService {
    private AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorEntity save(AuthorEntity authorEntity) {
        return authorRepository.save(authorEntity);
    }
}
