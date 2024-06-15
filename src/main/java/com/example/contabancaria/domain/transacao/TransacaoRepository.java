package com.example.contabancaria.domain.transacao;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransacaoRepository extends JpaRepository<Transacao, String> {
    void deleteByIdContaBancaria(String idContaBancaria);

    void deleteByIdContaRecebedora(String idContaRecebedora);

    List<Transacao> findByIdContaBancariaOrIdContaRecebedora(String id, String id1);
}
