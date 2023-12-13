package com.application.controller;
import com.application.payload.TemplatePessoa;
import com.application.payload.request.TemplateRequestCard;
import com.application.payload.request.TemplateRequestPessoa;
import com.application.payload.dto.PessoaDTO;
import com.application.service.TemplateService;
import com.application.utils.Constantes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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


    @GetMapping("/details/{email:.+}")
    @CrossOrigin("*")
    public ResponseEntity<PessoaDTO> verDetalhes(
            @RequestParam(defaultValue = Constantes.DEFAULT_QUANTIDADE_CARTOES) String quantidadeCartoes,
            @PathVariable String email){

        PessoaDTO pessoaDTOsResponse =
                this.templateService.verDetalhes(
                        this.templateService.handleTemplateCartao(
                                this.restTemplate.getForEntity(this.templateService.requestCardsURL(quantidadeCartoes), TemplateRequestCard.class)
                                        .getBody()
                                        .getData()), email);


        return ResponseEntity.status(HttpStatus.OK).body(pessoaDTOsResponse);


    }

}
