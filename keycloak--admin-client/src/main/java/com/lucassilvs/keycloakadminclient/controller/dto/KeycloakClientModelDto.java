package com.lucassilvs.keycloakadminclient.controller.dto;

import jakarta.annotation.Nonnull;

import java.util.List;

public record KeycloakClientModelDto(
        @Nonnull
        String clientId,
        String clientSecret,
        List<KeycloakRealmRoleModelDto> realmRoles
) {

    public KeycloakClientModelDto(String clientId, String clientSecret, List<KeycloakRealmRoleModelDto> realmRoles) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.realmRoles = realmRoles;
    }
}


