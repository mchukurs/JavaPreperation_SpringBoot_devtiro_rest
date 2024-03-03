package com.chukurs.database.services.impl;

import com.chukurs.database.domain.entities.AuthorEntity;
import com.chukurs.database.domain.entities.BookEntity;
import com.chukurs.database.repositories.AuthorRepository;
import com.chukurs.database.repositories.BookRepository;
import com.chukurs.database.services.AuthorService;
import com.chukurs.database.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service //makes it a bean
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookEntity createUpdateBook(String isbn, BookEntity bookEntity) {
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

    @Override
    public Optional<BookEntity> findOne(String isbn) {
        return bookRepository.findById(isbn);
    }

    @Override
    public boolean isExists(String isbn) {
        return bookRepository.existsById(isbn);
    }

    @Override
    public BookEntity partialUpdate(String isbn, BookEntity updatedBookEntity) {
        updatedBookEntity.setIsbn(isbn);

        return bookRepository.findById(isbn).map(existingBook -> {
            Optional.ofNullable(updatedBookEntity.getTitle()).ifPresent(existingBook::setTitle);
            return bookRepository.save(existingBook);
        }).orElseThrow(() -> new RuntimeException("Book does not exist!"));
    }

    @Override
    public void delete(String isbn) {
        bookRepository.deleteById(isbn);
    }

    @Override
    public Page<BookEntity> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }
}
