package com.application.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class Cartao {

    @Id
    @UuidGenerator(style = UuidGenerator.Style.RANDOM)
    private UUID id;

    private String tipo;
    private String numero;
    private String dataVencimento;
    private String titular;

//    @ManyToOne(cascade = CascadeType.PERSIST)
//    private Pessoa pessoa;
}
