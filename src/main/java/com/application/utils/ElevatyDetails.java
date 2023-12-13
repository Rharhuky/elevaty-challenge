package com.application.utils;

import java.util.Random;

public final class ElevatyDetails {

    public static final String NOME_EMPRESA = "Elevaty";
    public static final String ENDERECO = "Alphaville - Baruaeri - SP - 06454-000";
    public static final String INSTAGRAM = "elevaty.tech";
    public static final String LINKEDIN = "elevaty-tech";
    public static final String ATENDIMENTO = "www.snxpay.com";

    public static final String CONTATO = String.format("Instagram: %s\nLinkedin: %s\nAtendimento:\n%s",INSTAGRAM, LINKEDIN, ATENDIMENTO);

    private static Random RANDOM_AMOUNT = new Random();

    public static float randomAmount(){
        return RANDOM_AMOUNT.nextFloat(17f, 3001f);
    }



}
