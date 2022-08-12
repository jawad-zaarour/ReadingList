package com.learning.readinglist.mapper;


import com.learning.readinglist.dto.UserDTO;
import com.learning.readinglist.entity.User;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface UserMapper {
    UserDTO getUserDTO(User user);
    List<UserDTO> toUserDTOs(List<User> users);

    User getUser(UserDTO userDTO);
}
