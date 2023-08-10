package com.lucassilvs.keycloakadminclient.controller.impl;

import com.lucassilvs.keycloakadminclient.controller.ClientCredentialController;
import com.lucassilvs.keycloakadminclient.controller.dto.ClientCredentialDTO;
import com.lucassilvs.keycloakadminclient.controller.dto.RealmRoleDTO;
import com.lucassilvs.keycloakadminclient.controller.mapper.ClientCredentialMapper;
import com.lucassilvs.keycloakadminclient.controller.mapper.RealmRoleMapper;
import com.lucassilvs.keycloakadminclient.services.KeycloakAdminServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/keycloak/{realm}")
public class ClientCredentialControllerImpl implements ClientCredentialController {

    private final KeycloakAdminServices keycloakAdminServices;

    private final ClientCredentialMapper clientCredentialMapper = ClientCredentialMapper.INSTANCE;

    private final RealmRoleMapper realmRoleMapper;

    @Autowired
    public ClientCredentialControllerImpl(KeycloakAdminServices keycloakAdminServices, RealmRoleMapper realmRoleMapper) {
        this.keycloakAdminServices = keycloakAdminServices;
        this.realmRoleMapper = realmRoleMapper;
    }

    @PostMapping("client")
    public ResponseEntity<Void> criarClientCredentials(@PathVariable(name = "realm") String realm, @RequestBody @Valid ClientCredentialDTO clientCredentialDTO) {
        keycloakAdminServices.criarClientCredentials(realm, clientCredentialMapper.map(clientCredentialDTO));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<ClientCredentialDTO> buscarClientCredentials(@PathVariable(name = "realm") String realm, @PathVariable(name = "clientId") String clientId) {
        return ResponseEntity.ok(clientCredentialMapper.map(keycloakAdminServices.buscarClientCredentials(realm, clientId)));
    }

    @PutMapping("/client/{clientId}/role/{nomeRole}")
    public ResponseEntity<Void> atribuiRealmRoleAoClient(@PathVariable(name = "realm") String realm, @PathVariable(name = "clientId") String clientId, @PathVariable(name = "nomeRole")String nomeRole) {
        keycloakAdminServices.atribuiRealmRoleAoClient(realm, clientId, nomeRole);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/role")
    public ResponseEntity<Void> criarRealmRole(@PathVariable(name = "realm") String realm, @RequestBody @Valid RealmRoleDTO realmRoleDTO) {
        keycloakAdminServices.criarRealmRole(realm, realmRoleMapper.map(realmRoleDTO));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/role/{nomeRole}")
    public ResponseEntity<RealmRoleDTO> buscaRealmRole(@PathVariable(name = "realm") String realm, @PathVariable(name = "nomeRole") String nomeRole) {
        return ResponseEntity.ok(realmRoleMapper.map(keycloakAdminServices.buscaRealmRole(realm, nomeRole)));
    }

}
