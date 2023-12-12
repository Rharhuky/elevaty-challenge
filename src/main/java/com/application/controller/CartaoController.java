package com.application.controller;

import com.application.model.Cartao;
import com.application.payload.dto.CartaoDTO;
import com.application.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cards")
public class CartaoController {

    private CartaoService cartaoService;

    @Autowired
    public CartaoController(CartaoService cartaoService){
        this.cartaoService = cartaoService;
    }

    @GetMapping
    public List<Cartao> verCartoes(){
        return this.cartaoService.verCartoes();
    }


}
