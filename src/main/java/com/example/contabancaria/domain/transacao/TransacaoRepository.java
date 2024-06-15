package com.example.contabancaria.domain.transacao;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepository extends JpaRepository<Transacao, String> {
    void deleteByIdContaBancaria(String idContaBancaria);

    void deleteByIdContaRecebedora(String idContaRecebedora);
}
