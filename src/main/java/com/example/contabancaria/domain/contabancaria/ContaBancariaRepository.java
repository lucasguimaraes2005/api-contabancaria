package com.example.contabancaria.domain.contabancaria;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, String> {
    Optional<ContaBancaria> findByIdProprietario(String idProprietario);
    Optional<ContaBancaria> findByNumeroConta(Double numeroConta);
    void deleteByIdProprietario(String idProprietario);
}
