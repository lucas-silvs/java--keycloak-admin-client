package com.lucassilvs.keycloakadminclient.controller.dto;

import jakarta.annotation.Nonnull;

public class KeycloakRealmRoleModelDto {

    @Nonnull
    private final String nomeRole;

    private final String descricaoRole;

    private final String poolId;

    public KeycloakRealmRoleModelDto(String nomeRole, String descricaoRole, String poolId) {
        this.nomeRole = nomeRole;
        this.descricaoRole = descricaoRole;
        this.poolId = poolId;
    }
    @Nonnull
    public String getNomeRole() {
        return nomeRole;
    }

    public String getDescricaoRole() {
        return descricaoRole;
    }

    public String getPoolId() {
        return poolId;
    }
}
