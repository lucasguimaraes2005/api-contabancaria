package com.example.contabancaria.controllers;

import com.example.contabancaria.domain.depositrequest.DepositRequest;
import com.example.contabancaria.domain.transacao.Transacao;
import com.example.contabancaria.domain.transacaorequest.TransacaoRequest;
import com.example.contabancaria.services.TransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {

    @Autowired
    private TransacaoService transacaoService;

    @PostMapping
    public ResponseEntity<?> createTransacao(@RequestBody TransacaoRequest transacaoRequest) {
        try {
            Transacao transacao = transacaoService.createTransacao(transacaoRequest);
            return ResponseEntity.ok(transacao);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/deposito")
    public ResponseEntity<?> createDeposito(@RequestBody DepositRequest depositoRequest) {
        try {
            Transacao transacao = transacaoService.createDeposito(depositoRequest);
            return ResponseEntity.ok(transacao);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transacao> getTransacao(@PathVariable String id) {
        Transacao transacao = transacaoService.getTransacao(id);
        return ResponseEntity.ok(transacao);
    }

    @GetMapping
    public ResponseEntity<List<Transacao>> getAllTransacoes(@RequestParam String email) {
        List<Transacao> transacoes = transacaoService.getAllTransacoes(email);
        return ResponseEntity.ok(transacoes);
    }
}
