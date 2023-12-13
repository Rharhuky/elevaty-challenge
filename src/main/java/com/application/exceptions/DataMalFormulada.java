package com.application.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DataMalFormulada extends RuntimeException{

    public DataMalFormulada(String message) {
        super(message);
    }
}
