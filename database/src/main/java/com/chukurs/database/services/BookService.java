package com.chukurs.database.services;

import com.chukurs.database.domain.entities.BookEntity;
import org.springframework.stereotype.Component;

@Component
public interface BookService {
    BookEntity createBook(String isbn, BookEntity bookEntity);
}
