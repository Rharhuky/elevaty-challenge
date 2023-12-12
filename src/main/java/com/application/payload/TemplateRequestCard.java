package com.application.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TemplateRequestCard {

    private String status;
    private int code;
    private String total;
    private List<TemplateCartao> data;
}
