package com.application.payload.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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

    @Override
    public String toString() {
        return String.format("%s\n%s\n%s\n", getNomeCompleto(), endereco, celular );
    }

    private String getNomeCompleto(){
        return nome + " " + sobrenome;
    }
}
