package com.example.contabancaria.services;

import com.example.contabancaria.domain.user.User;
import com.example.contabancaria.domain.user.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public Map<String, String> authenticate(User loginUser) {
        Optional<User> optionalUser = userRepository.findByEmail(loginUser.getEmail());
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (passwordEncoder.matches(loginUser.getSenha(), user.getSenha())) {
                String token = Jwts.builder()
                        .setSubject(user.getId())
                        .setExpiration(new Date(System.currentTimeMillis() + 3600000))
                        .signWith(SignatureAlgorithm.HS256, "5945a15423121ecc3542f9a7e30028dda8a17a8ab3cd8ef56ffa5f9cf43e3500")
                        .compact();
                Map<String, String> tokenResponse = new HashMap<>();
                tokenResponse.put("token", token);
                return tokenResponse;
            }
        }
        return null;
    }
}
