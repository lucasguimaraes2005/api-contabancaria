package com.example.contabancaria.controllers;

import com.example.contabancaria.domain.transacao.Transacao;
import com.example.contabancaria.domain.transacao.TransacaoRepository;
import com.example.contabancaria.domain.contabancaria.ContaBancaria;
import com.example.contabancaria.domain.contabancaria.ContaBancariaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {
    @Autowired
    private TransacaoRepository transacaoRepository;
    private ContaBancariaRepository contaBancariaRepository;

    @PostMapping
    public ResponseEntity<Transacao> createTransacao(@RequestBody Transacao transacao) {
        transacao.setDataHoraTransacao(new java.sql.Timestamp(System.currentTimeMillis()));
        Transacao newTransacao = transacaoRepository.save(transacao);


        ContaBancaria contaRecebedora = contaBancariaRepository.findById(transacao.getIdContaRecebedora()).orElse(null);
        if (contaRecebedora != null) {
            contaRecebedora.setSaldoAtual(contaRecebedora.getSaldoAtual() + transacao.getValorTransacao());
            contaBancariaRepository.save(contaRecebedora);
        }

        return ResponseEntity.ok(newTransacao);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Transacao> getTransacao(@PathVariable String id) {
        Transacao transacao = transacaoRepository.findById(id).orElse(null);
        return ResponseEntity.ok(transacao);
    }

    @GetMapping
    public ResponseEntity<List<Transacao>> getAllTransacoes() {
        List<Transacao> transacoes = transacaoRepository.findAll();
        return ResponseEntity.ok(transacoes);
    }
}
