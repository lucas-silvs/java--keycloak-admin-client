package com.lucassilvs.keycloakadminclient.controller.impl;

import com.lucassilvs.keycloakadminclient.controller.KeycloakRealmClientController;
import com.lucassilvs.keycloakadminclient.controller.dto.RealmAdminClientDTO;
import com.lucassilvs.keycloakadminclient.services.RealmClientAdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.QueryParam;
import java.util.List;

@RestController
@RequestMapping("/admin/keycloak")
public class KeycloakRealmClientControllerImpl implements KeycloakRealmClientController {

    @Autowired
    private RealmClientAdminServices realmClientAdminServices;

    @PostMapping
    public ResponseEntity<Void> cadastrarRealmAdminClient(@RequestBody RealmAdminClientDTO realmAdminClientDTO) {
        realmClientAdminServices.cadastrarRealmAdminClient(realmAdminClientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Override
    public ResponseEntity<List<RealmAdminClientDTO>> listarRealmAdminClients() {
        return ResponseEntity.ok(realmClientAdminServices.listarRealmAdminClients());
    }

    @GetMapping("/{realm}")
    public ResponseEntity<RealmAdminClientDTO> buscarRealmAdminClient(@PathVariable String realm) {
        return ResponseEntity.ok(realmClientAdminServices.buscarRealmAdminClient(realm));
    }
}
