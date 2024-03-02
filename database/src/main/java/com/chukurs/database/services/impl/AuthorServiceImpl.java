package com.chukurs.database.services.impl;

import com.chukurs.database.domain.entities.AuthorEntity;
import com.chukurs.database.repositories.AuthorRepository;
import com.chukurs.database.services.AuthorService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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

    @Override
    public List<AuthorEntity> findAll() {
        return StreamSupport.stream(authorRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    @Override
    public Optional<AuthorEntity> findOne(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public boolean isExists(Long id) {
        return authorRepository.existsById(id);
    }

    @Override
    public AuthorEntity partialUpdate(Long id, AuthorEntity updatedAuthorEntity) {
        updatedAuthorEntity.setId(id);

        return authorRepository.findById(id).map(existingAuthor -> {
            Optional.ofNullable(updatedAuthorEntity.getName()).ifPresent(existingAuthor::setName);
            Optional.ofNullable(updatedAuthorEntity.getAge()).ifPresent(existingAuthor::setAge);
            return authorRepository.save(existingAuthor);
        }).orElseThrow(() -> new RuntimeException("Author does not exist!"));
    }
}
