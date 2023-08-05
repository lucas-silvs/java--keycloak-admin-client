package com.lucassilvs.keycloakadminclient.configuration.handler;

public class ResponseLayerException {

    private String layer;
    private String message;

    public ResponseLayerException(String layer, String message) {
        this.layer = layer;
        this.message = message;
    }

    public String getLayer() {
        return layer;
    }

    public String getMessage() {
        return message;
    }
}
