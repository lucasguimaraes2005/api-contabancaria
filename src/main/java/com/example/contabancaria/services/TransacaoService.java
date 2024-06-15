package com.example.contabancaria.services;

import com.example.contabancaria.domain.depositrequest.DepositRequest;
import com.example.contabancaria.domain.transacao.Transacao;
import com.example.contabancaria.domain.transacaorequest.TransacaoRequest;
import com.example.contabancaria.domain.contabancaria.ContaBancaria;
import com.example.contabancaria.domain.contabancaria.ContaBancariaRepository;
import com.example.contabancaria.domain.transacao.TransacaoRepository;
import com.example.contabancaria.domain.user.User;
import com.example.contabancaria.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransacaoService {

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

    @Autowired
    private UserRepository userRepository;

    public Transacao createTransacao(TransacaoRequest transacaoRequest) {
        User usuario = userRepository.findByEmail(transacaoRequest.getEmailContaBancaria()).orElse(null);
        ContaBancaria contaOrigem = contaBancariaRepository.findByIdProprietario(usuario.getId()).orElse(null);
        ContaBancaria contaRecebedora = contaBancariaRepository.findByNumeroConta(transacaoRequest.getNumeroConta()).orElse(null);

        if (contaOrigem == null || contaRecebedora == null) {
            throw new RuntimeException("Conta de origem ou conta recebedora não encontrada");
        }

        if (contaOrigem.getSaldoAtual() < transacaoRequest.getValorTransacao()) {
            throw new RuntimeException("Saldo insuficiente na conta de origem");
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

        return newTransacao;
    }

    public Transacao createDeposito(DepositRequest depositoRequest) {
        ContaBancaria contaRecebedora = contaBancariaRepository.findByNumeroConta(depositoRequest.getNumeroConta()).orElse(null);

        if (contaRecebedora == null) {
            throw new RuntimeException("Conta não encontrada");
        }

        Transacao transacao = new Transacao();
        transacao.setIdContaBancaria(contaRecebedora.getId());
        transacao.setIdContaRecebedora(contaRecebedora.getId());
        transacao.setValorTransacao(depositoRequest.getValorDeposito());
        transacao.setTipoTransacao(depositoRequest.getTipoTransacao());
        transacao.setDataHoraTransacao(new java.sql.Timestamp(System.currentTimeMillis()));

        contaRecebedora.setSaldoAtual(contaRecebedora.getSaldoAtual() + transacao.getValorTransacao());
        contaBancariaRepository.save(contaRecebedora);

        Transacao newTransacao = transacaoRepository.save(transacao);

        return newTransacao;
    }

    public Transacao getTransacao(String id) {
        Transacao transacao = transacaoRepository.findById(id).orElse(null);
        return transacao;
    }

    public List<Transacao> getAllTransacoes(String email) {
        User usuario = userRepository.findByEmail(email).orElse(null);
        List<Transacao> transacoes = transacaoRepository.findByIdContaBancariaOrIdContaRecebedora(usuario.getId(), usuario.getId());
        return transacoes;
    }
}
