package com.chukurs.database.repositories;

import com.chukurs.database.domain.entities.BookEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
//this now a bean than can be injected wherever needed
public interface BookRepository extends CrudRepository<BookEntity, String> {

}
