package com.learning.readinglist;

import com.learning.readinglist.entity.Book;
import com.learning.readinglist.entity.User;
import com.learning.readinglist.repo.UserRepository;
import com.learning.readinglist.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    private UserService userService;
    User user;

    @Mock
    private UserRepository userRepository;


    @BeforeEach
    void setUp() {
        userService = new UserService(userRepository);
        Set<Book> books = new HashSet<>();
        user = new User(1L, books, "Jawad", "123456789",
                "jawad@mail.com", false, "ROLE_USER");
    }


    @Test
    public void createUserTest() {
        //when
        userService.createUser(user);
        //then
        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userArgumentCaptor.capture());
        User captureUser = userArgumentCaptor.getValue();
        assertThat(captureUser).isEqualTo(user);
    }


}
