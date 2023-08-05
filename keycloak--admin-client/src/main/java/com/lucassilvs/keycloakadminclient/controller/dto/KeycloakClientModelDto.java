package com.lucassilvs.keycloakadminclient.controller.dto;

import java.util.List;

public record KeycloakClientModelDto(
        String clientId,
        String clientSecret,
        List<KeycloakRealmRoleModelDto> realmRoles,
        String roleName
) {

    public KeycloakClientModelDto(
            String clientId,
            String clientSecret,
            List<KeycloakRealmRoleModelDto> realmRoles,
            String roleName) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.realmRoles = realmRoles;
        this.roleName = roleName;
    }
}


