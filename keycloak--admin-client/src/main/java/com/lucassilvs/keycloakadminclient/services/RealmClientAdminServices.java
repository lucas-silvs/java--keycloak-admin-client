package com.lucassilvs.keycloakadminclient.services;

import com.lucassilvs.keycloakadminclient.controller.dto.RealmAdminClientDTO;

import java.util.List;

public interface RealmClientAdminServices {

    void cadastrarRealmAdminClient(RealmAdminClientDTO keyclokaAdminClientDto);

    RealmAdminClientDTO buscarRealmAdminClient(String realm);

    List<RealmAdminClientDTO> listarRealmAdminClients();
}
