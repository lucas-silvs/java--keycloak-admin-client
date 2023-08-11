package com.lucassilvs.keycloakadminclient.application.exceptions;

import org.springframework.http.HttpStatus;

public class DomainException extends ResponseException{

    public DomainException(String message, HttpStatus statusCode) {
        super(message, LayerExceptionEnum.DOMAIN, statusCode);
    }

}
