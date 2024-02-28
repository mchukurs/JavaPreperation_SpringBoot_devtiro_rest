package com.chukurs.database.domain.dto;

import com.chukurs.database.domain.entities.AuthorEntity;
import jakarta.persistence.JoinColumn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//POJO
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDto {
    private String isbn;
    private String title;
    @JoinColumn(name = "author_id")
    private AuthorDto author;


}
