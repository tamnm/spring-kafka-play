package com.example.templateservice.service;

import com.example.templateservice.exception.TemplateGeneratingException;
import com.example.templateservice.exception.TemplateLoadingException;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TemplateServiceImpl implements TemplateService {
    private final TemplateEngine templateEngine;
    private final ConcurrentHashMap<String,String> templates = new ConcurrentHashMap<>();

    public TemplateServiceImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Override
    public void addTemplate(@NonNull String templateId,@NonNull String templateText) throws TemplateLoadingException {

        templates.put(templateId, templateText);
    }

    @Override
    public void generateText(@NonNull String templateId, @NonNull Map<String, Object> input, @NonNull OutputStream outputStream) throws TemplateGeneratingException {
        try {
            var template = templates.getOrDefault(templateId, null);
            if(template== null) throw new TemplateGeneratingException("Template was not found");

            var context = new Context();
            context.setVariables(input);

            try(var writer =new OutputStreamWriter(outputStream)){
                templateEngine.process(template, context, writer);
            }
        } catch (IOException e) {
            throw new TemplateGeneratingException(e.getMessage(), e);
        }
    }

    @Override
    public String getTemplate(@NonNull String templateId) {
        return templates.getOrDefault(templateId, null);
    }
}
