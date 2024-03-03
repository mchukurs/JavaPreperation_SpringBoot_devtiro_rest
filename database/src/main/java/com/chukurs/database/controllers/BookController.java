package com.chukurs.database.controllers;

import com.chukurs.database.domain.dto.AuthorDto;
import com.chukurs.database.domain.dto.BookDto;
import com.chukurs.database.domain.entities.AuthorEntity;
import com.chukurs.database.domain.entities.BookEntity;
import com.chukurs.database.mappers.Mapper;
import com.chukurs.database.services.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class BookController {
    private BookService bookService;
    private Mapper<BookEntity, BookDto> bookMapper;

    public BookController(BookService bookService, Mapper<BookEntity, BookDto> bookMapper) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
    }

    @PutMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> createUpdateBook(@PathVariable("isbn") String isbn, @RequestBody BookDto book) {
        BookEntity bookEntity = bookMapper.mapFrom(book);
        boolean bookExists = bookService.isExists(isbn);
        BookEntity savedBookEntity = bookService.createUpdateBook(isbn, bookEntity);
        if (bookExists) {
            //update
            HttpStatus bookStatus = HttpStatus.OK;
            return new ResponseEntity<>(bookMapper.mapTo(savedBookEntity), HttpStatus.OK);
        } else {
            //create
            return new ResponseEntity<>(bookMapper.mapTo(savedBookEntity), HttpStatus.CREATED);
        }
    }

    @GetMapping(path = "/books")
    public Page<BookDto> listBooks(Pageable pageable) {
        Page<BookEntity> books = bookService.findAll(pageable);
        return books.map(bookMapper::mapTo);


    }

    @GetMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> getBook(@PathVariable("isbn") String isbn) {
        Optional<BookEntity> foundBook = bookService.findOne(isbn);
        return foundBook.map(bookEntity -> {
                    BookDto bookDto = bookMapper.mapTo(bookEntity);
                    return new ResponseEntity<>(bookDto, HttpStatus.OK);
                })
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PatchMapping(path = "/books/{isbn}")
    public ResponseEntity<BookDto> partialUpdateBook(
            @PathVariable("isbn") String isbn,
            @RequestBody BookDto bookDto) {
        //if there is no book to update, return 404
        if (!bookService.isExists(isbn)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookDto.setIsbn(isbn);
        BookEntity updatedBookEntity = bookMapper.mapFrom(bookDto);
        BookEntity savedBookEntity = bookService.partialUpdate(updatedBookEntity.getIsbn(), updatedBookEntity);
        return new ResponseEntity<>
                (bookMapper.mapTo(savedBookEntity),
                        HttpStatus.OK);
    }

    @DeleteMapping(path = "/books/{isbn}")
    public ResponseEntity deleteBook(
            @PathVariable("isbn") String isbn) {

        bookService.delete(isbn);

        return new ResponseEntity<>
                (HttpStatus.NO_CONTENT);//204 no content

    }
}

