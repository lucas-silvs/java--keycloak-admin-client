package com.lucassilvs.keycloakadminclient.services;

import com.lucassilvs.keycloakadminclient.controller.dto.KeycloakClientModelDto;
import com.lucassilvs.keycloakadminclient.controller.dto.KeycloakRealmRoleModelDto;


public interface KeycloakAdminServices {

    void criarClientCredentials(String realm, KeycloakClientModelDto keycloakClientModelDto);

    KeycloakClientModelDto buscarClientCredentials(String realm, String clientId);

    void criarRealmRole(String realm, KeycloakRealmRoleModelDto keycloakRealmRoleModelDto);

    KeycloakRealmRoleModelDto buscaRealmRole(String realm, String nomeRole);

    void atribuiRealmRoleAoClient(String realm, String clientId, String nomeRole);
}
