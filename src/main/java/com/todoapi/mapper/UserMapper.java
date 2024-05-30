package com.todoapi.mapper;

import com.todoapi.dto.UserDto;
import com.todoapi.model.User;

public class UserMapper {

    // Convert User JPA Entity into UserDto
    public static UserDto mapToUserDto(User user) {
        UserDto userDto = new UserDto(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getEmail(), user.getPassword(), user.getRole());
        return userDto;
    }

    // Convert UserDto into User JPA Entity
    public static User mapToUser(UserDto userDto) {
        User user = new User(
                userDto.getId(),
                userDto.getSurname(),
                userDto.getSurname(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getRole(), null, null, null, false, null, null, null, null, null, 0, null, null);
        return user;
    }
}
