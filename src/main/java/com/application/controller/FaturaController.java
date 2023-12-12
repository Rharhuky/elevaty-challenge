package com.application.controller;

import com.application.configuracao.ConfiguracaoFatura;
import com.application.service.FaturaService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Controller
@RequestMapping("api/v1/faturas")
public class FaturaController {

    private final Path fileStorageLocation;
    private final FaturaService faturaService;

    public FaturaController(ConfiguracaoFatura fileStorageLocation, FaturaService faturaService) {
        this.fileStorageLocation = Paths.get(fileStorageLocation.getDownloadDirectory()).toAbsolutePath().normalize();
        this.faturaService = faturaService;
    }

    @SneakyThrows
    @GetMapping("/{email:.+}")
    @CrossOrigin("*")
    public ResponseEntity<Resource> obterFatura(@PathVariable String email, HttpServletRequest servletRequest) throws FileNotFoundException{
        this.faturaService.gerarPDF(email);
        return this.faturaService.obterPDF(servletRequest);
    }
}
