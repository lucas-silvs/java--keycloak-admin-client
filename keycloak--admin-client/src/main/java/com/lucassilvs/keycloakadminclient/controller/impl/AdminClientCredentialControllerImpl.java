package com.lucassilvs.keycloakadminclient.controller.impl;

import com.lucassilvs.keycloakadminclient.controller.AdminClientCredentialController;
import com.lucassilvs.keycloakadminclient.controller.dto.AdminClientCredentialDTO;
import com.lucassilvs.keycloakadminclient.controller.mapper.AdminClientCredentialMapper;
import com.lucassilvs.keycloakadminclient.services.RealmClientAdminServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/keycloak")
public class AdminClientCredentialControllerImpl implements AdminClientCredentialController {

    private final RealmClientAdminServices realmClientAdminServices;

    private final AdminClientCredentialMapper adminClientCredentialMapper = AdminClientCredentialMapper.INSTANCE;


    @Autowired
    public AdminClientCredentialControllerImpl(RealmClientAdminServices realmClientAdminServices) {
        this.realmClientAdminServices = realmClientAdminServices;
    }

    @PostMapping
    public ResponseEntity<Void> cadastrarRealmAdminClient( @Valid @RequestBody AdminClientCredentialDTO adminClientCredentialDTO) {
        realmClientAdminServices.cadastrarRealmAdminClient(adminClientCredentialMapper.map(adminClientCredentialDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Override
    public ResponseEntity<List<AdminClientCredentialDTO>> listarRealmAdminClients() {
        return ResponseEntity.ok(realmClientAdminServices.listarRealmAdminClients().stream().map(adminClientCredentialMapper::map).toList());
    }

    @GetMapping("/{realm}")
    public ResponseEntity<AdminClientCredentialDTO> buscarRealmAdminClient(@PathVariable String realm) {
        return ResponseEntity.ok(adminClientCredentialMapper.map(realmClientAdminServices.buscarRealmAdminClient(realm)));
    }
}
