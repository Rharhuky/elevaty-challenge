package com.application.payload.dto;

import com.application.model.Pessoa;
import lombok.*;

@Getter
@Setter
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

    @Override
    public String toString() {
        return String.format("%s %s\n%s %s \n%s",numero,nomeRua, cidade, pais, zipCode);
    }
}
