package com.application.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmailInexistenteException extends RuntimeException {
    private final String emailPessoa;
    public EmailInexistenteException(String emailPessoa) {
        this.emailPessoa = emailPessoa;
    }
}
