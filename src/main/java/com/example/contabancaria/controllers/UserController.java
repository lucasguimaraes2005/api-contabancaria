package com.example.contabancaria.controllers;

import com.example.contabancaria.domain.user.User;
import com.example.contabancaria.domain.userrequest.UserRequest;
import com.example.contabancaria.exceptions.UserNotFoundException;
import com.example.contabancaria.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<User> getUser(@RequestBody UserRequest userRequest) {
        User user = userService.getUser(userRequest.getEmail());
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User updatedUser) {
        try {
            User userUpdated = userService.updateUser(updatedUser);
            return ResponseEntity.ok().body(userUpdated);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody UserRequest userRequest) {
        try {
            userService.deleteUser(userRequest.getEmail());
            return ResponseEntity.ok().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

