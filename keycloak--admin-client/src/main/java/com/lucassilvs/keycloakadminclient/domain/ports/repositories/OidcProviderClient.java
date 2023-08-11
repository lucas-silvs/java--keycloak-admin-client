package com.lucassilvs.keycloakadminclient.domain.ports.repositories;

import com.lucassilvs.keycloakadminclient.domain.model.ClientCredential;
import com.lucassilvs.keycloakadminclient.domain.model.RealmRole;

import java.util.Optional;

public interface OidcProviderClient {

    void createClientCredential(String realm, ClientCredential clientCredential);

    Optional<ClientCredential> getClientCredential(String realm, String clientId);

    void createRealmRole(String realm,  RealmRole realmRole);

    Optional<RealmRole> getRealmRole(String realm, String roleName);

    void attachRealmRoleToClient(String realm, String clientId, String roleName);
}
