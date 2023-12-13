package com.application.service;

import com.application.model.Cartao;
import com.application.model.Pessoa;
import com.application.payload.dto.CartaoDTO;
import com.application.payload.dto.PessoaDTO;
import com.application.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor

@Service
public class CartaoService {

    private final CartaoRepository cartaoRepository;
    private final ModelMapper modelMapper;

    public List<Cartao> verCartoes(){

        return this.cartaoRepository.findAll();

    }
    private CartaoDTO mapTODTO(Cartao cartao){
        return modelMapper.map(cartao, CartaoDTO.class);
    }

    public List<CartaoDTO> salvarCartoes(List<Cartao> cartoes){

        return this.cartaoRepository.saveAll(cartoes).stream()
                .map(this::mapTODTO)
                .toList();

    }

}
