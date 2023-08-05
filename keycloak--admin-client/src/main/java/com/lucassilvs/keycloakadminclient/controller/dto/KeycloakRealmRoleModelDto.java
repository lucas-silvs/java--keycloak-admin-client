package com.lucassilvs.keycloakadminclient.controller.dto;

import jakarta.annotation.Nonnull;

public record KeycloakRealmRoleModelDto(
        @Nonnull String nomeRole,
        String descricaoRole,
        String poolId) {

    public KeycloakRealmRoleModelDto(String nomeRole, String descricaoRole, String poolId) {
        this.nomeRole = nomeRole;
        this.descricaoRole = descricaoRole;
        this.poolId = poolId;
    }
}
