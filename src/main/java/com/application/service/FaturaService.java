package com.application.service;

import com.application.configuracao.ConfiguracaoFatura;
import com.application.exceptions.EmailInexistenteException;
import com.application.model.Pessoa;
import com.application.payload.dto.PessoaDTO;
import com.application.utils.Constantes;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        Path filePath = fileStorageLocation.resolve(Paths.get("faturas/newFatura.pdf").toAbsolutePath()).normalize();
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
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; Nome do Arquivo=\"" + resource.getFilename() + "\"")
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
            document.save("faturas/newFatura.pdf");

        document.close();

    }


    public boolean preencherFatura(PDAcroForm acroForm, PessoaDTO pessoaDTO) throws IOException {

        if(! Objects.isNull(acroForm)){

            acroForm.getField("Your company name").setValue("Elevaty");
            acroForm.getField("Comapny address").setValue("Alphaville - Baruaeri - SP - 06454-000");
            acroForm.getField("Company contact details").setValue("www.instagram.com/elevaty.tech\nhttps://www.linkedin.com/company/elevaty-tech/");
            acroForm.getField("Billing to").setValue(pessoaDTO.getEmail());
            acroForm.getField("Invoice Date:").setValue("rapaz n funciona");
            acroForm.getField("Invoice number").setValue("00001");
            return true;

        }

        return false;
    }

}

