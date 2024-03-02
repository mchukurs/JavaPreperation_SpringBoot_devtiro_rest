package com.chukurs.database.services;

import com.chukurs.database.domain.entities.AuthorEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface AuthorService {
    AuthorEntity save(AuthorEntity authorEntity);

    List<AuthorEntity> findAll();

    Optional<AuthorEntity> findOne(Long id);

    boolean isExists(Long id);

    AuthorEntity partialUpdate(Long id, AuthorEntity updatedAuthorEntity);
}
