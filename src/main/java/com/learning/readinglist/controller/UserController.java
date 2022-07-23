package com.learning.readinglist.controller;

import com.learning.readinglist.entity.Book;
import com.learning.readinglist.entity.User;
import com.learning.readinglist.service.BookService;
import com.learning.readinglist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @GetMapping("/everybody")
    public String all() {
        return ("<h1>Welcome all</h1>");
    }

    @PostMapping("/")
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
         userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") int id){
        return userService.getUser(id);
    }

    @PutMapping("/{userId}/books/{bookId}")
    User addBookToUser(@PathVariable long bookId, @PathVariable long userId){
        Book book =  bookService.getBook(bookId);
        User user = userService.getUser(userId);
        user.addBook(book);
        return userService.createUser(user);
    }

}
