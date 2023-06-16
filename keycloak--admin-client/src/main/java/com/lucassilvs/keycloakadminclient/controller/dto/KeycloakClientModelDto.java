package com.lucassilvs.keycloakadminclient.controller.dto;

import jakarta.annotation.Nonnull;

public class KeycloakClientModelDto {

    @Nonnull
    private final String clientId;

    private final String clientSecret;

    public KeycloakClientModelDto(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}
