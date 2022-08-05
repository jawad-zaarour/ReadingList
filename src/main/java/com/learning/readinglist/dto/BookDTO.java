package com.learning.readinglist.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * DTO: Data transfer object
 */
@Data
public class BookDTO {

    @JsonIgnore
    private Long id;

    private String isbn;

    private String title;

    private String author;

    private String description;

    public BookDTO(long id, String isbn, String title, String author, String description) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.description = description;
    }

    public BookDTO() {
    }
}
