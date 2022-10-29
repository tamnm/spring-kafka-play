package com.example.templateservice.exception;

public class TemplateGeneratingException extends Exception {
    public TemplateGeneratingException(String message) {
        super(message);
    }

    public TemplateGeneratingException(String message, Throwable cause) {
        super(message, cause);
    }
}
