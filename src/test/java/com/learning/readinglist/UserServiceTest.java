package com.learning.readinglist;

import com.learning.readinglist.dto.UserDTO;
import com.learning.readinglist.entity.Book;
import com.learning.readinglist.entity.User;
import com.learning.readinglist.repo.UserRepository;
import com.learning.readinglist.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService = new UserService();

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private User user = new User();
    private UserDTO userDTO = new UserDTO();


    @BeforeEach
    void setUp() {
        user.setUserName("Jawad");
        user.setPassword("123456789");
        user.setEmail("jawad@mail.com");
        user.setRoles("ROLE_USER");
        user.setActive(false);
        user.setId(25L);

        userDTO.setUserName("Jawad");
        userDTO.setPassword("123456789");
        userDTO.setEmail("jawad@mail.com");
        userDTO.setRoles("ROLE_USER");
        userDTO.setActive(false);
        userDTO.setId(25L);
    }


    @Test
    public void createUserTest() {
        when(modelMapper.map(any(), any())).thenReturn(userDTO);
        when(passwordEncoder.encode(any())).thenReturn("a");
        long userId = user.getId();
        given(userRepository.save(user)).willReturn(user);
        userService.createUser(user);
        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);
        verify(userRepository)
                .save(userArgumentCaptor.capture());
        User captureUser = userArgumentCaptor.getValue();
        assertThat(captureUser).isEqualTo(user);

        given(userRepository.findById(userId)).willReturn(Optional.ofNullable(user));
        assertNotNull(userService.getUser(userId));
    }


}
