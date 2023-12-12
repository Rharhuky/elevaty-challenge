package com.application.service;

import com.application.model.Endereco;
import com.application.payload.dto.EnderecoDTO;
import com.application.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor

@Service

public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final ModelMapper modelMapper;
    public List<EnderecoDTO> verEnderecos(){

        return this.enderecoRepository.findAll().stream().map(this::mapToDTO).toList();

    }

    private EnderecoDTO mapToDTO(Endereco endereco){

        return modelMapper.map(endereco, EnderecoDTO.class);

    }


}
