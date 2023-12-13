package com.application.configuracao;

import com.application.model.Endereco;
import com.application.model.Pessoa;
import com.application.payload.TemplateCartao;
import com.application.payload.TemplateEndereco;
import com.application.payload.TemplatePessoa;
import com.application.payload.dto.CartaoDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfiguracaoModelMapper {

    /**
     * Esse Bean serve para configurar o ModelMapper.
     * Além disso, possui lógicas de configurações para mapeamento entre algumas
     * entidades como {@link Pessoa} e {@link TemplatePessoa}.
     * @return {@link ModelMapper}
     */
    @Bean
    public ModelMapper modelMapper(){

        ModelMapper newModelMapper = new ModelMapper();
        newModelMapper.addMappings(new PropertyMap<TemplatePessoa, Pessoa>() {

            @Override
            protected void configure() {

                //acoplamento GENERO e ModelMapper - nao é legal
                map().setGenero(source.getGender());
                map().setNascimento(source.getBirthday());
                map().setSobrenome(source.getLastname());
                map().setNome(source.getFirstname());
                map().setCelular(source.getPhone());
                map().setEmail(source.getEmail());

            }
        });

        newModelMapper.addMappings(new PropertyMap<TemplateEndereco, Endereco>() {

            @Override
            protected void configure() {

                map().setCountyCode(source.getCounty_code());
                map().setNumero(source.getBuildingNumber());
                map().setLongitude(source.getLongitude());
                map().setNomeRua(source.getStreetName());
                map().setLatitude(source.getLatitude());
                map().setZipCode(source.getZipcode());
                map().setPais(source.getCountry());
                map().setCidade(source.getCity());
                map().setRua(source.getStreet());

            }
        });


        newModelMapper.addMappings(new PropertyMap<TemplateCartao, CartaoDTO>() {

            @Override
            protected void configure() {

                map().setDataVencimento(source.getExpiration());
                map().setTitular(source.getOwner());
                map().setNumero(source.getNumber());
                map().setTipo(source.getType());

            }

        });

        return newModelMapper;

    }

}
