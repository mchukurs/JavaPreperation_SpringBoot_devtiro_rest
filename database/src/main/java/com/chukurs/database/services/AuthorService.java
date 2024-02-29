package com.chukurs.database.services;

import com.chukurs.database.domain.entities.AuthorEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AuthorService {
    AuthorEntity save(AuthorEntity authorEntity);

    List<AuthorEntity> findAll();
}
