package com.lucassilvs.keycloakadminclient.controller.dto;

import jakarta.annotation.Nonnull;

import java.util.List;

public class KeycloakClientModelDto {

    @Nonnull
    private final String clientId;

    private final String clientSecret;

    private List<KeycloakRealmRoleModelDto> realmRoles;


    public KeycloakClientModelDto(String clientId, String clientSecret, List<KeycloakRealmRoleModelDto> realmRoles) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.realmRoles = realmRoles;
    }

    public KeycloakClientModelDto( String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public List<KeycloakRealmRoleModelDto> getRealmRoles() {
        return realmRoles;
    }

    public void setRealmRoles(List<KeycloakRealmRoleModelDto> realmRoles) {
        this.realmRoles = realmRoles;
    }



    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }
}
