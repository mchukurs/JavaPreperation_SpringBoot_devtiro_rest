package com.chukurs.database.controllers;

import com.chukurs.database.TestDataUtil;
import com.chukurs.database.domain.dto.AuthorDto;
import com.chukurs.database.domain.dto.BookDto;
import com.chukurs.database.domain.entities.BookEntity;
import com.chukurs.database.services.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


//this is running application test--> integration test
//creates an instance of mock MVC for us, and places inside application context

//mock mvc is super powerful way of testing Controllers
@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc

public class BookControllerIntegrationTests {

    private BookService bookService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;


    @Autowired
    public BookControllerIntegrationTests(MockMvc mockMvc, BookService bookService) {
        this.mockMvc = mockMvc;
        this.bookService = bookService;
        this.objectMapper = new ObjectMapper();

    }

    @Test
    public void testThatCreateBookSuccessfullyReturnsHttp201Created() throws Exception {
        BookDto testBookDtoA = TestDataUtil.createTestBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(testBookDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + testBookDtoA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateBookSuccessfullyReturnsBody() throws Exception {
        BookDto testBookDtoA = TestDataUtil.createTestBookDtoA(null);
        String bookJson = objectMapper.writeValueAsString(testBookDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + testBookDtoA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBookDtoA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBookDtoA.getTitle())
        );
    }

    @Test
    public void testThatListBooksReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListBooksReturnsListOfBooks() throws Exception {
        BookEntity book = TestDataUtil.createTestBookEntityA(null);
        bookService.createUpdateBook(book.getIsbn(), book);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content.[0].isbn").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content.[0].title").isString()
        );
    }

    @Test
    public void testThatGetBookReturns200WhenExists() throws Exception {
        BookEntity testBookA = TestDataUtil.createTestBookEntityA(null);
        bookService.createUpdateBook(testBookA.getIsbn(), testBookA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + testBookA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetBookReturns404WhenDoesNotExist() throws Exception {
        // BookEntity testBookA = TestDataUtil.createTestBookEntityA(null);
        //bookService.createBook(testBookA.getIsbn(), testBookA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + 123)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetBookReturnsBookWhenExists() throws Exception {
        BookEntity testBookA = TestDataUtil.createTestBookEntityA(null);
        bookService.createUpdateBook(testBookA.getIsbn(), testBookA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/books/" + testBookA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBookA.getIsbn())
        );
    }

    @Test
    public void testThatUpdateBookSuccessfullyReturnsHttp200Updated() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestBookEntityA(null);
        bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        BookDto testBookDtoA = TestDataUtil.createTestBookDtoA(null);
        testBookDtoA.setIsbn(testBookEntityA.getIsbn());

        String bookJson = objectMapper.writeValueAsString(testBookDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + testBookDtoA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatUpdateBookReturnsUpdatedBook() throws Exception {
        BookEntity testBookEntityA = TestDataUtil.createTestBookEntityA(null);
        bookService.createUpdateBook(testBookEntityA.getIsbn(), testBookEntityA);

        BookDto testBookDtoA = TestDataUtil.createTestBookDtoA(null);
        testBookDtoA.setIsbn(testBookEntityA.getIsbn());
        testBookDtoA.setTitle("Updated title");
        String bookJson = objectMapper.writeValueAsString(testBookDtoA);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/books/" + testBookDtoA.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.isbn").value(testBookDtoA.getIsbn())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value(testBookDtoA.getTitle())
        );
    }

    @Test
    public void testThatPartialUpdateBookReturnsHttpStatus200WhenBookExists() throws Exception {
        //create book in database
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA(null);
        bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);

        BookDto testBookDto = TestDataUtil.createTestBookDtoA(null);
        testBookDto.setTitle("Testing Partial book Title");
        //convert to json, so can mimic request
        String bookDtoJson = objectMapper.writeValueAsString(testBookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + testBookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateBookReturnsBookWhenBookExists() throws Exception {
        //create book in database
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA(null);
        bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);


        BookDto testBookDto = TestDataUtil.createTestBookDtoA(null);
        testBookDto.setTitle("Testing Partial book Title");
        //convert to json, so can mimic request
        String bookDtoJson = objectMapper.writeValueAsString(testBookDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/books/" + testBookDto.getIsbn())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bookDtoJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.title").value("Testing Partial book Title")
        );
    }

    @Test
    public void testThatDeleteBookReturns204() throws Exception {
        BookEntity bookEntity = TestDataUtil.createTestBookEntityA(null);
        bookService.createUpdateBook(bookEntity.getIsbn(), bookEntity);
        BookDto testBookDto = TestDataUtil.createTestBookDtoA(null);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/books/" + testBookDto.getTitle())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

}
