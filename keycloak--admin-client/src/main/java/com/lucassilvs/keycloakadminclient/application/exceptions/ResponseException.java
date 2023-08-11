package com.lucassilvs.keycloakadminclient.application.exceptions;

import org.springframework.http.HttpStatus;

public abstract class ResponseException extends RuntimeException{

    private final HttpStatus statusCode;
    private final String message;

    private final LayerExceptionEnum layerException;


    protected ResponseException(String message, LayerExceptionEnum layerException, HttpStatus statusCode) {
        this.message = message;
        this.layerException = layerException;
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public LayerExceptionEnum getLayerException() {
        return layerException;
    }
}
