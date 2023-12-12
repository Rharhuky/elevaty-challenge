package com.application.payload.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CartaoDTO {

    private UUID id  = UUID.randomUUID();
    private String tipo;
    private String numero;
    private String dataVencimento;
    private String titular;

//    private PessoaDTO pessoa;

}
