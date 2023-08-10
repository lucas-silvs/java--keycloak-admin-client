package com.lucassilvs.keycloakadminclient.services;

import com.lucassilvs.keycloakadminclient.services.model.AdminClientCredentials;

import java.util.List;

public interface RealmClientAdminServices {

    void cadastrarRealmAdminClient(AdminClientCredentials keyclokaAdminClientDto);

    AdminClientCredentials buscarRealmAdminClient(String realm);

    List<AdminClientCredentials> listarRealmAdminClients();
}
