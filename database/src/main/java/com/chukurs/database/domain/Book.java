package com.chukurs.database.domain;

import com.chukurs.database.domain.entities.AuthorEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "books")

public class Book {
    @Id
    private String isbn;
    private String title;

    //cascade.all -> author comes with book, if we make change to author, it actually is changed in DB too
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id")
    private AuthorEntity author;

}
