package com.application.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TemplatePessoa {

    private String firstname;
    private String lastname;
    private String birthday;
    private String email;
    private String phone;
    private String gender;
    private TemplateEndereco address;

}
