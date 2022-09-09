package com.learning.readinglist.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class UserDTO {
    @JsonIgnore
    private Long id;

    @JsonIgnore
    private Set<BookDTO> books = new HashSet<>();

    private String userName;

    @JsonIgnore
    private String password;

    private String email;

    private boolean active;

    @JsonIgnore
    private String roles;


}
