package com.learning.readinglist.controller;

import com.learning.readinglist.dto.UserDTO;
import com.learning.readinglist.entity.Book;
import com.learning.readinglist.entity.User;
import com.learning.readinglist.service.BookService;
import com.learning.readinglist.service.UserService;
import com.learning.readinglist.util.ObjectMapperUtils;
import org.modelmapper.ModelMapper;
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

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BookService bookService;

    @GetMapping("/everybody")
    public String all() {
        return ("<h1>Welcome all</h1>");
    }

    @PostMapping("/")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        User userRequest = modelMapper.map(userDTO, User.class);
        UserDTO userResponse = modelMapper.map(userService.createUser(userRequest), UserDTO.class);
        return new ResponseEntity<>(userResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") long id) {
        User user = userService.getUser(id);
        UserDTO response = modelMapper.map(user, UserDTO.class);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //TODO (NEW)
    //Get all the users to specific book
    //Notes: I added JsonIgnore in the UserDTO class at the Set<BookDTO> books in order to get only users
    @GetMapping("/book/{bookId}")
    public List<UserDTO> getUsersByBookId(@PathVariable("bookId") long bookId) {
        List<User> users = userService.getUsersByBookId(bookId);
        return ObjectMapperUtils.mapAll(users, UserDTO.class);
    }

    //TODO Please check the logic of this method (NEW)
    //add EXCITING book to specific user
    @PutMapping("/{userId}/books/{bookId}")
    public ResponseEntity<UserDTO> addBookToUser(@PathVariable long bookId, @PathVariable long userId) {
        Book book = bookService.getBookById(bookId);
        User user = userService.getUser(userId);
        user.getBooks().add(book);
        userService.updateUser(user);
        UserDTO response = modelMapper.map(user, UserDTO.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //TODO Please check the logic of this method (NEW)
    //add NEW book to specific user
    @PostMapping("/{userId}/books/")
    public ResponseEntity<UserDTO> addNewBookToUser(
            @PathVariable long userId, @RequestBody Book newBook) {

        User user = userService.addBook(userId, newBook);
        userService.updateUser(user);
        UserDTO response = modelMapper.map(user, UserDTO.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
