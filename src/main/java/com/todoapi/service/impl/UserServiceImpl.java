package com.todoapi.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.todoapi.dto.UserDto;
import com.todoapi.exception.ResourceNotFoundException;
import com.todoapi.model.User;
import com.todoapi.repository.UserRepository;
import com.todoapi.service.UserInfoDetails;
import com.todoapi.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userDetail = repository.findByUsername(username);
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    @Override
    public ResponseEntity<String> addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        repository.save(user);
        return new ResponseEntity<>("User added successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> createUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        repository.save(user);
        return new ResponseEntity<>("User added successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> updateUser(int id, User updateUser) {
        try {
            Optional<User> optionalUser = repository.findById(id);
            if (!optionalUser.isPresent()) {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }

            User existingUser = optionalUser.get();

            if (updateUser.getName() != null) {
                existingUser.setName(updateUser.getName());
            }
            if (updateUser.getSurname() != null) {
                existingUser.setSurname(updateUser.getSurname());
            }
            if (updateUser.getEmail() != null) {
                existingUser.setEmail(updateUser.getEmail());
            }
            if (updateUser.getUsername() != null) {
                existingUser.setUsername(updateUser.getUsername());
            }

            repository.save(existingUser);

            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Updating error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = repository.findAll();
        return users.stream().map((user) -> modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserDto getUser(int userId) {
        User user = repository.findById(userId).orElseThrow(
            () -> new ResourceNotFoundException("User", "id", userId)
        );
        return modelMapper.map(user, UserDto.class);
    }
}
