package com.lucassilvs.keycloakadminclient.application.exceptions;

import org.springframework.http.HttpStatus;

public class ApplicationException extends ResponseException {
    public ApplicationException(String message, HttpStatus statusCode) {
        super(message, LayerExceptionEnum.APPLICATION, statusCode);
    }
}
