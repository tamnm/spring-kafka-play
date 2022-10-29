package com.example.templateservice.controller;

import com.example.templateservice.exception.TemplateLoadingException;
import com.example.templateservice.http.StreamResponse;
import com.example.templateservice.service.PdfService;
import com.example.templateservice.service.TemplateService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("template")
public class TemplateController {
    private final TemplateService templateService;
    private final PdfService pdfService;

    public TemplateController(TemplateService templateService, PdfService pdfService) {
        this.templateService = templateService;
        this.pdfService = pdfService;
    }

    @PostMapping(path = "{templateId}")
    public ResponseEntity<String> saveTemplate(@PathVariable("templateId") String templateId, @RequestBody String template) throws TemplateLoadingException {
        templateService.addTemplate(templateId, template);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "{templateId}")
    public ResponseEntity<String> getTemplate(@PathVariable("templateId") String templateId)  {
        var template = templateService.getTemplate(templateId);

        if(template== null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(template);
        }
    }

    @PostMapping(path = "{templateId}/generate", produces = "application/json")
    public ResponseEntity<StreamResponse> generate(@PathVariable("templateId") String  templateId, @RequestBody Map<String, Object> variables) {
        final StreamResponse streamResponse = new StreamResponse(os -> {
            templateService.generateText(templateId, variables, os);
        });

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);

        return new ResponseEntity<>(streamResponse, headers, HttpStatus.OK);
    }

    @PostMapping(path = "{templateId}/generate-pdf", produces = "application/json")
    public ResponseEntity<StreamResponse> generatePdf(@PathVariable("templateId") String  templateId, @RequestBody Map<String, Object> variables) {
        final StreamResponse streamResponse = new StreamResponse(outputStream -> {
            try(var htmlTemplateOS =  new ByteArrayOutputStream()){
                templateService.generateText(templateId, variables, htmlTemplateOS);
                pdfService.htmlToPDf(htmlTemplateOS.toString(StandardCharsets.UTF_8), outputStream);
            }
        });

        var headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        return new ResponseEntity<>(streamResponse, headers, HttpStatus.OK);
    }
}
