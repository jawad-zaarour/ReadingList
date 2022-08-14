package com.learning.readinglist.mapper;

import com.learning.readinglist.dto.UserDTO;
import com.learning.readinglist.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO getUserDTO(User user);

    List<UserDTO> toUserDTOs(List<User> users);

    User getUser(UserDTO userDTO);

}
