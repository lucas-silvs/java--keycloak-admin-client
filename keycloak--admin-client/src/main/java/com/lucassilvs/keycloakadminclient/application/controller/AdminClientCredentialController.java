package com.lucassilvs.keycloakadminclient.application.controller;

import com.lucassilvs.keycloakadminclient.application.controller.dto.AdminClientCredentialDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AdminClientCredentialController {

    ResponseEntity<Void> cadastrarRealmAdminClient(AdminClientCredentialDTO adminClientCredentialDTO);

    ResponseEntity<List<AdminClientCredentialDTO>> listarRealmAdminClients();

    ResponseEntity<AdminClientCredentialDTO> buscarRealmAdminClient(String realm);
}
