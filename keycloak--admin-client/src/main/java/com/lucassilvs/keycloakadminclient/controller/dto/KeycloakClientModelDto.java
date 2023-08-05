package com.lucassilvs.keycloakadminclient.controller.dto;

import java.util.List;

public record KeycloakClientModelDto(
        String clientId,
        String clientSecret,
        List<KeycloakRealmRoleModelDto> realmRoles
) {

    public KeycloakClientModelDto(
            String clientId,
            String clientSecret,
            List<KeycloakRealmRoleModelDto> realmRoles) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.realmRoles = realmRoles;
    }
}


