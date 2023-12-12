package com.application.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="enderecos")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rua;
    private String nomeRua;
    private String numero;
    private String cidade;
    private String zipCode;
    private String pais;
    private String countyCode;
    private Double latitude;
    private Double longitude;

    @OneToOne
    @JoinColumn(name = "pessoa_id", referencedColumnName = "id")
    @JsonIgnore
    private Pessoa pessoa;

}
