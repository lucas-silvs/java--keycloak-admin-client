package com.lucassilvs.keycloakadminclient.application.controller.dto;

import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ClientCredentialDTO(
        @NotBlank String clientId,
        String clientSecret,
        List<RealmRoleDTO> realmRoles,
        String roleIamName
) {}


