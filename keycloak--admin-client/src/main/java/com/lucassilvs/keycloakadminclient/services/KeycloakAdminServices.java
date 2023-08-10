package com.lucassilvs.keycloakadminclient.services;

import com.lucassilvs.keycloakadminclient.services.model.ClientCredential;
import com.lucassilvs.keycloakadminclient.services.model.RealmRole;


public interface KeycloakAdminServices {

    void criarClientCredentials(String realm, ClientCredential clientCredential);

    ClientCredential buscarClientCredentials(String realm, String clientId);

    void criarRealmRole(String realm, RealmRole keycloakRealmRoleModelDto);

    RealmRole buscaRealmRole(String realm, String nomeRole);

    void atribuiRealmRoleAoClient(String realm, String clientId, String nomeRole);
}
