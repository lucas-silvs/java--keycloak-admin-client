package com.lucassilvs.keycloakadminclient.controller.dto;

import jakarta.annotation.Nonnull;

public class KeycloakRealmRoleModelDto {

    @Nonnull
    private final String nomeRole;

    private final String descricaoRole;

    public KeycloakRealmRoleModelDto(String nomeRole, String descricaoRole) {
        this.nomeRole = nomeRole;
        this.descricaoRole = descricaoRole;
    }
    @Nonnull
    public String getNomeRole() {
        return nomeRole;
    }

    public String getDescricaoRole() {
        return descricaoRole;
    }
}
