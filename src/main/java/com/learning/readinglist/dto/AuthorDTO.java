package com.learning.readinglist.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.learning.readinglist.entity.Book;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class AuthorDTO {

    @JsonIgnore
    private Long id;

    private String name;

    @JsonIgnore
    private Set<BookDTO> bookDTOS = new HashSet<>();


}
