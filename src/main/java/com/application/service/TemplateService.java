package com.application.service;

import com.application.model.Cartao;
import com.application.model.Endereco;
import com.application.model.Pessoa;
import com.application.payload.TemplateCartao;
import com.application.payload.TemplatePessoa;
import com.application.payload.dto.CartaoDTO;
import com.application.payload.dto.PessoaDTO;
import com.application.utils.Constantes;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.scanner.Constant;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@RequiredArgsConstructor

@Service
public class TemplateService {

    private final PessoaService pessoaService;
    private final EnderecoService enderecoService;
    private final CartaoService cartaoService;
    private final ModelMapper modelMapper;
    private String seed;


    public List<PessoaDTO> handleTemplatePessoaWithEndereco(List<TemplatePessoa> templatePessoa) {

        List<Pessoa> pessoas =

                templatePessoa.stream().map((pessoa) -> {

                    Endereco novoEndereco = modelMapper.map(pessoa.getAddress(), Endereco.class);
                    Pessoa novaPessoa = modelMapper.map(pessoa, Pessoa.class);

                    novaPessoa.setEndereco(novoEndereco);
                    novoEndereco.setPessoa(novaPessoa);

                    return novaPessoa;
                }).toList();

        return pessoaService.salvarPessoas(pessoas);

    }

    public List<CartaoDTO> handleTemplateCartao(List<TemplateCartao> templateCartao){

        List<CartaoDTO> cartoes =
                templateCartao.stream()
                        .map((cartao) -> modelMapper.map(cartao, CartaoDTO.class))
                        .toList();

        return cartoes;
    }

    public PessoaDTO verDetalhes(List<CartaoDTO> cartaoDTOS, String emailPessoa){
        PessoaDTO pessoaDTO = this.pessoaService.verPessoaPeloEmail(emailPessoa);
        pessoaDTO.setCartoes(cartaoDTOS);
        return pessoaDTO;
    }

    public String requestPersonsURL(String dataInicio, String dataFim){

        return String.format(Constantes.URL_PESSOAS + "?_birthday_start=%s&_birthday_end=%s",dataInicio, dataFim);

    }

    public String requestCardsURL(String numeroCartoes){
        if(Objects.isNull(seed)){
            seed = String.valueOf(new Random().nextInt(1, 30));
        }

        return String.format(Constantes.URL_CARTOES + "?_quantity=%s&_seed=%s", numeroCartoes, seed);

    }

    public List<PessoaDTO> listarPessoas(){
        return this.pessoaService.verTodasPessoas();
    }

}
