package com.lucassilvs.keycloakadminclient.controller.impl;

import com.lucassilvs.keycloakadminclient.controller.KeycloakAdminController;
import com.lucassilvs.keycloakadminclient.controller.dto.KeycloakClientModelDto;
import com.lucassilvs.keycloakadminclient.controller.dto.KeycloakRealmRoleModelDto;
import com.lucassilvs.keycloakadminclient.services.KeycloakAdminServices;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/keycloak/{realm}")
public class KeycloakAdminControllerImpl implements KeycloakAdminController {

    private final KeycloakAdminServices keycloakAdminServices;

    public KeycloakAdminControllerImpl(KeycloakAdminServices keycloakAdminServices) {
        this.keycloakAdminServices = keycloakAdminServices;
    }

    @PostMapping("client")
    public ResponseEntity<Void> criarClientCredentials(@PathVariable(name = "realm") String realm, @RequestBody KeycloakClientModelDto keycloakClientModelDto) {
        try {
            keycloakAdminServices.criarClientCredentials(realm, keycloakClientModelDto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<KeycloakClientModelDto> buscarClientCredentials(@PathVariable(name = "realm") String realm, @PathVariable(name = "clientId") String clientId) {
        try {
            KeycloakClientModelDto keycloakClientModelDto = keycloakAdminServices.buscarClientCredentials(realm, clientId);
            return ResponseEntity.ok(keycloakClientModelDto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping("/client/{clientId}/role/{nomeRole}")
    public ResponseEntity<Void> atribuiRealmRoleAoClient(@PathVariable(name = "realm") String realm, @PathVariable(name = "clientId") String clientId, @PathVariable(name = "nomeRole")String nomeRole) {

        try {
            keycloakAdminServices.atribuiRealmRoleAoClient(realm, clientId, nomeRole);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }

    }


    @PostMapping("/role")
    public ResponseEntity<Void> criarRealmRole(@PathVariable(name = "realm") String realm, @RequestBody KeycloakRealmRoleModelDto keycloakRealmRoleModelDto) {
        try {
            keycloakAdminServices.criarRealmRole(realm, keycloakRealmRoleModelDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/role/{nomeRole}")
    public ResponseEntity<KeycloakRealmRoleModelDto> buscaRealmRole(@PathVariable(name = "realm") String realm, @PathVariable(name = "nomeRole") String nomeRole) {
        try {
            KeycloakRealmRoleModelDto keycloakRealmRoleModelDto = keycloakAdminServices.buscaRealmRole(realm, nomeRole);
            return ResponseEntity.ok(keycloakRealmRoleModelDto);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
