package com.learning.readinglist.controller;

import com.learning.readinglist.dto.BookDTO;
import com.learning.readinglist.dto.UserDTO;
import com.learning.readinglist.entity.User;
import com.learning.readinglist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/everybody")
    public String all() {
        return ("<h1>Welcome all</h1>");
    }

    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        UserDTO userResponse = userService.createUser(user);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") long id) {
        UserDTO userResponse = userService.getUser(id);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    //TODO (NEW)
    //Get all the users to specific book
    //Notes: I added JsonIgnore in the UserDTO class at the Set<BookDTO> books in order to get only users
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<UserDTO>> getUsersByBookId(@PathVariable("bookId") long bookId) {
        List<UserDTO> users = userService.getUsersByBookId(bookId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //TODO Please check the logic of this method (NEW)
    //add EXCITING book to specific user
    @PutMapping("/{userId}/books/{bookId}")
    public ResponseEntity<UserDTO> addBookToUser(@PathVariable long bookId, @PathVariable long userId) {
        UserDTO response = userService.addBookToUser(bookId, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //TODO Please check the logic of this method (NEW)
    //add NEW book to specific user
    @PostMapping("/{userId}/books/")
    public ResponseEntity<UserDTO> addNewBookToUser(
            @PathVariable long userId, @RequestBody BookDTO newBook) {

        UserDTO response = userService.addNewBookToUser(userId, newBook);
        return new ResponseEntity<>(response, HttpStatus.CREATED);

        /*
        what is the best way to write the below code?
                UserDTO response = userService.addNewBookToUser(userId, newBook);
                return new ResponseEntity<>(response, HttpStatus.CREATED);

                 or

                 return new ResponseEntity<>(userService.addNewBookToUser(userId, newBook), HttpStatus.CREATED);
        */
    }
    //TODO Please check the logic of this method (NEW)
    //remove book from specific user
    @DeleteMapping("/{userId}/books/{bookId}")
    public String removeBookFromUser(@PathVariable long bookId, @PathVariable long userId) {
        return userService.removeBookFromUser(bookId, userId);
    }

}
