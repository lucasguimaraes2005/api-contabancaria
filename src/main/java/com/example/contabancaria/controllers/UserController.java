package com.example.contabancaria.controllers;

import com.example.contabancaria.domain.contabancaria.ContaBancariaRepository;
import com.example.contabancaria.domain.transacao.TransacaoRepository;
import com.example.contabancaria.domain.user.User;
import com.example.contabancaria.domain.user.UserRepository;
import com.example.contabancaria.domain.userrequest.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getSenha());
        user.setSenha(hashedPassword);
        User newUser = userRepository.save(user);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping
    public ResponseEntity<User> getUser(@RequestBody UserRequest userRequest) {
        User user = userRepository.findByEmail(userRequest.getEmail()).orElse(null);
        return ResponseEntity.ok(user);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User updatedUser) {
        return userRepository.findByEmail(updatedUser.getEmail())
                .map(user -> {
                    user.setNome(updatedUser.getNome());
                    user.setEmail(updatedUser.getEmail());
                    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    String hashedPassword = passwordEncoder.encode(updatedUser.getSenha());
                    user.setSenha(hashedPassword);
                    User userUpdated = userRepository.save(user);
                    return ResponseEntity.ok().body(userUpdated);
                }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody UserRequest userRequest) {
        return userRepository.findByEmail(userRequest.getEmail())
                .map(user -> {

                    transacaoRepository.deleteByIdContaBancaria(user.getId());


                    transacaoRepository.deleteByIdContaRecebedora(user.getId());


                    contaBancariaRepository.deleteByIdProprietario(user.getId());


                    userRepository.delete(user);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }




}
