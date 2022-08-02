package com.learning.readinglist.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

/**
 * DTO: Data transfer object
 */
@Data
public class BookDTO {

    @JsonIgnore
    private String id;

    private String isbn;

    private String title;

    private String author;

    private String description;


}
