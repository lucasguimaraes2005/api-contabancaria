package com.example.contabancaria.controllers;

import com.example.contabancaria.domain.contabancaria.ContaBancaria;
import com.example.contabancaria.domain.contabancaria.ContaBancariaRepository;
import com.example.contabancaria.domain.contabancariarequest.ContaBancariaRequest;
import com.example.contabancaria.domain.user.User;
import com.example.contabancaria.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contabancaria")
public class ContaController {
    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> createContaBancaria(@RequestBody ContaBancariaRequest contaBancariaRequest) {

        User usuario = userRepository.findByEmail(contaBancariaRequest.getEmailUsuario()).orElse(null);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não encontrado");
        }

        ContaBancaria contaExistente = contaBancariaRepository.findByIdProprietario(usuario.getId()).orElse(null);
        if (contaExistente != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Conta bancária já existente para este usuário");
        }

        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.setIdProprietario(usuario.getId());
        contaBancaria.setNumeroConta(Math.random() * 10000);
        contaBancaria.setSaldoAtual(0.0);
        contaBancaria.setDataHoraCadastro(new java.sql.Timestamp(System.currentTimeMillis()));

        ContaBancaria newContaBancaria = contaBancariaRepository.save(contaBancaria);

        return ResponseEntity.ok(newContaBancaria);
    }


    @GetMapping("/conta")
    public ResponseEntity<?> getContaBancariaPorEmail(@RequestBody ContaBancariaRequest contaBancariaRequest) {
        User usuario = userRepository.findByEmail(contaBancariaRequest.getEmailUsuario()).orElse(null);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário não encontrado");
        }

        ContaBancaria contaBancaria = contaBancariaRepository.findByIdProprietario(usuario.getId()).orElse(null);
        if (contaBancaria == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Conta bancária não encontrada para este usuário");
        }

        return ResponseEntity.ok(contaBancaria);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContaBancaria(@PathVariable String id) {
        return contaBancariaRepository.findById(id)
                .map(contaBancaria -> {
                    contaBancariaRepository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }
}
