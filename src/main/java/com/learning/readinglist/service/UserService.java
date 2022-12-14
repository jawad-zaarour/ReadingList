package com.learning.readinglist.service;

import com.learning.readinglist.dto.UserDTO;
import com.learning.readinglist.entity.Book;
import com.learning.readinglist.entity.User;
import com.learning.readinglist.mapper.UserMapper;
import com.learning.readinglist.repo.UserRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserMapper userMapper;


    public UserDTO createUser(User user) {

        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userMapper.getUserDTO(userRepository.save(user));
        } catch (ServiceException serviceException) {
            throw new ServiceException("Error in converting btw DTO and Entity" + serviceException);
        }
    }

    public Boolean deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }


    public User getUser(long id) {
//        return userRepository.findById(id)
//                .map(userMapper::getUserDTO).orElse(null);
        return userRepository.findById(id).orElse(null);
    }

    public UserDTO getUserDTO(long id) {
        return userRepository.findById(id)
                .map(userMapper::getUserDTO).orElse(null);
    }


    public UserDTO addNewBookToUser(long userId, Book newBook) {
        User user = getUser(userId);
        if (user == null) {
            return null;
        }
        user.getBooks().add(newBook);
        this.updateUser(user);
        return userMapper.getUserDTO(user);
    }

    public void updateUser(User user) {
        //User user = userMapper.getUser(userDTO);
        userRepository.save(user);

    }

    public List<UserDTO> getUsersByBookId(long bookId) {
        List<User> users = userRepository.findUsersByBooksId(bookId);
        return userMapper.toUserDTOs(users);
    }

    public UserDTO addBookToUser(long bookId, long userId) {
        Book book = bookService.getBookById(bookId);
        User user = this.getUser(userId);
        user.addBook(book);
        this.updateUser(user);
        return userMapper.getUserDTO(user);
    }


    public String removeBookFromUser(long bookId, long userId) {
        Book book = bookService.getBookById(bookId);
        User user = this.getUser(userId);

        if (book == null || user == null) {

            return "book or user does not exist !!";
        } else if (user.getBooks().remove(book)) {
            this.updateUser(user);
            return book.getId() + " is removed from the reading list of " + user.getId();
        }
        return "this book is not the reading list of the user !!";

    }
}
