package com.chukurs.database.services.impl;

import com.chukurs.database.domain.entities.AuthorEntity;
import com.chukurs.database.domain.entities.BookEntity;
import com.chukurs.database.repositories.AuthorRepository;
import com.chukurs.database.repositories.BookRepository;
import com.chukurs.database.services.AuthorService;
import com.chukurs.database.services.BookService;
import org.springframework.stereotype.Service;

@Service //makes it a bean
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(String isbn,BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }
}
