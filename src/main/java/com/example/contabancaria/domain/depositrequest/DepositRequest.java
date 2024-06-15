package com.example.contabancaria.domain.depositrequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositRequest {

    private Double numeroConta;
    private Double valorDeposito;
    private String tipoTransacao;
}

