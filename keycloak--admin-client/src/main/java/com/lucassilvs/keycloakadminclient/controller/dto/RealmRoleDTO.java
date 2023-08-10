package com.lucassilvs.keycloakadminclient.controller.dto;

import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.Map;

public record RealmRoleDTO(
        @Nonnull String nome,
        String descricao,
        Map<String, List<String>> atributos
){}