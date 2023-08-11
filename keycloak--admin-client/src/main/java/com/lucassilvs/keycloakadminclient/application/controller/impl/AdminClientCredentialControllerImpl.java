package com.lucassilvs.keycloakadminclient.application.controller.impl;

import com.lucassilvs.keycloakadminclient.application.controller.AdminClientCredentialController;
import com.lucassilvs.keycloakadminclient.application.controller.dto.AdminClientCredentialDTO;
import com.lucassilvs.keycloakadminclient.application.controller.mapper.AdminClientCredentialMapper;
import com.lucassilvs.keycloakadminclient.domain.ports.interfaces.RealmClientAdminPort;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/keycloak")
public class AdminClientCredentialControllerImpl implements AdminClientCredentialController {

    private final RealmClientAdminPort realmClientAdminPort;

    private final AdminClientCredentialMapper adminClientCredentialMapper = AdminClientCredentialMapper.INSTANCE;


    @Autowired
    public AdminClientCredentialControllerImpl(RealmClientAdminPort realmClientAdminPort) {
        this.realmClientAdminPort = realmClientAdminPort;
    }

    @PostMapping
    public ResponseEntity<Void> cadastrarRealmAdminClient( @Valid @RequestBody AdminClientCredentialDTO adminClientCredentialDTO) {
        realmClientAdminPort.cadastrarRealmAdminClient(adminClientCredentialMapper.map(adminClientCredentialDTO));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    @Override
    public ResponseEntity<List<AdminClientCredentialDTO>> listarRealmAdminClients() {
        return ResponseEntity.ok(realmClientAdminPort.listarRealmAdminClients().stream().map(adminClientCredentialMapper::map).toList());
    }

    @GetMapping("/{realm}")
    public ResponseEntity<AdminClientCredentialDTO> buscarRealmAdminClient(@PathVariable String realm) {
        return ResponseEntity.ok(adminClientCredentialMapper.map(realmClientAdminPort.buscarRealmAdminClient(realm)));
    }
}
