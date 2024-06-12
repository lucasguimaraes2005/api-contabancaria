package com.example.contabancaria.domain.contabancaria;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContaBancariaRepository extends JpaRepository<ContaBancaria, String> {
    List<ContaBancaria> findByIdProprietario(String idProprietario);
}
