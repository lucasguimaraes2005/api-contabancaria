package com.example.contabancaria.services;

import com.example.contabancaria.domain.contabancaria.ContaBancaria;
import com.example.contabancaria.domain.contabancaria.ContaBancariaRepository;
import com.example.contabancaria.domain.contabancariarequest.ContaBancariaRequest;
import com.example.contabancaria.domain.user.User;
import com.example.contabancaria.domain.user.UserRepository;
import com.example.contabancaria.exceptions.ContaBancariaAlreadyExistsException;
import com.example.contabancaria.exceptions.ContaBancariaNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContaBancariaService {

    @Autowired
    private ContaBancariaRepository contaBancariaRepository;

    @Autowired
    private UserRepository userRepository;

    public ContaBancaria createContaBancaria(ContaBancariaRequest contaBancariaRequest) throws ContaBancariaAlreadyExistsException, ContaBancariaNotFoundException {
        User usuario = userRepository.findByEmail(contaBancariaRequest.getEmailUsuario()).orElse(null);
        if (usuario == null) {
            throw new ContaBancariaNotFoundException("Usuário não encontrado");
        }

        ContaBancaria contaExistente = contaBancariaRepository.findByIdProprietario(usuario.getId()).orElse(null);
        if (contaExistente != null) {
            throw new ContaBancariaAlreadyExistsException("Conta bancária já existente para este usuário");
        }

        ContaBancaria contaBancaria = new ContaBancaria();
        contaBancaria.setIdProprietario(usuario.getId());
        contaBancaria.setNumeroConta(Math.random() * 10000);
        contaBancaria.setSaldoAtual(0.0);
        contaBancaria.setDataHoraCadastro(new java.sql.Timestamp(System.currentTimeMillis()));

        return contaBancariaRepository.save(contaBancaria);
    }

    public ContaBancaria getContaBancariaPorEmail(ContaBancariaRequest contaBancariaRequest) throws ContaBancariaNotFoundException {
        User usuario = userRepository.findByEmail(contaBancariaRequest.getEmailUsuario()).orElse(null);
        if (usuario == null) {
            throw new ContaBancariaNotFoundException("Usuário não encontrado");
        }

        ContaBancaria contaBancaria = contaBancariaRepository.findByIdProprietario(usuario.getId()).orElse(null);
        if (contaBancaria == null) {
            throw new ContaBancariaNotFoundException("Conta bancária não encontrada para este usuário");
        }

        return contaBancaria;
    }

    public void deleteContaBancaria(String id) throws ContaBancariaNotFoundException {
        Optional<ContaBancaria> contaBancaria = contaBancariaRepository.findById(id);
        if (!contaBancaria.isPresent()) {
            throw new ContaBancariaNotFoundException("Conta bancária não encontrada");
        }
        contaBancariaRepository.deleteById(id);
    }
}
