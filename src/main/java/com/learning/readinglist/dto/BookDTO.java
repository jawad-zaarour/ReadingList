package com.learning.readinglist.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learning.readinglist.entity.Author;
import com.learning.readinglist.entity.User;
import com.learning.readinglist.util.EnBookType;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class BookDTO {

    @JsonIgnore
    private Long id;

    private String isbn;

    private String title;

    private String description;

    private EnBookType type;

    @JsonIgnore
    private Set<User> users = new HashSet<>();

    @JsonIgnore
    private Author author;


    public BookDTO(Long id, String isbn, String title, String description, EnBookType type) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.description = description;
        this.type = type;
    }

    public BookDTO() {
    }
}
