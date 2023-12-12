package com.application.controller;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
public class ControllerTestes {

    private static final String PATH ="faturas/fatura.pdf";
    //"@~/Fatura Elevaty.pdf";
    @GetMapping("/novoPDF")
    public void novoPdf() {

        try (PDDocument document = PDDocument.load(new File(PATH))) {
            PDDocumentCatalog catalog = document.getDocumentCatalog();
            PDAcroForm acroForm = catalog.getAcroForm();

            if (acroForm != null) {

                acroForm.getField("Billing to").setValue("Jovial");
                for (PDField field : acroForm.getFields()) {
                    System.out.println("Nome do Campo: " + field.getFullyQualifiedName());
                }
                document.save("faturas/fatura.pdf");
            } else {
                System.out.println("O PDF não possui campos de formulário editáveis.");
            }

            }

        catch(Exception e){
                e.printStackTrace();
            }
        }
    }
