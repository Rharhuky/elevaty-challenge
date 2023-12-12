package com.application.exceptions;

import com.application.payload.DetalhesErro;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

@ControllerAdvice
public class CentralExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FileNotFoundException.class)
    public ResponseEntity<DetalhesErro> handleFileNotFoundException(FileNotFoundException exception, WebRequest webRequest){

        DetalhesErro detalhesErro = DetalhesErro.builder()
                .mensagem("ARQUIVO NAO ENCONTRADO")
                .localDateTime(LocalDateTime.now())
                .rota(webRequest.getDescription(false))
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detalhesErro);


    }

    @ExceptionHandler(EmailInexistenteException.class)
    public ResponseEntity<DetalhesErro> handleEmailInexistenteException(EmailInexistenteException exception, WebRequest webRequest){

        DetalhesErro detalhesErro = DetalhesErro.builder()
                .mensagem(String.format("EMAIL |%s| NÃO ENCONTRADO ", exception.getEmailPessoa()))
                .rota(webRequest.getDescription(false))
                .localDateTime(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(detalhesErro, HttpStatus.NOT_FOUND);

    }



}
