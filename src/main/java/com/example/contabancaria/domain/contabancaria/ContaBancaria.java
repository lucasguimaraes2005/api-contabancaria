package com.example.contabancaria.domain.contabancaria;

import com.example.contabancaria.domain.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "contabancaria")
@Entity(name = "contabancaria")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()

public class ContaBancaria {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "id_proprietario")
    private String idProprietario;

    @Column(name = "numeroconta")
    private Double numeroConta;

    @Column(name = "saldoatual")
    private Double saldoAtual;

    @Column(name = "datahora_cadastro")
    private java.sql.Timestamp dataHoraCadastro;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_proprietario", referencedColumnName = "id", insertable = false, updatable = false)
    private User proprietario;
}
