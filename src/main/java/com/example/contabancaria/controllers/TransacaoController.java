package com.example.contabancaria.controllers;

import com.example.contabancaria.domain.depositrequest.DepositRequest;
import com.example.contabancaria.domain.transacao.Transacao;
import com.example.contabancaria.domain.transacao.TransacaoRepository;
import com.example.contabancaria.domain.contabancaria.ContaBancaria;
import com.example.contabancaria.domain.contabancaria.ContaBancariaRepository;
import com.example.contabancaria.domain.transacaorequest.TransacaoRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transacao")
public class TransacaoController {
    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

    @PostMapping
    public ResponseEntity<?> createTransacao(@RequestBody TransacaoRequest transacaoRequest) {

        ContaBancaria contaOrigem = contaBancariaRepository.findById(transacaoRequest.getIdContaBancaria()).orElse(null);
        ContaBancaria contaRecebedora = contaBancariaRepository.findByNumeroConta(transacaoRequest.getNumeroConta()).orElse(null);

        if (contaOrigem == null || contaRecebedora == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Conta de origem ou conta recebedora não encontrada");
        }

        if (contaOrigem.getSaldoAtual() < transacaoRequest.getValorTransacao()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Saldo insuficiente na conta de origem");
        }

        Transacao transacao = new Transacao();
        transacao.setIdContaBancaria(contaOrigem.getId());
        transacao.setIdContaRecebedora(contaRecebedora.getId());
        transacao.setValorTransacao(transacaoRequest.getValorTransacao());
        transacao.setTipoTransacao(transacaoRequest.getTipoTransacao());
        transacao.setDataHoraTransacao(new java.sql.Timestamp(System.currentTimeMillis()));

        contaOrigem.setSaldoAtual(contaOrigem.getSaldoAtual() - transacao.getValorTransacao());
        contaRecebedora.setSaldoAtual(contaRecebedora.getSaldoAtual() + transacao.getValorTransacao());

        contaBancariaRepository.save(contaOrigem);
        contaBancariaRepository.save(contaRecebedora);

        Transacao newTransacao = transacaoRepository.save(transacao);

        return ResponseEntity.ok(newTransacao);
    }


    @PostMapping("/deposito")
    public ResponseEntity<?> createDeposito(@RequestBody DepositRequest depositoRequest) {

        ContaBancaria contaRecebedora = contaBancariaRepository.findByNumeroConta(depositoRequest.getNumeroConta()).orElse(null);


        if (contaRecebedora != null) {
            Transacao transacao = new Transacao();
            transacao.setIdContaBancaria(contaRecebedora.getId());
            transacao.setIdContaRecebedora(contaRecebedora.getId());
            transacao.setValorTransacao(depositoRequest.getValorDeposito());
            transacao.setTipoTransacao(depositoRequest.getTipoTransacao());
            transacao.setDataHoraTransacao(new java.sql.Timestamp(System.currentTimeMillis()));

            contaRecebedora.setSaldoAtual(contaRecebedora.getSaldoAtual() + transacao.getValorTransacao());
            contaBancariaRepository.save(contaRecebedora);

            Transacao newTransacao = transacaoRepository.save(transacao);

            return ResponseEntity.ok(newTransacao);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Conta não encontrada");
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
