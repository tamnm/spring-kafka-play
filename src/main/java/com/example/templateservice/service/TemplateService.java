package com.example.templateservice.service;

import com.example.templateservice.exception.TemplateGeneratingException;
import com.example.templateservice.exception.TemplateLoadingException;
import lombok.NonNull;

import java.io.OutputStream;
import java.util.Map;

public interface TemplateService {
    void addTemplate(@NonNull String templateId, @NonNull String templateText) throws TemplateLoadingException;
    void generateText(@NonNull String templateId, @NonNull Map<String, Object> input, @NonNull OutputStream outputStream) throws TemplateGeneratingException;

    String getTemplate(@NonNull String templateId);
}
