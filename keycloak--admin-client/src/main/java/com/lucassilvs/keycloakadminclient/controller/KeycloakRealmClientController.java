package com.lucassilvs.keycloakadminclient.controller;

import com.lucassilvs.keycloakadminclient.controller.dto.RealmAdminClientDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface KeycloakRealmClientController {

    ResponseEntity<Void> cadastrarRealmAdminClient(RealmAdminClientDTO realmAdminClientDTO);

    ResponseEntity<List<RealmAdminClientDTO>> listarRealmAdminClients();

    ResponseEntity<RealmAdminClientDTO> buscarRealmAdminClient(String realm);
}
