package com.application.service;

import com.application.configuracao.ConfiguracaoFatura;
import com.application.payload.dto.PessoaDTO;
import com.application.utils.Constantes;
import com.application.utils.ElevatyDetails;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;


@Service
public class FaturaService {

    private final PessoaService pessoaService;
    private final Path fileStorageLocation;

    public FaturaService(ConfiguracaoFatura fileStorageLocation, PessoaService pessoaService) {
        this.fileStorageLocation = Paths.get(fileStorageLocation.getDownloadDirectory()).toAbsolutePath().normalize();
        this.pessoaService = pessoaService;

    }

    public ResponseEntity<Resource> obterPDF(HttpServletRequest servletRequest) throws FileNotFoundException {

        Path filePath = fileStorageLocation.resolve(Paths.get(Constantes.DEFAULT_NEW_FATURA_PDF_PATH).toAbsolutePath()).normalize();
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException();
        }

        try {
            Resource resource = new UrlResource(filePath.toUri());
            String contentType = servletRequest.getServletContext().getMimeType(resource.getFile().getAbsolutePath());

            if (Objects.isNull(contentType))
                contentType = "application/octet-stream";

            MediaType mediaType = MediaType.parseMediaType(contentType);
            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + "newInvoice" + "\"")
                    .body(resource);


        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void gerarPDF(String email) throws IOException {

        PessoaDTO pessoa = this.pessoaService.verPessoaPeloEmail(email);
        PDDocument document = PDDocument.load(new File(Constantes.DEFAULT_FATURA_PDF_PATH));
        PDAcroForm acroForm =  document.getDocumentCatalog().getAcroForm();  //catalog.getAcroForm();

        if(preencherFatura(acroForm, pessoa))
            document.save(Constantes.DEFAULT_NEW_FATURA_PDF_PATH);

        document.close();

    }


    public boolean preencherFatura(PDAcroForm acroForm, PessoaDTO pessoaDTO) throws IOException {

        if(! Objects.isNull(acroForm)){

            acroForm.getField("Your company name").setValue         (ElevatyDetails.NOME_EMPRESA);
            acroForm.getField("Comapny address").setValue           (ElevatyDetails.ENDERECO);
            acroForm.getField("Company contact details").setValue   (ElevatyDetails.CONTATO);

            acroForm.getField("Billing to").setValue                (pessoaDTO.toString());
            acroForm.getField("Invoice Date:").setValue             ("0001");
            acroForm.getField("Invoice number").setValue(LocalDate.now().toString());

            acroForm.getField("Your item name").setValue("Notebook");
            acroForm.getField("Your item name 2").setValue("AMERICANAS");
            acroForm.getField("Your item name 3").setValue("Spring Boot Course - Udemy");
            acroForm.getField("Your item name 4").setValue("I WANT this oportunity");
            acroForm.getField("Your item name 5").setValue("KeyBoard");
            acroForm.getField("Your item name 6").setValue("Air conditioning workshop");
            acroForm.getField("Your item name 7").setValue("BigBomPreco");
            acroForm.getField("Your item name 8").setValue("PagSeguro");

            float invoiceTotal = 0;
            float currentValue;
            String cost;

            for(int i = 1; i < 9; i ++){

                currentValue = ElevatyDetails.randomAmount();
                cost = String.format("$%6.2f", currentValue);

                acroForm.getField("Unit cost "+i).setValue(cost);
                acroForm.getField("Amount "+i).setValue(cost);

                invoiceTotal += currentValue;
            }

            acroForm.getField("Subtotal").setValue(String.format("%.2f", invoiceTotal));
            acroForm.getField("Total").setValue(String.format("%.2f", invoiceTotal));
            return true;

        }

        return false;
    }

}

