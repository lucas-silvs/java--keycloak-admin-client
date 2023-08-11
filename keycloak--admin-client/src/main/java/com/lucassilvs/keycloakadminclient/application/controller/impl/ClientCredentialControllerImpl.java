package com.lucassilvs.keycloakadminclient.application.controller.impl;

import com.lucassilvs.keycloakadminclient.application.controller.ClientCredentialController;
import com.lucassilvs.keycloakadminclient.application.controller.dto.ClientCredentialDTO;
import com.lucassilvs.keycloakadminclient.application.controller.dto.RealmRoleDTO;
import com.lucassilvs.keycloakadminclient.application.controller.mapper.ClientCredentialMapper;
import com.lucassilvs.keycloakadminclient.application.controller.mapper.RealmRoleMapper;
import com.lucassilvs.keycloakadminclient.domain.ports.interfaces.ClientCredentialPorts;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/keycloak/{realm}")
public class ClientCredentialControllerImpl implements ClientCredentialController {

    private final ClientCredentialPorts clientCredentialPorts;

    private final ClientCredentialMapper clientCredentialMapper = ClientCredentialMapper.INSTANCE;

    private final RealmRoleMapper realmRoleMapper;

    @Autowired
    public ClientCredentialControllerImpl(ClientCredentialPorts clientCredentialPorts, RealmRoleMapper realmRoleMapper) {
        this.clientCredentialPorts = clientCredentialPorts;
        this.realmRoleMapper = realmRoleMapper;
    }

    @PostMapping("client")
    public ResponseEntity<Void> criarClientCredentials(@PathVariable(name = "realm") String realm, @RequestBody @Valid ClientCredentialDTO clientCredentialDTO) {
        clientCredentialPorts.criarClientCredentials(realm, clientCredentialMapper.map(clientCredentialDTO));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<ClientCredentialDTO> buscarClientCredentials(@PathVariable(name = "realm") String realm, @PathVariable(name = "clientId") String clientId) {
        return ResponseEntity.ok(clientCredentialMapper.map(clientCredentialPorts.buscarClientCredentials(realm, clientId)));
    }

    @PutMapping("/client/{clientId}/role/{nomeRole}")
    public ResponseEntity<Void> atribuiRealmRoleAoClient(@PathVariable(name = "realm") String realm, @PathVariable(name = "clientId") String clientId, @PathVariable(name = "nomeRole")String nomeRole) {
        clientCredentialPorts.atribuiRealmRoleAoClient(realm, clientId, nomeRole);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/role")
    public ResponseEntity<Void> criarRealmRole(@PathVariable(name = "realm") String realm, @RequestBody @Valid RealmRoleDTO realmRoleDTO) {
        clientCredentialPorts.criarRealmRole(realm, realmRoleMapper.map(realmRoleDTO));
        return ResponseEntity.ok().build();
    }

    @GetMapping("/role/{nomeRole}")
    public ResponseEntity<RealmRoleDTO> buscaRealmRole(@PathVariable(name = "realm") String realm, @PathVariable(name = "nomeRole") String nomeRole) {
        return ResponseEntity.ok(realmRoleMapper.map(clientCredentialPorts.buscaRealmRole(realm, nomeRole)));
    }

}
