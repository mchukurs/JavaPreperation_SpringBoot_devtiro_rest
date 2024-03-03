package com.chukurs.database.controllers;

import com.chukurs.database.TestDataUtil;
import com.chukurs.database.domain.dto.AuthorDto;
import com.chukurs.database.domain.entities.AuthorEntity;
import com.chukurs.database.services.AuthorService;
import com.fasterxml.jackson.core.JsonProcessingException;
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

import javax.print.attribute.standard.Media;


//this is running application test--> integration test
//creates an instance of mock MVC for us, and places inside application context

//mock mvc is super powerful way of testing Controllers
@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc

public class AuthorControllerIntegrationTests {

    private AuthorService authorService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;


    @Autowired
    public AuthorControllerIntegrationTests(MockMvc mockMvc, AuthorService authorService) {
        this.mockMvc = mockMvc;
        this.authorService = authorService;
        this.objectMapper = new ObjectMapper();

    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsHttp201Created() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorEntityA();
        testAuthorA.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthorA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.status().isCreated()
        );
    }

    @Test
    public void testThatCreateAuthorSuccessfullyReturnsSavedAuthor() throws Exception {
        AuthorDto testAuthorA = TestDataUtil.createTestAuthorDtoA();
        testAuthorA.setId(null);
        String authorJson = objectMapper.writeValueAsString(testAuthorA);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Abigail Rose")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.age").value(80)
        );
    }

    @Test
    public void testThatListAuthorsReturnsHttpStatus200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatListAuthorsReturnsListOfAuthors() throws Exception {
        authorService.save(TestDataUtil.createTestAuthorEntityA());
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].name").isString()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$[0].age").isNumber()
        );
    }

    @Test
    public void testThatGetAuthorReturns200WhenExists() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorEntityA();
        authorService.save(testAuthorA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + 1)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatGetAuthorReturns404WhenDoesNotExist() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + 1)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatGetAuthorReturnsAuthorWhenExists() throws Exception {
        AuthorEntity testAuthorA = TestDataUtil.createTestAuthorEntityA();
        authorService.save(testAuthorA);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/authors/" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(1L)
        );
    }

    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus404WhenNoAuthorExists() throws Exception {
        AuthorDto testAuthorDto = TestDataUtil.createTestAuthorDtoA();
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + 99)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isNotFound()
        );
    }

    @Test
    public void testThatFullUpdateAuthorReturnsHttpStatus200WhenAuthorExists() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
        authorService.save(testAuthorEntityA);

        AuthorDto testAuthorDto = TestDataUtil.createTestAuthorDtoA();
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + testAuthorDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatFullUpdateUpdatesExistingAuthor() throws Exception {
        AuthorEntity testAuthorEntityA = TestDataUtil.createTestAuthorEntityA();
        AuthorEntity savedAuthor = authorService.save(testAuthorEntityA);

        AuthorDto testAuthorDto = TestDataUtil.createTestAuthorDtoA();
        testAuthorDto.setName("Tester Name");
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDto);
        mockMvc.perform(
                MockMvcRequestBuilders.put("/authors/" + testAuthorDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Tester Name")
        );
    }

    @Test
    public void testThatPartialUpdateAuthorReturnsHttpStatus200WhenAuthorExists() throws Exception {
        authorService.save(TestDataUtil.createTestAuthorEntityA());

        AuthorDto testAuthorDto = TestDataUtil.createTestAuthorDtoA();
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/" + testAuthorDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    public void testThatPartialUpdateAuthorReturnsAuthorWhenAuthorExists() throws Exception {
        authorService.save(TestDataUtil.createTestAuthorEntityA());

        AuthorDto testAuthorDto = TestDataUtil.createTestAuthorDtoA();
        testAuthorDto.setName("Testing Partial");
        String authorDtoJson = objectMapper.writeValueAsString(testAuthorDto);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/authors/" + testAuthorDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(authorDtoJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("Testing Partial")
        ).andExpect(MockMvcResultMatchers.jsonPath("$.age").value(testAuthorDto.getAge()));
    }
    @Test
    public void testThatDeleteAuthorReturns204() throws Exception {
        authorService.save(TestDataUtil.createTestAuthorEntityA());
        AuthorDto testAuthorDto = TestDataUtil.createTestAuthorDtoA();
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/authors/" + testAuthorDto.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.status().isNoContent()
        );
    }

}
