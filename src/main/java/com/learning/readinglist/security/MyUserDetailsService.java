package com.learning.readinglist.security;

import com.learning.readinglist.entity.User;
import com.learning.readinglist.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(userName);
        //check if an Optional has a value (if not >> throw an exception)
        user.orElseThrow(() -> new UsernameNotFoundException("Not Found: " + userName));
        return user.map(MyUserDetails::new).get();
    }
}
