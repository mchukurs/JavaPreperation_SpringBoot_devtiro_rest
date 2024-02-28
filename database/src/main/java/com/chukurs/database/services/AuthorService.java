package com.chukurs.database.services;

import com.chukurs.database.domain.entities.AuthorEntity;
import org.springframework.stereotype.Component;

@Component
public interface AuthorService {
    AuthorEntity save(AuthorEntity authorEntity);
}
