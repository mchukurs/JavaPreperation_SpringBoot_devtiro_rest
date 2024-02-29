package com.chukurs.database.services.impl;

import com.chukurs.database.domain.entities.AuthorEntity;
import com.chukurs.database.domain.entities.BookEntity;
import com.chukurs.database.repositories.AuthorRepository;
import com.chukurs.database.repositories.BookRepository;
import com.chukurs.database.services.AuthorService;
import com.chukurs.database.services.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service //makes it a bean
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createBook(String isbn, BookEntity bookEntity) {
        bookEntity.setIsbn(isbn);
        return bookRepository.save(bookEntity);
    }

    @Override
    public List<BookEntity> findAll() {
        return StreamSupport.stream(bookRepository
                                .findAll()
                                .spliterator(),
                        false)
                .collect(Collectors.toList());
    }
}
