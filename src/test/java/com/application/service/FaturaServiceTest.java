package com.application.service;

import com.application.payload.dto.PessoaDTO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class FaturaServiceTest {

    private static PDDocument document;
    private static PessoaDTO pessoaDTOTest;

    @BeforeAll
    static void setUp() throws IOException {
        document = PDDocument.load(new File("faturas\\fatura.pdf"));
        pessoaDTOTest = new PessoaDTO();
        pessoaDTOTest.setEmail("Jovem@mail");
    }

    @Test
    void gerarPDF() {

        PDDocument newDocument;
        String email = "jovem";
        try {

            newDocument = PDDocument.load(new File("faturas\\fatura.pdf"));
            PDDocumentCatalog catalog = document.getDocumentCatalog();
            PDAcroForm acroForm = catalog.getAcroForm();

            if (acroForm != null) {

                acroForm.getField("Billing to").setValue("TuDUUUUU");
                for (PDField field : acroForm.getFields()) {
                    System.out.println("Nome do Campo: " + field.getFullyQualifiedName() + field.getValueAsString());
                }
                //document.save("faturas/newDocument.pdf");
            } else {
                // FIXME using CustomException
                System.out.println("O PDF não possui campos de formulário editáveis.");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        assertNotEquals(document, newDocument," Should be not the same");
    }

    @Test
    void preencherFatura() throws IOException {

        PDAcroForm pdAcroForm = document.getDocumentCatalog().getAcroForm();

        pdAcroForm.getField("Your company name").setValue("Elevaty");
        pdAcroForm.getField("Comapny address").setValue("Alphaville - Baruaeri - SP - 06454-000");
        pdAcroForm.getField("Company contact details").setValue("www.instagram.com/elevaty.tech\nhttps://www.linkedin.com/company/elevaty-tech/");
        pdAcroForm.getField("Billing to").setValue(pessoaDTOTest.getEmail() + "\n" + "hehe");
        pdAcroForm.getField("Invoice Date:").setValue("rapaz n funciona");
        pdAcroForm.getField("Invoice number").setValue("00001");
        document.save("faturas/newDocument.pdf");

        assertEquals("Elevaty", document.getDocumentCatalog().getAcroForm().getField("Your company name").getValueAsString(), "Should be equals bro");


    }

}