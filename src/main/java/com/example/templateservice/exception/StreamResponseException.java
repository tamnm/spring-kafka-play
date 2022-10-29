package com.example.templateservice.exception;

public class StreamResponseException extends Exception {
    public StreamResponseException(String message) {
        super(message);
    }

    public StreamResponseException(String message, Throwable cause) {
        super(message, cause);
    }
}
