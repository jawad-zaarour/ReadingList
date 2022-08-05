package com.learning.readinglist.service;

import com.learning.readinglist.ServiceException;
import com.learning.readinglist.dto.BookDTO;
import com.learning.readinglist.dto.UserDTO;
import com.learning.readinglist.entity.User;
import com.learning.readinglist.repo.UserRepository;
import com.learning.readinglist.util.ObjectMapperUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookService bookService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserService() {

    }

    public UserDTO createUser(User user) {

        if (userRepository.findByUserName(user.getUserName()).isPresent() ||
                userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ServiceException("This user is already exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        UserDTO userResponse = modelMapper.map(userRepository.save(user), UserDTO.class);
        return userResponse;
    }

    public void deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new ServiceException("user with id " + id + " does not exists");
        }
        userRepository.deleteById(id);
    }

    public UserDTO getUser(long id) {

        Optional<User> result = userRepository.findById(id);
        if (result.isPresent()) {
            UserDTO userResponse = modelMapper.map(result.get(), UserDTO.class);
            return userResponse;
        } else {
            throw new ServiceException("user with id " + id + " does not exists");
        }


    }


    public UserDTO addNewBookToUser(long userId, BookDTO newBook) {
        UserDTO user = getUser(userId);
        if (user == null) {
            return null;
        }
        user.getBooks().add(newBook);
        this.updateUser(user);
        return user;
    }

    public void updateUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        userRepository.save(user);

    }

    public List<UserDTO> getUsersByBookId(long bookId) {
        List<User> users = userRepository.findUsersByBooksId(bookId);
        return ObjectMapperUtils.mapAll(users, UserDTO.class);
    }

    public UserDTO addBookToUser(long bookId, long userId) {
        BookDTO book = bookService.getBookById(bookId);
        UserDTO user = this.getUser(userId);
        user.getBooks().add(book);
        this.updateUser(user);
        return user;
    }

    //todo so2al: hon 3ende bel bookService.getBookById berja3 exception eza ma la2a l book
    //b7al raja3 exception ya3ne book==null ? kermll ane 3am shayek hon 3al book eza null
    //bas bel mante2 mosta7el tkon null ya book aw exception !!
    //so2ale howe lama yraje3 exception behot null bel variable ?
    public String removeBookFromUser(long bookId, long userId) {
        BookDTO book = bookService.getBookById(bookId);
        UserDTO user = this.getUser(userId);

        if (book == null || user == null) {

            return "book or user does not exist !!";
        }
        if (user.getBooks().remove(book)) {
            this.updateUser(user);
            return book.getId() + " is removed from the reading list of " + user.getId();
        }
        return "this book is not the reading list of the user !!";

    }
}
