package com.example.contabancaria.domain.user;


import com.example.contabancaria.domain.contabancaria.ContaBancaria;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Table(name = "usuario")
@Entity(name = "usuario")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
@Getter
@Setter
public class User {

    @Id @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String nome;

    private String sexo;

    private String CPF;

    private String email;

    private String senha;


    @JsonManagedReference
    @OneToMany(mappedBy = "proprietario")
    private List<ContaBancaria> contasBancarias;
}
