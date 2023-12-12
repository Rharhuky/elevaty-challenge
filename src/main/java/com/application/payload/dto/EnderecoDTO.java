package com.application.payload.dto;

import com.application.model.Pessoa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class EnderecoDTO {

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

    private PessoaDTO pessoa;
}
