package com.learning.readinglist.service;

import com.learning.readinglist.entity.User;
import com.learning.readinglist.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        String encoder = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(encoder);
        return userRepository.save(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }

    public User getUser(long id) {
        return userRepository.findById(id).orElse(null);
    }


}
