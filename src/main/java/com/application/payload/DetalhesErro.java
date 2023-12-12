package com.application.payload;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class DetalhesErro {

    private String mensagem;
    private LocalDateTime localDateTime;
    private String rota;

}
