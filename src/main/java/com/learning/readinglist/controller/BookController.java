package com.learning.readinglist.controller;

import com.learning.readinglist.dto.BookDTO;
import com.learning.readinglist.entity.Book;
import com.learning.readinglist.service.BookService;
import com.learning.readinglist.util.EnBookType;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO>> getBooks() {
        List<BookDTO> bookResponse = bookService.getBooksDTO();
        if (CollectionUtils.isEmpty(bookResponse)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(bookResponse, HttpStatus.OK);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<BookDTO> getBookDTOById(@PathVariable("id") Long id) {
        BookDTO bookResponse = bookService.getBookDTOById(id);

        if (bookResponse == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(bookResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/authors/{name}")
    public ResponseEntity<List<BookDTO>> getBooksByAuthor(@PathVariable("name") String name) {
        List<BookDTO> bookResponse = bookService.getBooksDTOByAuthorName(name);

        if (bookResponse == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(bookResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<BookDTO> getBookByTitle(@PathVariable String title) {
        BookDTO bookResponse = bookService.getBookDTOByTitle(title);

        if (bookResponse == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(bookResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<BookDTO>> findBookByType(@PathVariable("type") EnBookType type) {

        List<BookDTO> bookResponse = bookService.getBooksDTOByType(type);
        if (CollectionUtils.isEmpty(bookResponse)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(bookResponse, HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<BookDTO> saveBook(@RequestBody Book book) {
        BookDTO bookResponse = bookService.saveBook(book);
        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<BookDTO> updateBook(@RequestBody Book book) {
        BookDTO bookResponse = bookService.updateBook(book);
        return new ResponseEntity<>(bookResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        Boolean isRemoved = bookService.deleteBook(id);
        if (!isRemoved) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/statistics")
    public ResponseEntity<Map<EnBookType, Long>> getBooksStat(){
        return new ResponseEntity<>(bookService.getBooksStat(), HttpStatus.OK);
    }
}
