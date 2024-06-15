package com.example.contabancaria.controllers;

import com.example.contabancaria.domain.user.User;
import com.example.contabancaria.exceptions.UserAlreadyExistsException;
import com.example.contabancaria.services.AuthService;
import com.example.contabancaria.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {
        Map<String, String> tokenResponse = authService.authenticate(loginUser);
        if (tokenResponse != null) {
            return ResponseEntity.ok(tokenResponse);
        } else {
            return ResponseEntity.status(401).body("Usuário não encontrado ou senha incorreta");
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        try {
            User newUser = userService.createUser(user);
            return ResponseEntity.ok(newUser);
        } catch (UserAlreadyExistsException e) {
            return ResponseEntity.status(400).body("Usuário já existe no sistema");
        }
    }
}
