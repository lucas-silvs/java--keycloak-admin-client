package com.lucassilvs.keycloakadminclient.domain.ports.interfaces;

import com.lucassilvs.keycloakadminclient.domain.model.ClientCredential;
import com.lucassilvs.keycloakadminclient.domain.model.RealmRole;


public interface ClientCredentialPorts {

    void criarClientCredentials(String realm, ClientCredential clientCredential);

    ClientCredential buscarClientCredentials(String realm, String clientId);

    void criarRealmRole(String realm, RealmRole keycloakRealmRoleModelDto);

    RealmRole buscaRealmRole(String realm, String nomeRole);

    void atribuiRealmRoleAoClient(String realm, String clientId, String nomeRole);
}
