package com.lucassilvs.keycloakadminclient.configuration.handler;

import com.lucassilvs.keycloakadminclient.configuration.exceptions.ResponseException;
import org.jboss.resteasy.spi.ApplicationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerComponent {

    @ExceptionHandler(ResponseException.class)
    protected ResponseEntity<ResponseLayerException> responseExceptionHandler(ResponseException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(new ResponseLayerException(ex.getLayerException().getDisplayName(), ex.getMessage()));
    }

}
