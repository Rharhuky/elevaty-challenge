package com.application.service;

import com.application.exceptions.EmailInexistenteException;
import com.application.model.Pessoa;
import com.application.payload.TemplatePessoa;
import com.application.payload.dto.PessoaDTO;
import com.application.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor

@Service
public class PessoaService {

    private final ModelMapper modelMapper;
    private final PessoaRepository pessoaRepository;

    public List<PessoaDTO> salvarPessoas(List<Pessoa> pessoas){

        return this.pessoaRepository.saveAll(pessoas)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<Pessoa> mapToPessoa(List<TemplatePessoa> templatePessoa){

        return templatePessoa
                .stream()
                .map((template) -> modelMapper.map(template, Pessoa.class))
                .toList();

    }

    public PessoaDTO verPessoaPeloEmail(String email) throws EmailInexistenteException {
        Pessoa pessoa = this.pessoaRepository.findByEmail(email);
        if(Objects.isNull(pessoa))
            throw new EmailInexistenteException(email);
        return mapTODTO(pessoa);
    }

    public PessoaDTO mapToDTO(Pessoa pessoa){
        return modelMapper.map(pessoa, PessoaDTO.class);
    }

    public PessoaDTO mapTODTO(Pessoa pessoa) throws IllegalArgumentException{
        return modelMapper.map(pessoa, PessoaDTO.class);
    }
    public List<PessoaDTO> verTodasPessoas(int numeroPagina, int tamanhoPagina){

        //Pageable paginacao = PageRequest.of(numeroPagina, tamanhoPagina);

        return this.pessoaRepository.findAll().stream().map(this::mapToDTO).toList();
    }

}
