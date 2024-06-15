package com.example.contabancaria.domain.transacaorequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransacaoRequest {

    private String emailContaBancaria;
    private Double numeroConta;
    private Double valorTransacao;
    private String tipoTransacao;
}
