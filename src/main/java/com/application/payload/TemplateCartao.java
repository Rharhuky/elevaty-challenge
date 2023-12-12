package com.application.payload;

import com.application.model.Cartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor

public class TemplateCartao {

    private String type;
    private String number;
    private String expiration;
    private String owner;

}
