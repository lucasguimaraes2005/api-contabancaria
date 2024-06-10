package com.example.contabancaria.controllers;

import com.example.contabancaria.domain.user.User;
import com.example.contabancaria.domain.user.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User loginUser) {
        User user = userRepository.findByEmail(loginUser.getEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (user != null && passwordEncoder.matches(loginUser.getSenha(), user.getSenha())) {
            String token = Jwts.builder()
                    .setSubject(user.getId())
                    .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                    .signWith(SignatureAlgorithm.HS256, "5945a15423121ecc3542f9a7e30028dda8a17a8ab3cd8ef56ffa5f9cf43e3500")
                    .compact();
            Map<String, String> tokenResponse = new HashMap<>();
            tokenResponse.put("token", token);
            return ResponseEntity.ok(tokenResponse);
        } else {
            return ResponseEntity.status(401).build();
        }
    }
}
