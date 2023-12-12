package com.application.controller;

import com.application.payload.dto.PessoaDTO;
import com.application.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor

@RestController
@RequestMapping("/api/v1/pessoas")
public class PessoaController {

    private final PessoaService pessoaService;

    @GetMapping
    public List<PessoaDTO> pessoas(){
        return this.pessoaService.verTodasPessoas();
    }

}
