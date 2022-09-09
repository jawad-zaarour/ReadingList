package com.learning.readinglist.controller;

import com.learning.readinglist.dto.AuthorDTO;
import com.learning.readinglist.dto.AuthorStatDTO;
import com.learning.readinglist.entity.Author;
import com.learning.readinglist.service.AuthorService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAuthors() {
        List<AuthorDTO> authorResponse = authorService.getAuthors();
        if (CollectionUtils.isEmpty(authorResponse)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(authorResponse, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> saveAuthor(@RequestBody Author author) {
        AuthorDTO authorResponse = authorService.saveAuthor(author);
        return new ResponseEntity<>(authorResponse, HttpStatus.CREATED);
    }

    @GetMapping("/statistics")
    public ResponseEntity<List<AuthorStatDTO>> getStatByAuthorBook(){
        return new ResponseEntity<>(authorService.getStatByAuthorBook(), HttpStatus.OK);
    }
}
