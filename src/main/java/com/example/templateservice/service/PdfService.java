package com.example.templateservice.service;

import com.example.templateservice.exception.PdfGenerationException;
import lombok.NonNull;

import java.io.OutputStream;

public interface PdfService {
    void htmlToPDf(@NonNull String html,@NonNull OutputStream outputStream)  throws PdfGenerationException;
}
