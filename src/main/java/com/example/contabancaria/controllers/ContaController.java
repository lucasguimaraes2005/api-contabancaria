package com.example.contabancaria.controllers;

import com.example.contabancaria.domain.contabancaria.ContaBancaria;
import com.example.contabancaria.domain.contabancariarequest.ContaBancariaRequest;
import com.example.contabancaria.exceptions.ContaBancariaAlreadyExistsException;
import com.example.contabancaria.exceptions.ContaBancariaNotFoundException;
import com.example.contabancaria.services.ContaBancariaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/contabancaria")
public class ContaController {

    @Autowired
    private ContaBancariaService contaBancariaService;

    @PostMapping
    public ResponseEntity<?> createContaBancaria(@RequestBody ContaBancariaRequest contaBancariaRequest) {
        try {
            ContaBancaria contaBancaria = contaBancariaService.createContaBancaria(contaBancariaRequest);
            return ResponseEntity.ok(contaBancaria);
        } catch (ContaBancariaAlreadyExistsException | ContaBancariaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/conta")
    public ResponseEntity<?> getContaBancariaPorEmail(@RequestBody ContaBancariaRequest contaBancariaRequest) {
        try {
            ContaBancaria contaBancaria = contaBancariaService.getContaBancariaPorEmail(contaBancariaRequest);
            return ResponseEntity.ok(contaBancaria);
        } catch (ContaBancariaNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteContaBancaria(@PathVariable String id) {
        try {
            contaBancariaService.deleteContaBancaria(id);
            return ResponseEntity.ok().build();
        } catch (ContaBancariaNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
