package com.learning.readinglist.controller;

import com.learning.readinglist.dto.BookDTO;
import com.learning.readinglist.entity.Book;
import com.learning.readinglist.service.BookService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookDTO>> getBooks(){
        List<BookDTO> bookResponse = bookService.getBooks();

        if(CollectionUtils.isEmpty(bookResponse)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(bookResponse, HttpStatus.OK);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") Long id) {
        BookDTO bookResponse = bookService.getBookById(id);

        if (bookResponse == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(bookResponse, HttpStatus.OK);
        }
    }

    @GetMapping("/book-by-title/{title}")
    public ResponseEntity<BookDTO> getBookByTitle(@PathVariable String title) {
        BookDTO bookResponse = bookService.getBookByTitle(title);

        if (bookResponse == null) {
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

    /**
     * TODO: this method should return as well ResponseEntity with http code = 204 -> ResponseEntity<>(HttpStatus.NO_CONTENT)
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        return bookService.deleteBook(id);
    }

}
