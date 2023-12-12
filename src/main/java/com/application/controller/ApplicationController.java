package com.application.controller;
import com.application.model.Endereco;
import com.application.model.Pessoa;
import com.application.payload.TemplateCartao;
import com.application.payload.TemplatePessoa;
import com.application.payload.TemplateRequestCard;
import com.application.payload.TemplateRequestPessoa;
import com.application.payload.dto.CartaoDTO;
import com.application.payload.dto.PessoaDTO;
import com.application.repository.PessoaRepository;
import com.application.service.TemplateService;
import com.application.utils.Constantes;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.bcel.Const;
import org.aspectj.apache.bcel.classfile.Field;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor

@RestController
@RequestMapping("/api/v1/persons")
public class ApplicationController {

    private final RestTemplate restTemplate;
    private final TemplateService templateService;


    @GetMapping
    @CrossOrigin("*")
    public ResponseEntity<List<PessoaDTO>> verPessoas(
            @RequestParam(defaultValue = Constantes.DEFAULT_DATA_INICIO) String dataInicio,
            @RequestParam(defaultValue = Constantes.DEFAULT_DATA_FIM)    String dataFim){

        //FIXME USING EXCEPTIONS

        List<TemplatePessoa> templatePessoa =
                restTemplate.getForEntity(
                                templateService.requestPersonsURL(dataInicio,dataFim),
                                TemplateRequestPessoa.class)
                        .getBody()
                        .getData();

        return  ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.templateService.handleTemplatePessoaWithEndereco(templatePessoa));

    }

    @GetMapping("/details")
    public ResponseEntity<List<PessoaDTO>> verDetalhes(
            @RequestParam(defaultValue = Constantes.DEFAULT_QUANTIDADE_CARTOES) String quantidadeCartoes){

        List<PessoaDTO> pessoaDTOsResponse = this.templateService.handleTemplateCartao(
                this.restTemplate.getForEntity(
                                templateService.requestCardsURL(quantidadeCartoes),
                        TemplateRequestCard.class)
                        .getBody()
                        .getData());

        return ResponseEntity.status(HttpStatus.OK).body(pessoaDTOsResponse);


    }

}
