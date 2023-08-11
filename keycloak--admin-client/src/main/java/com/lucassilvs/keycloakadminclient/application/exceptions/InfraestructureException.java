package com.lucassilvs.keycloakadminclient.application.exceptions;

import org.springframework.http.HttpStatus;

public class InfraestructureException extends ResponseException{
    public InfraestructureException(String message, HttpStatus statusCode) {
        super(message, LayerExceptionEnum.INFRASTRUCTURE, statusCode);
    }
}
