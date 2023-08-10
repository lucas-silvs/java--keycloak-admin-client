package com.lucassilvs.keycloakadminclient.services.model;


import java.util.List;
import java.util.Map;

public record RealmRole(

        String nome,
        String descricao,

        Map<String, List<String>> atributos

) {
}
