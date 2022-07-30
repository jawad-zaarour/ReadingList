package com.learning.readinglist.controller;

import com.learning.readinglist.dto.BookDTO;
import com.learning.readinglist.dto.BookMapper;
import com.learning.readinglist.entity.Book;
import com.learning.readinglist.service.BookService;
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


    //TODO the rest api's should return ResponseEntity<>
    //in case the response is empty just return http 204 NO_CONTENT, otherwise 200

    //TODO you should not return the entity in the controller but you should create instead DTO classes

    /**
     * Note: we should not have an API that return all the db data
     * we should have always a filter (by userId, by readerId, by categoryId etc...)
     * Only it's acceptable in case the table data is very limited eg: table containing 10, 20, 50 rows (like the category types, etc...)
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<List<Book>> getBooks() {
        List<Book> lstBooks = bookService.getBooks();

        //if(CollectionUtils.isEmpty(lstBooks)): nice to include apache library to use CollectionUtils
        if (lstBooks == null || lstBooks.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(bookService.getBooks(), HttpStatus.OK);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable("id") Long id) {
        BookDTO book = bookService.getBookById(id);

        if (book == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(book, HttpStatus.OK);
        }
    }

    @GetMapping("/book-by-title/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return bookService.getBookByTitle(title);
    }

    @PostMapping
    public Book saveBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        return bookService.deleteBook(id);
    }

    @PutMapping
    public Book updateBook(@RequestBody Book book) {
        return bookService.updateBook(book);
    }


}
