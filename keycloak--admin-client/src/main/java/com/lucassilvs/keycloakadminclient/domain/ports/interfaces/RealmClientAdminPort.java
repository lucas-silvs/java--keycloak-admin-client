package com.lucassilvs.keycloakadminclient.domain.ports.interfaces;

import com.lucassilvs.keycloakadminclient.domain.model.AdminClientCredential;

import java.util.List;

public interface RealmClientAdminPort {

    void cadastrarRealmAdminClient(AdminClientCredential adminClientCredential);

    AdminClientCredential buscarRealmAdminClient(String realm);

    List<AdminClientCredential> listarRealmAdminClients();
}
