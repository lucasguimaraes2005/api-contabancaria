package com.example.contabancaria.controllers;

import com.example.contabancaria.domain.contabancaria.ContaBancaria;
import com.example.contabancaria.domain.contabancaria.ContaBancariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contabancaria")
public class ContaController {
    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

    @PostMapping
    public ResponseEntity<ContaBancaria> createContaBancaria(@RequestBody ContaBancaria contaBancaria) {
        contaBancaria.setNumeroConta(Math.random() * 10000);
        contaBancaria.setSaldoAtual(0.0);
        contaBancaria.setDataHoraCadastro(new java.sql.Timestamp(System.currentTimeMillis()));
        ContaBancaria newContaBancaria = contaBancariaRepository.save(contaBancaria);
        return ResponseEntity.ok(newContaBancaria);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaBancaria> getContaBancaria(@PathVariable String id) {
        ContaBancaria contaBancaria = contaBancariaRepository.findById(id).orElse(null);
        return ResponseEntity.ok(contaBancaria);
    }

    @GetMapping("/proprietario/{idProprietario}")
    public ResponseEntity<List<ContaBancaria>> getContasBancariasByProprietario(@PathVariable String idProprietario) {
        List<ContaBancaria> contasBancarias = contaBancariaRepository.findByIdProprietario(idProprietario);
        return ResponseEntity.ok(contasBancarias);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ContaBancaria> updateContaBancaria(@PathVariable String id, @RequestBody ContaBancaria updatedContaBancaria) {
        return contaBancariaRepository.findById(id)
                .map(contaBancaria -> {
                    contaBancaria.setNumeroConta(updatedContaBancaria.getNumeroConta());
                    contaBancaria.setSaldoAtual(updatedContaBancaria.getSaldoAtual());
                    ContaBancaria contaBancariaUpdated = contaBancariaRepository.save(contaBancaria);
                    return ResponseEntity.ok().body(contaBancariaUpdated);
                }).orElse(ResponseEntity.notFound().build());
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
