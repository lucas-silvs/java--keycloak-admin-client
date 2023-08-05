package com.lucassilvs.keycloakadminclient.configuration.exceptions;

import org.springframework.http.HttpStatus;

public class EntrypointException extends ResponseException{
    public EntrypointException(String message, HttpStatus statusCode){
        super(message, LayerExceptionEnum.ENTRYPOINT, statusCode);
    }
}
