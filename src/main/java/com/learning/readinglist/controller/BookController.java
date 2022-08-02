package com.learning.readinglist.controller;

import com.learning.readinglist.dto.BookDTO;
import com.learning.readinglist.entity.Book;
import com.learning.readinglist.service.BookService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private ModelMapper modelMapper;

    //TODO I used the modelmapper for DTO ( it is old ?)

    @GetMapping("{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") Long id) {
        Book book = bookService.getBookById(id);
        // convert entity to DTO
        BookDTO response = modelMapper.map(book, BookDTO.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/book-by-title/{title}")
    public ResponseEntity<BookDTO> getBookByTitle(@PathVariable String title) {

        Book book = bookService.getBookByTitle(title);
        BookDTO response = modelMapper.map(book, BookDTO.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BookDTO> saveBook(@RequestBody BookDTO bookDTO) {

        // convert DTO to entity
        Book bookRequest = modelMapper.map(bookDTO, Book.class);

        // convert entity to DTO
        BookDTO bookResponse = modelMapper.map(bookService.saveBook(bookRequest), BookDTO.class);

        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);

    }

    @PutMapping
    public ResponseEntity<BookDTO> updateBook(@RequestBody BookDTO bookDTO) {
        Book bookRequest = modelMapper.map(bookDTO, Book.class);
        BookDTO bookResponse = modelMapper.map(bookService.updateBook(bookRequest), BookDTO.class);
        return new ResponseEntity<>(bookResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        return bookService.deleteBook(id);
    }


}
