package com.learning.readinglist.controller;

import com.learning.readinglist.entity.Book;
import com.learning.readinglist.entity.User;
import com.learning.readinglist.service.BookService;
import com.learning.readinglist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public List<Book> getBooks() {
        return bookService.getBooks();
    }

    @GetMapping("{id}")
    public Book getBookById(@PathVariable("id") Long id) {
        return bookService.getBookById(id);
    }

    @GetMapping("/book-by-title/{title}")
    public Book getBookByTitle(@PathVariable String title) {
        return bookService.getBookByTitle(title);
    }

    @PostMapping("/")
    public Book saveBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id) {
        return bookService.deleteBook(id);
    }

    @PutMapping("/")
    public Book updateBook(@RequestBody Book book) {
        return bookService.updateBook(book);
    }


}
