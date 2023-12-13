package com.application.payload.request;

import com.application.payload.TemplatePessoa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TemplateRequestPessoa {

    private String status;
    private int code;
    private String total;
    private List<TemplatePessoa> data;
}
