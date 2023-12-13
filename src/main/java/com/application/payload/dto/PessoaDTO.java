package com.application.payload.dto;

import com.application.model.Genero;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PessoaDTO {

    private Long id;
    private String nome;
    private String sobrenome;
    private String nascimento;
    private String email;
    private String celular;

    private String genero;

    private List<CartaoDTO> cartoes = new ArrayList<>();

    private EnderecoDTO endereco;

}
