package com.learning.readinglist.service;

import com.learning.readinglist.ServiceException;
import com.learning.readinglist.entity.User;
import com.learning.readinglist.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public User createUser(User user) {

        if (userRepository.findByUserName(user.getUserName()).isPresent() ||
                userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ServiceException("This user is already exist");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public void deleteUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new ServiceException("user with id " + id + " does not exists");
        }
        userRepository.deleteById(id);
    }

    public User getUser(long id) {
        if (!userRepository.existsById(id)) {
            throw new ServiceException("user with id " + id + " does not exists");
        }
        return userRepository.findById(id).orElse(null);
    }


}
