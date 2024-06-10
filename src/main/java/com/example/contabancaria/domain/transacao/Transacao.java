package com.example.contabancaria.domain.transacao;

import jakarta.persistence.*;
import lombok.*;

import com.example.contabancaria.domain.contabancaria.ContaBancaria;

@Table(name = "transacao")
@Entity(name = "transacao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()

public class Transacao {

    @Id
    private String id;

    @Column(name = "id_contabancaria")
    private String idContaBancaria;

    @Column(name = "id_conta_recebedora")
    private String idContaRecebedora;

    @Column(name = "valor_transacao")
    private Double valorTransacao;

    @Column(name = "tipo_transacao")
    private String tipoTransacao;

    @Column(name = "datahora_transacao")
    private java.sql.Timestamp dataHoraTransacao;

    @ManyToOne
    @JoinColumn(name = "id_contabancaria", referencedColumnName = "id", insertable = false, updatable = false)
    private ContaBancaria contaBancaria;

    @ManyToOne
    @JoinColumn(name = "id_conta_recebedora", referencedColumnName = "id", insertable = false, updatable = false)
    private ContaBancaria contaRecebedora;
}
