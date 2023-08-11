package com.lucassilvs.keycloakadminclient.infrastructure.configuration.handler;

import com.lucassilvs.keycloakadminclient.application.exceptions.LayerExceptionEnum;
import com.lucassilvs.keycloakadminclient.application.exceptions.ResponseException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerComponent {

    @ExceptionHandler(ResponseException.class)
    protected ResponseEntity<ResponseLayerException> responseExceptionHandler(ResponseException ex) {
        return ResponseEntity.status(ex.getStatusCode()).body(new ResponseLayerException(ex.getLayerException().getDisplayName(), ex.getMessage()));
    }
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ResponseLayerException> bindExceptionHandler(BindException ex) {
        return ResponseEntity.badRequest().body(new ResponseLayerException(LayerExceptionEnum.ENTRYPOINT.getDisplayName() ,ex.toString()));
    }

}
