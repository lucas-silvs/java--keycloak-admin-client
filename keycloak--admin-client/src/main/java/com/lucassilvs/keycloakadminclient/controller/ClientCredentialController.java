package com.lucassilvs.keycloakadminclient.controller;

import com.lucassilvs.keycloakadminclient.controller.dto.ClientCredentialDTO;
import com.lucassilvs.keycloakadminclient.controller.dto.RealmRoleDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface ClientCredentialController {

    ResponseEntity<Void> criarClientCredentials(String realm, ClientCredentialDTO clientCredentialDTO);

    ResponseEntity<ClientCredentialDTO> buscarClientCredentials(String realm, String clientId);

    ResponseEntity<Void> criarRealmRole(String realm, RealmRoleDTO realmRoleDTO);

    ResponseEntity<RealmRoleDTO> buscaRealmRole(String realm, String nomeRole);

    ResponseEntity<Void> atribuiRealmRoleAoClient(String realm, String clientId, String nomeRole);

}
