package com.lucassilvs.keycloakadminclient.controller;

import com.lucassilvs.keycloakadminclient.controller.dto.KeycloakClientModelDto;
import com.lucassilvs.keycloakadminclient.controller.dto.KeycloakRealmRoleModelDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface KeycloakAdminController {

    ResponseEntity<Void> criarClientCredentials(String realm, KeycloakClientModelDto keycloakClientModelDto);

    ResponseEntity<KeycloakClientModelDto> buscarClientCredentials(String realm, String clientId);

    ResponseEntity<Void> criarRealmRole(String realm, KeycloakRealmRoleModelDto keycloakRealmRoleModelDto);

    ResponseEntity<KeycloakRealmRoleModelDto> buscaRealmRole(String realm, String nomeRole);

    ResponseEntity<Void> atribuiRealmRoleAoClient(String realm, String clientId, String nomeRole);

}
