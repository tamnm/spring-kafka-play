package com.example.templateservice.exception;

public class TemplateLoadingException extends Exception{
    public TemplateLoadingException(String message) {
        super(message);
    }

    public TemplateLoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
