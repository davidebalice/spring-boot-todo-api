package com.todoapi.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.todoapi.dto.UserDto;
import com.todoapi.model.User;

@Service
public interface UserService extends UserDetailsService {
    List<UserDto> getAllUsers();

    UserDto getUser(int id);

    ResponseEntity<String> addUser(User user);

    ResponseEntity<String> createUser(UserDto user);

    ResponseEntity<String> updateUser(int id, User updateUser);

    ResponseEntity<User> getUserByUsername(String username);
}