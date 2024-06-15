package com.example.contabancaria.services;

import com.example.contabancaria.domain.contabancaria.ContaBancariaRepository;
import com.example.contabancaria.domain.transacao.TransacaoRepository;
import com.example.contabancaria.domain.user.User;
import com.example.contabancaria.domain.user.UserRepository;
import com.example.contabancaria.exceptions.UserAlreadyExistsException;
import com.example.contabancaria.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

    @Autowired
    private TransacaoRepository transacaoRepository;

    public User createUser(User user) throws UserAlreadyExistsException {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(user.getSenha());
        user.setSenha(hashedPassword);


        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new UserAlreadyExistsException("Usuário já existe no sistema");
        }

        return userRepository.save(user);
    }

    public User getUser(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public User updateUser(User updatedUser) throws UserNotFoundException {
        return userRepository.findByEmail(updatedUser.getEmail())
                .map(user -> {
                    user.setNome(updatedUser.getNome());
                    user.setEmail(updatedUser.getEmail());
                    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                    String hashedPassword = passwordEncoder.encode(updatedUser.getSenha());
                    user.setSenha(hashedPassword);
                    return userRepository.save(user);
                }).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    public void deleteUser(String email) throws UserNotFoundException {
        userRepository.findByEmail(email)
                .map(user -> {
                    transacaoRepository.deleteByIdContaBancaria(user.getId());
                    transacaoRepository.deleteByIdContaRecebedora(user.getId());
                    contaBancariaRepository.deleteByIdProprietario(user.getId());
                    userRepository.delete(user);
                    return null;
                }).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }
}

